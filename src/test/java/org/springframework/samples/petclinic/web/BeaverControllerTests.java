package org.springframework.samples.petclinic.web;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Perfil;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.BeaverService;
import org.springframework.samples.petclinic.service.PerfilService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collection;
import java.util.HashSet;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@RunWith(SpringRunner.class)
@WebMvcTest(value = BeaverController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
        classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
@AutoConfigureMockMvc
public class BeaverControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BeaverService beaverService;

    @MockBean
    private UserService userService;

    @MockBean
    private PerfilService perfilService;


    @MockBean
    private AuthoritiesService	authoritiesService;

    private static final int TEST_BEAVER_ID = 1;

    private Beaver				beaver1;
   // User user;

    @BeforeEach
    void setup() {

        final User user = new User();
        user.setUsername("beaver1");
        user.setPassword("supersecretpass");
        user.setEnabled(true);

        this.beaver1 = new Beaver();
        this.beaver1.setId(TEST_BEAVER_ID);
        this.beaver1.setFirstName("Nombre");
        this.beaver1.setId(1);
        this.beaver1.setLastName("Apellidos");
        this.beaver1.setEmail("valid@gmail.com");
        this.beaver1.setEspecialidades("Pintura");
        this.beaver1.setDni("12345678Q");
        this.beaver1.setUser(user);

        final Perfil perfil = new Perfil();
        perfil.setBeaver(this.beaver1);
        perfil.setDescripcion("Descripcion perfil");
        //Collection<String> portfolio = new HashSet<>();
        //String foto1 = "fotoPrueba1";
       // String foto2 = "fotoPrueba2";

        //portfolio.add(foto1);
       // portfolio.add(foto2);


        this.beaver1.setPerfil(perfil);

        BDDMockito.given(this.beaverService.findBeaverByIntId(BeaverControllerTests.TEST_BEAVER_ID)).willReturn(this.beaver1);
    }


    @WithMockUser(value = "beaver1")
    @Test
    public void testMostrarPerfilUsuario() throws Exception {
        this.mockMvc.perform(get("/beavers/beaverInfo/{beaverId}", TEST_BEAVER_ID))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("beaver"))
            .andExpect(model().attributeExists("perfil"))
            .andExpect(view().name("users/perfilBeaver"));
    }

    @WithMockUser(value = "testuser")
    @Test
    public void testInitActualizarPerfil() throws Exception {
        this.mockMvc.perform(get("/beavers/beaverInfo/edit/perfil", TEST_BEAVER_ID))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("perfil"))
            .andExpect(view().name("users/editarPerfil"));
    }

    @WithMockUser(value = "testuser")
    @Test
    public void testInitActualizarPerfilHasErrors() throws Exception {
        this.mockMvc.perform(get("/beavers/beaverInfo/edit/perfil"))
            .andExpect(status().isOk())
            .andExpect(view().name("accesoNoAutorizado"));
    }

    @WithMockUser(value = "testuser")
    @Test
    public void testprocessActualizarPerfil() throws Exception {
        this.mockMvc.perform(post("/beavers/beaverInfo/perfil/edit", TEST_BEAVER_ID)
            .param("perfil", "Nueva descripción"))
            .andExpect(status().isOk())
            .andExpect(view().name("users/perfilBeaver"));
    }
}
