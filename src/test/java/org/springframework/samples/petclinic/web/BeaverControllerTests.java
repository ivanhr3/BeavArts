package org.springframework.samples.petclinic.web;


import org.junit.jupiter.api.Test;
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
import org.springframework.samples.petclinic.model.Especialidad;
import org.springframework.samples.petclinic.model.Portfolio;
import org.springframework.samples.petclinic.service.PortfolioService;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.BeaverService;
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


//@RunWith(SpringRunner.class)
@WebMvcTest(value = BeaverController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
        classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
//@AutoConfigureMockMvc
public class BeaverControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BeaverService beaverService;

    @MockBean
    private UserService userService;

    @MockBean
    private PortfolioService portfolioService;


    @MockBean
    private AuthoritiesService	authoritiesService;

    private static final int TEST_BEAVER_ID = 11;

    private Beaver				beaver1;

    private Beaver				beaver2;
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
        this.beaver1.setId(11);
        this.beaver1.setLastName("Apellidos");
        this.beaver1.setEmail("valid@gmail.com");
        Collection<Especialidad> esP = new HashSet<>();
        esP.add(Especialidad.ILUSTRACIÓN);
        this.beaver1.setEspecialidades(esP);
        this.beaver1.setDni("12345678Q");
        this.beaver1.setUser(user);

        final Portfolio portfolio = new Portfolio();
        portfolio.setBeaver(this.beaver1);
        portfolio.setSobreMi("Descripcion perfil");
        Collection<String> fotos = new HashSet<>();
        String foto1 = "fotoPrueba1";
        String foto2 = "fotoPrueba2";
        fotos.add(foto1);
        fotos.add(foto2);

        this.beaver1.setPortfolio(portfolio);

        final User user2 = new User();
        user2.setUsername("beaver2");
        user2.setPassword("supersecretpass");
        user2.setEnabled(true);

        this.beaver2 = new Beaver();
        this.beaver2.setId(12);
        this.beaver2.setFirstName("Nombre 2");
        this.beaver2.setLastName("Apellidos");
        this.beaver2.setEmail("valid4@gmail.com");
        Collection<Especialidad> esp = new HashSet<>();
        esp.add(Especialidad.ILUSTRACIÓN);
        this.beaver2.setEspecialidades(esp);
        this.beaver2.setDni("12345978Q");
        this.beaver2.setUser(user2);


        BDDMockito.given(this.userService.findUserByUsername("beaver2")).willReturn(user2);
        BDDMockito.given(this.userService.findUserByUsername("beaver1")).willReturn(user);
        BDDMockito.given(this.beaverService.findBeaverByUsername("beaver1")).willReturn(this.beaver1);
        BDDMockito.given(this.beaverService.findBeaverByIntId(BeaverControllerTests.TEST_BEAVER_ID)).willReturn(this.beaver1);
        BDDMockito.given(this.beaverService.findBeaverByIntId(12)).willReturn(this.beaver2);
    }


    @WithMockUser(value = "beaver1")
    @Test
    public void testMostrarPerfilUsuario() throws Exception {

        System.out.println(this.beaver1.getFirstName());

        this.mockMvc.perform(get("/beavers/beaverInfo/{beaverId}", TEST_BEAVER_ID))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("beaver"))
            .andExpect(model().attributeExists("portfolio"))
            .andExpect(view().name("users/perfilBeaver"));
    }

    @WithMockUser(value = "beaver2")
    @Test
    public void testMostrarPerfilUsuarioPortfolioNulo() throws Exception {

        System.out.println(this.beaver1.getFirstName());

        this.mockMvc.perform(get("/beavers/beaverInfo/{beaverId}", 12))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("beaver"))
            .andExpect(model().attributeExists("portfolio"))
            .andExpect(view().name("users/perfilBeaver"));
    }

    @WithMockUser(value = "beaver1")
    @Test
    public void testInitActualizarPerfil() throws Exception {
        this.mockMvc.perform(get("/beavers/beaverInfo/{beaverId}/portfolio/edit", TEST_BEAVER_ID))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("portfolio"))
            .andExpect(view().name("users/editarPortfolio"));
    }

    @WithMockUser(value = "beaver2")
    @Test
    public void testInitActualizarPerfilHasErrors() throws Exception {
        this.mockMvc.perform(get("/beavers/beaverInfo/{beaverId}/portfolio/edit", TEST_BEAVER_ID))
            .andExpect(status().isOk())
            .andExpect(view().name("accesoNoAutorizado"));
    }

    @WithMockUser(value = "beaver1")
    @Test
    public void testProcessActualizarPerfil() throws Exception {
        this.mockMvc.perform(post("/beavers/beaverInfo/{beaverId}/portfolio/edit", TEST_BEAVER_ID).with(csrf())
            .param("sobreMi", "Nueva descripción")
            .param("photos", "fotos", "fotos2"))
            .andExpect(status().isOk())
            .andExpect(view().name("users/perfilBeaver"));
    }

    @WithMockUser(value = "beaver2")
    @Test
    public void testProcessActualizarPerfilNulo() throws Exception {
        this.mockMvc.perform(post("/beavers/beaverInfo/{beaverId}/portfolio/edit", 12).with(csrf())
            .param("sobreMi", "Nueva descripción")
            .param("photos", "fotos", "fotos2"))
            .andExpect(status().isOk())
            .andExpect(view().name("users/perfilBeaver"));
    }

}
