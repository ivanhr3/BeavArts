package org.springframework.samples.petclinic.web;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Encargo;
import org.springframework.samples.petclinic.model.Estado;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.BeaverService;
import org.springframework.samples.petclinic.service.EncargoService;
import org.springframework.samples.petclinic.service.SolicitudService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.FilterType;


@WebMvcTest(value = SolicitudController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
public class SolicitudControllerTests {
    @Autowired
    private SolicitudController solicitudController;
    @MockBean
    private SolicitudService solicitudService;
    @MockBean
    private EmailSender emailSender;
    @MockBean
    private BeaverService beaverService;
    @MockBean
    private EncargoService encargoService;

    @Autowired
    private MockMvc mockMvc;


    Beaver beaver;
    Encargo encargo;
    User user;
    Authorities authorities;

    Beaver beaver2;
    User user2;
    Authorities authorities2;

    Solicitud solicitud;

    private static final int TEST_BEAVER_ID = 1;
    private static final int TEST_USER_ID = 1;
    private static final int TEST_AUTHORITIES_ID = 1;
    private static final int TEST_ENCARGO_ID = 1;

    private static final int TEST_BEAVER_ID2 = 2;
    private static final int TEST_AUTHORITIES_ID2 = 2;

    private static final int TEST_SOLICITUD_ID = 1;

    @BeforeEach
    void setup(){
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

        beaver2 = new Beaver();
        beaver2.setId(TEST_BEAVER_ID2);
        beaver2.setFirstName("Nombre");
        beaver2.setLastName("Apellidos");
        beaver2.setEmail("valid2@gmail.com");
        beaver2.setEspecialidades("Pintura");
        beaver2.setDni("12345678Q");
            user2 = new User();
            user2.setUsername("User2");
            user2.setPassword("supersecretpass");
            user2.setEnabled(true);

        beaver2.setUser(user2);

        encargo = new Encargo();
        encargo.setId(TEST_ENCARGO_ID);
        encargo.setTitulo("Encargo chulisimo");
        encargo.setDescripcion("mira que wapo mi encargo reshulon porque tienen que ser tantos caracteres");
        encargo.setDisponibilidad(true);
        encargo.setPrecio(199);
        encargo.setBeaver(beaver);

        solicitud = new Solicitud();
        solicitud.setId(TEST_SOLICITUD_ID);
        solicitud.setEstado(Estado.PENDIENTE);
        solicitud.setEncargo(encargo);
        solicitud.setBeaver(beaver);
        solicitud.setPrecio(199);
        solicitudService.saveSolicitud(solicitud);

        given(solicitudService.findById(Mockito.anyInt())).willReturn(solicitud);
        given(encargoService.findEncargoById(TEST_ENCARGO_ID)).willReturn(encargo);
    }

    @WithMockUser(value = "spring")
    @Test
    void testAceptarSolicitud() throws Exception{
        Mockito.doNothing().when(emailSender).sendEmail(Mockito.anyString(), Mockito.anyString());
        given(beaverService.getCurrentBeaver()).willReturn(beaver);

        mockMvc.perform(get("/solicitud/accept/{solId}", TEST_SOLICITUD_ID))
        .andExpect(status().isOk())
        .andExpect(view().name("testview"));
    }

    @WithMockUser(value = "spring")
    @Test
    void testRechazarSolicitud() throws Exception{
        Mockito.doNothing().when(emailSender).sendEmail(Mockito.anyString(), Mockito.anyString());
        given(beaverService.getCurrentBeaver()).willReturn(beaver);

        mockMvc.perform(get("/solicitud/decline/{solId}", TEST_SOLICITUD_ID))
        .andExpect(status().isOk())
        .andExpect(view().name("testview"));
    }

    @WithMockUser(value = "spring")
    @Test
    void testAceptarSolicitudWithWrongUser() throws Exception{
        Mockito.doNothing().when(emailSender).sendEmail(Mockito.anyString(), Mockito.anyString());
        given(beaverService.getCurrentBeaver()).willReturn(beaver2);

        mockMvc.perform(get("/solicitud/accept/{solId}", TEST_SOLICITUD_ID))
        .andExpect(status().isOk())
        .andExpect(view().name("ErrorPermisos"));
    }

    @WithMockUser(value = "spring")
    @Test
    void testRechazarSolicitudWithWrongUser() throws Exception{
        Mockito.doNothing().when(emailSender).sendEmail(Mockito.anyString(), Mockito.anyString());
        given(beaverService.getCurrentBeaver()).willReturn(beaver2);

        mockMvc.perform(get("/solicitud/accept/{solId}", TEST_SOLICITUD_ID))
        .andExpect(status().isOk())
        .andExpect(view().name("ErrorPermisos"));
    }
}
