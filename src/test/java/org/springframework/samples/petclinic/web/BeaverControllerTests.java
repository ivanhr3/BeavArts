package org.springframework.samples.petclinic.web;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.BeaverService;
import org.springframework.samples.petclinic.service.SolicitudService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;



@RunWith(SpringRunner.class)
@WebMvcTest(value = SolicitudController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
@AutoConfigureMockMvc
public class BeaverControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BeaverService beaverService;

    @Autowired
    private SolicitudService solicitudService;

    private static final int TEST_BEAVER_ID = 1;

    Beaver beaver;
    User user;

    @BeforeEach
    void setup() {
        beaver = new Beaver();
        beaver.setId(TEST_BEAVER_ID);
        beaver.setFirstName("Nombre");
        beaver.setLastName("Apellidos");
        beaver.setEmail("valid@gmail.com");
        beaver.setEspecialidades("Pintura");
        beaver.setDni("12345678Q");

        user = new User();
        user.setUsername("User");
        user.setPassword("supersecretpass");
        user.setEnabled(true);

        beaver.setUser(user);
    }


    @WithMockUser(value = "testuser")
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
