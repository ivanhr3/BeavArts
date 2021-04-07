package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.*;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collection;
import java.util.HashSet;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AnuncioController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
@AutoConfigureMockMvc
public class AnuncioControllerTests {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnuncioService anuncioService;

    @MockBean
    private BeaverService beaverService;

    private static final int	TEST_BEAVER_ID		= 99;
    //private static final int	TEST_ANUNCIO_ID		= 1;
    //private static final int	TEST_BEAVER_ID2	= 98;

    @BeforeEach
    public void setUp() {
        Beaver beaver = new Beaver();
        beaver.setFirstName("Nombre");
        beaver.setLastName("Apellidos");
        beaver.setEmail("valid@gmail.com");
        beaver.setId(99);
        Collection<Especialidad> espe = new HashSet<>();
        espe.add(Especialidad.ESCULTURA);
        beaver.setEspecialidades(espe);
        beaver.setDni("12345678Q");
        User user = new User();
        user.setUsername("User123");
        user.setPassword("supersecretpass");
        user.setEnabled(true);
        beaver.setUser(user);

        beaver.setEncargos(new HashSet<>());

        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver);
        //BDDMockito.given(this.beaverService.findBeaverByIntId(beaver.getId())).willReturn(beaver);

    }

    @WithMockUser(value = "User123")
    @Test
    public void testInitCreationSucces() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/anuncios/new", AnuncioControllerTests.TEST_BEAVER_ID))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("anuncios/createAnunciosForm"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("anuncio"));
    }

    @WithMockUser(value = "User123")
    @Test
    public void testPostCreationSucces() throws Exception {

        this.mockMvc
            .perform(MockMvcRequestBuilders.post("/beavers/{beaverId}/anuncios/new", AnuncioControllerTests.TEST_BEAVER_ID).with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("titulo", "AnuncioTest")
                .param("id", "60")
                .param("precio", "35.50")
                .param("especialidad", "TEXTIL")
                .param("descripcion", "Descripción del anuncio de prueba"))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.view().name("redirect:/beavers/99/anuncios/60"));

    }

    @WithMockUser(value = "User123")
    @Test
    public void testPostCreationHasErrors() throws Exception {

        this.mockMvc
            .perform(MockMvcRequestBuilders.post("/beavers/{beaverId}/anuncios/new", AnuncioControllerTests.TEST_BEAVER_ID).with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("titulo", "AnuncioTest")
                .param("id", "60")
                .param("precio", "")
                .param("especialidad", "TEXTIL")
                .param("descripcion", "Descripción del anuncio de prueba"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("anuncios/createAnunciosForm"));

    }

    @WithMockUser(value = "User123")
    @Test
    public void testPostCreationHasErrors2() throws Exception {

        this.mockMvc
            .perform(MockMvcRequestBuilders.post("/beavers/{beaverId}/anuncios/new", AnuncioControllerTests.TEST_BEAVER_ID).with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("titulo", "")
                .param("id", "60")
                .param("precio", "35.50")
                .param("especialidad", "TEXTIL")
                .param("descripcion", "Descripción del anuncio de prueba"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("anuncios/createAnunciosForm"));

    }
}
