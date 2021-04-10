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
import org.springframework.samples.petclinic.model.*;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(value = AnuncioController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
@AutoConfigureMockMvc
public class AnuncioControllerTests {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnuncioService anuncioService;

    @MockBean
    private BeaverService beaverService;

    private static final int TEST_BEAVER_ID	= 99;
    private static final int TEST_BEAVER_ID2 = 100;
    private static final int TEST_ANUNCIO_ID = 90;
    private static final int TEST_ANUNCIO_ID2 = 91;
    private static final int TEST_ANUNCIO_ID3 = 92;

    private Beaver beaver;
    private Beaver beaver2;
    private Anuncio anuncio;
    private Anuncio anuncio2;
    private Anuncio anuncio3;
    private Solicitud solicitud;


    @BeforeEach
    public void setUp() {
        beaver = new Beaver();
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

        beaver2 = new Beaver();
        beaver2.setFirstName("Nombre");
        beaver2.setLastName("Apellidos");
        beaver2.setEmail("valid@gmail.com");
        Collection<Especialidad> espe2 = new HashSet<>();
        espe2.add(Especialidad.ESCULTURA);
        beaver2.setEspecialidades(espe2);
        beaver2.setDni("12345678X");
        beaver2.setId(100);

        User user2 = new User();
        user2.setUsername("User123456");
        user2.setPassword("supersecretpass2");
        user2.setEnabled(true);
        beaver2.setUser(user2);

        anuncio = new Anuncio();
        anuncio.setBeaver(beaver);
        anuncio.setDescripcion("Esto es una descripción");
        anuncio.setPrecio(50.0);
        anuncio.setTitulo("Esto es un título");
        anuncio.setEspecialidad(Especialidad.ESCULTURA);
        anuncio.setId(90);

        anuncio2 = new Anuncio();
        anuncio2.setBeaver(beaver);
        anuncio2.setDescripcion("Esto es una descripción 2");
        anuncio2.setPrecio(40.0);
        anuncio2.setTitulo("Esto es un título 2");
        anuncio2.setEspecialidad(Especialidad.RESINA);
        anuncio2.setId(91);

        anuncio3 = new Anuncio();
        anuncio3.setBeaver(beaver2);
        anuncio3.setDescripcion("Esto es una descripción 2");
        anuncio3.setPrecio(40.0);
        anuncio3.setTitulo("Esto es un título 2");
        anuncio3.setEspecialidad(Especialidad.RESINA);
        anuncio3.setId(92);

        solicitud = new Solicitud();
        solicitud.setPrecio(50.0);
        solicitud.setAnuncio(anuncio2);
        solicitud.setDescripcion("Esto es una descripción");
        solicitud.setEstado(Estados.ACEPTADO);
        Collection<Solicitud> solicitudes = new HashSet<>();
        solicitudes.add(solicitud);
        anuncio2.setSolicitud(solicitudes);

        BDDMockito.given(this.anuncioService.findAnuncioById(TEST_ANUNCIO_ID)).willReturn(anuncio);
        BDDMockito.given(this.anuncioService.findAnuncioById(TEST_ANUNCIO_ID2)).willReturn(anuncio2);
        BDDMockito.given(this.anuncioService.findAnuncioById(TEST_ANUNCIO_ID3)).willReturn(anuncio3);
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver);
        BDDMockito.given(this.beaverService.findBeaverByIntId(TEST_BEAVER_ID)).willReturn(beaver);
        BDDMockito.given(this.beaverService.findBeaverByIntId(TEST_BEAVER_ID2)).willReturn(beaver2);

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

    // TESTS DE EDITAR ANUNCIO

    @WithMockUser(value = "testuser")
    @Test
    public void testInitUpdateForm() throws Exception {
        mockMvc.perform(get("/beavers/{beaverId}/anuncios/{anuncioId}/edit", TEST_BEAVER_ID, TEST_ANUNCIO_ID)
            .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("anuncio"))
            .andExpect(view().name("anuncios/createAnunciosForm"));
    }

    @WithMockUser(value = "testuser")
    @Test
    public void testInitUpdateFormHasErrors() throws Exception {
        mockMvc.perform(get("/beavers/{beaverId}/anuncios/{anuncioId}/edit", TEST_BEAVER_ID, TEST_ANUNCIO_ID2)
            .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(view().name("anuncios/anunciosDetails"));
    }

    @WithMockUser(value = "testuser")
    @Test
    public void testInitUpdateFormHasErrors2() throws Exception {
        mockMvc.perform(get("/beavers/{beaverId}/anuncios/{anuncioId}/edit", TEST_BEAVER_ID, TEST_ANUNCIO_ID3)
            .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(view().name("accesoNoAutorizado"));
    }

    @WithMockUser(value = "testuser")
    @Test
    public void testProcessUpdateForm() throws Exception {
        mockMvc.perform(post("/beavers/{beaverId}/anuncios/{anuncioId}/edit", TEST_BEAVER_ID, TEST_ANUNCIO_ID)
            .with(csrf())
            .param("titulo", "Cambio en el titulo")
            .param("precio", "49.0")
            .param("descripcion", "Cambio en la descripcion"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/beavers/" + TEST_BEAVER_ID + "/anuncios/" + TEST_ANUNCIO_ID));
    }

    @WithMockUser(value = "testuser")
    @Test
    public void testProcessUpdateFormHasErrors() throws Exception {
        mockMvc.perform(post("/beavers/{beaverId}/anuncios/{anuncioId}/edit", TEST_BEAVER_ID, TEST_ANUNCIO_ID2)
            .with(csrf()))
            .andExpect(model().attributeExists("anuncio"))
            .andExpect(status().isOk())
            .andExpect(view().name("anuncios/createAnunciosForm"));
    }

    // TESTS PARA EL DELETE DE ANUNCIOS

    @WithMockUser(value = "testuser")
    @Test
    public void testDeleteAnuncio() throws Exception {
        mockMvc.perform(get("/beavers/{beaverId}/anuncios/{anuncioId}/delete", TEST_BEAVER_ID, TEST_ANUNCIO_ID)
            .with(csrf()))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/beavers/" + TEST_BEAVER_ID + "/encargos/list"));
    }

    @WithMockUser(value = "testuser")
    @Test
    public void testDeleteAnuncioHasErrors() throws Exception {
        mockMvc.perform(get("/beavers/{beaverId}/anuncios/{anuncioId}/delete", TEST_BEAVER_ID, TEST_ANUNCIO_ID2)
            .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(view().name("anuncios/anunciosDetails"));
    }

    @WithMockUser(value = "testuser")
    @Test
    public void testDeleteHasErrors2() throws Exception {
        mockMvc.perform(get("/beavers/{beaverId}/anuncios/{anuncioId}/delete", TEST_BEAVER_ID2, TEST_ANUNCIO_ID)
            .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(view().name("accesoNoAutorizado"));
    }

    @WithMockUser(value = "testuser")
    @Test
    public void testListAnuncios() throws Exception {
        mockMvc.perform(get("/anuncios/list"))
            .andExpect(model().attributeExists("anuncios"))
            .andExpect(status().isOk())
            .andExpect(view().name("anuncios/listAnuncios"));
    }

    @WithMockUser(value = "testuser")
    @Test
    public void testListAnunciosPorEspecialidad() throws Exception {
        mockMvc.perform(get("/anuncios/listEspecialidad").param("especialidades", "ESCULTURA"))
            .andExpect(model().attributeExists("anuncios"))
            .andExpect(status().isOk())
            .andExpect(view().name("anuncios/listAnuncios"));
    }

    @WithMockUser(value = "testuser")
    @Test
    public void testMostrarAnuncio() throws Exception {
        mockMvc.perform(get("/beavers/{beaverId}/anuncios/{anuncioId}", TEST_BEAVER_ID, TEST_ANUNCIO_ID))
            .andExpect(model().attributeExists("anuncio"))
            .andExpect(status().isOk())
            .andExpect(view().name("anuncios/anunciosDetails"));
    }
}
