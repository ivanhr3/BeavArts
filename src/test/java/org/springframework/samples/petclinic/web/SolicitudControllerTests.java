package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WebMvcTest(value = SolicitudController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
//@AutoConfigureMockMvc
public class SolicitudControllerTests {

    @Autowired
    private SolicitudController	solicitudController;
    @MockBean
    private SolicitudService	solicitudService;
    @MockBean
    private EmailSender			emailSender;
    @MockBean
    private BeaverService		beaverService;
    @MockBean
    private EncargoService		encargoService;
    @MockBean
    private UserService			userService;
    @MockBean
    private AnuncioService anuncioService;

    @MockBean
    private AuthoritiesService	authoritiesService;

    @MockBean
    private FacturaService facturaService;

    @Autowired
    private MockMvc				mockMvc;

    Beaver						beaver;
    Encargo						encargo;
    User						user;
    Authorities					authorities;

    Beaver						beaver2;
    User						user2;
    Authorities					authorities2;

    Solicitud					solicitud;
    Solicitud                   solicitud2;

    Anuncio anuncio;
    Anuncio anuncio2;

    private static final int	TEST_BEAVER_ID			= 1;
    private static final int	TEST_USER_ID			= 1;
    private static final int	TEST_AUTHORITIES_ID		= 1;
    private static final int	TEST_ENCARGO_ID			= 1;

    private static final int	TEST_BEAVER_ID2			= 2;
    private static final int	TEST_AUTHORITIES_ID2	= 2;

    private static final int	TEST_SOLICITUD_ID		= 1;
    private static final int	TEST_SOLICITUD_ID2		= 2;
    private static final int TEST_ANUNCIO_ID = 1;


    @BeforeEach
    void setup() {
        this.beaver = new Beaver();
        this.beaver.setId(SolicitudControllerTests.TEST_BEAVER_ID);
        this.beaver.setFirstName("Nombre");
        this.beaver.setLastName("Apellidos");
        this.beaver.setEmail("valid@gmail.com");
        Collection<Especialidad> especialidad = new HashSet<Especialidad>();
        especialidad.add(Especialidad.FOTOGRAFIA);
        this.beaver.setEspecialidades(especialidad);
        this.beaver.setDni("12345678Q");
        this.user = new User();
        this.user.setUsername("User");
        this.user.setPassword("supersecretpass");
        this.user.setEnabled(true);

        this.beaver.setUser(this.user);

        this.beaver2 = new Beaver();
        this.beaver2.setId(SolicitudControllerTests.TEST_BEAVER_ID2);
        this.beaver2.setFirstName("Nombre");
        this.beaver2.setLastName("Apellidos");
        this.beaver2.setEmail("valid2@gmail.com");
        this.beaver2.setEspecialidades(especialidad);
        this.beaver2.setDni("12345678Q");
        this.user2 = new User();
        this.user2.setUsername("User2");
        this.user2.setPassword("supersecretpass");
        this.user2.setEnabled(true);

        this.beaver2.setUser(this.user2);

        this.encargo = new Encargo();
        this.encargo.setId(SolicitudControllerTests.TEST_ENCARGO_ID);
        this.encargo.setTitulo("Encargo chulisimo");
        this.encargo.setDescripcion("mira que wapo mi encargo reshulon porque tienen que ser tantos caracteres");
        this.encargo.setDisponibilidad(true);
        this.encargo.setPrecio(199.0);
        this.encargo.setBeaver(this.beaver);

        this.solicitud = new Solicitud();
        this.solicitud.setId(SolicitudControllerTests.TEST_SOLICITUD_ID);
        this.solicitud.setEstado(Estados.PENDIENTE);
        this.solicitud.setEncargo(this.encargo);
        this.solicitud.setBeaver(this.beaver2);
        this.solicitud.setPrecio(199.0);
        this.solicitud.setDescripcion("descripcion");
        this.solicitudService.saveSolicitud(this.solicitud);

        List<Solicitud> list = new ArrayList<Solicitud>();
        list.add(this.solicitud);
        this.encargo.setSolicitud(list);
        List<Encargo> list2 = new ArrayList<Encargo>();
        list2.add(this.encargo);
        this.beaver.setEncargos(list2);

        anuncio = new Anuncio();
        anuncio.setBeaver(beaver);
        anuncio.setDescripcion("Esto es una descripción");
        anuncio.setPrecio(50.0);
        anuncio.setTitulo("Esto es un título");
        anuncio.setDestacado(false);
        anuncio.setEspecialidad(Especialidad.ESCULTURA);
        anuncio.setId(90);

        anuncio2 = new Anuncio();
        anuncio2.setBeaver(beaver2);
        anuncio2.setDescripcion("Esto es una descripción 2");
        anuncio2.setPrecio(40.0);
        anuncio2.setTitulo("Esto es un título 2");
        anuncio2.setDestacado(true);
        anuncio2.setEspecialidad(Especialidad.RESINA);
        anuncio2.setId(91);

        solicitud2 = new Solicitud();
        solicitud2.setId(SolicitudControllerTests.TEST_SOLICITUD_ID);
        solicitud2.setEstado(Estados.PENDIENTE);
        solicitud2.setAnuncio(anuncio);
        solicitud2.setBeaver(beaver2);
        solicitud2.setPrecio(199.0);
        solicitud2.setDescripcion("descripcion");
        solicitudService.saveSolicitud(solicitud2);

        BDDMockito.given(this.solicitudService.findById(TEST_SOLICITUD_ID)).willReturn(this.solicitud);
        BDDMockito.given(this.encargoService.findEncargoById(SolicitudControllerTests.TEST_ENCARGO_ID)).willReturn(this.encargo);
        BDDMockito.given(this.userService.findUserByUsername("spring")).willReturn(this.user);
        BDDMockito.given(this.userService.findUserByUsername("spring2")).willReturn(this.user2);
        BDDMockito.given(this.anuncioService.findAnuncioById(TEST_ANUNCIO_ID)).willReturn(anuncio);
        BDDMockito.doNothing().when(this.facturaService).crearFactura(Mockito.any(Factura.class));
    }

    @Test
    @WithMockUser(value = "spring")
    public void crearSolicitudForm() throws Exception {
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver2);
        BDDMockito.given(this.solicitudService.existSolicitudByBeaver(this.beaver2, this.encargo)).willReturn(false);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/{engId}/create", SolicitudControllerTests.TEST_ENCARGO_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("solicitudes/creationForm"));
    }

    @Test
    @WithMockUser(value = "spring")
    public void crearSolicitudSameBeaverAsEncargo() throws Exception {
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver);
        BDDMockito.given(this.solicitudService.existSolicitudByBeaver(this.beaver, this.encargo)).willReturn(false);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/{engId}/create", SolicitudControllerTests.TEST_ENCARGO_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("accesoNoAutorizado"));
    }

    @Test
    @WithMockUser(value = "spring")
    //TODO: El init no tiene ninguna comprobación de este tipo, lo tiene el process. Replantearse como realizar este test.
    public void crearSolicitudSolicitudAlreadyExists() throws Exception {
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver2);
        BDDMockito.given(this.solicitudService.existSolicitudByBeaver(this.beaver2, this.encargo)).willReturn(true);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/{engId}/create", SolicitudControllerTests.TEST_ENCARGO_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("solicitudes/creationForm"));
    }

    @Test
    @WithMockUser(value = "spring")
    public void crearSolicitudForNonExistingEncargo() throws Exception {
        BDDMockito.given(this.encargoService.findEncargoById(SolicitudControllerTests.TEST_ENCARGO_ID)).willReturn(null);
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver2);
        BDDMockito.given(this.solicitudService.existSolicitudByBeaver(this.beaver2, this.encargo)).willReturn(false);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/{engId}/create", SolicitudControllerTests.TEST_ENCARGO_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("exception"));
    }

    @Test
    @WithMockUser(value = "spring")
    public void crearSolicitudNoDisponibleForm() throws Exception {
        this.encargo.setDisponibilidad(false);
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver2);
        BDDMockito.given(this.solicitudService.existSolicitudByBeaver(this.beaver2, this.encargo)).willReturn(false);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/{engId}/create", SolicitudControllerTests.TEST_ENCARGO_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("accesoNoAutorizado"));
    }

    @Test
    @WithMockUser(value = "spring")
    public void crearSolicitudNoBeaverForm() throws Exception {
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(null);
        BDDMockito.given(this.solicitudService.existSolicitudByBeaver(this.beaver2, this.encargo)).willReturn(false);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/{engId}/create", SolicitudControllerTests.TEST_ENCARGO_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("accesoNoAutorizado"));
    }

    @Test
    @WithMockUser(value = "spring")
    public void crearSolicitudPost() throws Exception {
        BDDMockito.given(this.encargoService.findEncargoById(SolicitudControllerTests.TEST_ENCARGO_ID)).willReturn(this.encargo);
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver2);
        BDDMockito.given(this.solicitudService.existSolicitudByBeaver(this.beaver2, this.encargo)).willReturn(false);
        BDDMockito.given(this.solicitudService.isCollectionAllURL(ArgumentMatchers.any(Solicitud.class))).willReturn(true);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/solicitudes/{engId}/create", SolicitudControllerTests.TEST_ENCARGO_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("descripcion", "esta es la descripcion"))
            .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("solicitudes/solicitudSuccess"));
    }

    @Test
    @WithMockUser(value = "spring")
    public void crearSolicitudSinDescripcionPost() throws Exception {
        BDDMockito.given(this.encargoService.findEncargoById(SolicitudControllerTests.TEST_ENCARGO_ID)).willReturn(this.encargo);
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver2);
        BDDMockito.given(this.solicitudService.existSolicitudByBeaver(this.beaver2, this.encargo)).willReturn(false);
        BDDMockito.given(this.solicitudService.isCollectionAllURL(ArgumentMatchers.any(Solicitud.class))).willReturn(true);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/solicitudes/{engId}/create", SolicitudControllerTests.TEST_ENCARGO_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("descripcion", "")).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().attributeExists("solicitud")).andExpect(MockMvcResultMatchers.view().name("solicitudes/creationForm"));
    }

    @Test
    @WithMockUser(value = "spring")
    public void crearSolicitudNoURLsPost() throws Exception {
        BDDMockito.given(this.encargoService.findEncargoById(SolicitudControllerTests.TEST_ENCARGO_ID)).willReturn(this.encargo);
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver2);
        BDDMockito.given(this.solicitudService.existSolicitudByBeaver(this.beaver2, this.encargo)).willReturn(false);
        BDDMockito.given(this.solicitudService.isCollectionAllURL(ArgumentMatchers.any(Solicitud.class))).willReturn(false);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/solicitudes/{engId}/create", SolicitudControllerTests.TEST_ENCARGO_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("descripcion", "esta es la descripcion"))
            .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("solicitud")).andExpect(MockMvcResultMatchers.view().name("solicitudes/creationForm"));
    }

    @Test
    @WithMockUser(value = "spring")
    public void crearSolicitudMismoBeaverPost() throws Exception {
        BDDMockito.given(this.encargoService.findEncargoById(SolicitudControllerTests.TEST_ENCARGO_ID)).willReturn(this.encargo);
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver);
        BDDMockito.given(this.solicitudService.existSolicitudByBeaver(this.beaver2, this.encargo)).willReturn(false);
        BDDMockito.given(this.solicitudService.isCollectionAllURL(ArgumentMatchers.any(Solicitud.class))).willReturn(true);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/solicitudes/{engId}/create", SolicitudControllerTests.TEST_ENCARGO_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("descripcion", "esta es la descripcion"))
            .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("accesoNoAutorizado"));
    }

    @Test
    @WithMockUser(value = "spring")
    public void crearSolicitudYaExistentePost() throws Exception {
        BDDMockito.given(this.encargoService.findEncargoById(SolicitudControllerTests.TEST_ENCARGO_ID)).willReturn(this.encargo);
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver2);
        BDDMockito.given(this.solicitudService.existSolicitudByBeaver(this.beaver2, this.encargo)).willReturn(true);
        BDDMockito.given(this.solicitudService.isCollectionAllURL(ArgumentMatchers.any(Solicitud.class))).willReturn(true);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/solicitudes/{engId}/create", SolicitudControllerTests.TEST_ENCARGO_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("descripcion", "esta es la descripcion"))
            .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("solicitud")).andExpect(MockMvcResultMatchers.view().name("solicitudes/creationForm"));
    }

    @Test
    @WithMockUser(value = "spring")
    public void crearSolicitudBeaverNullPost() throws Exception {
        BDDMockito.given(this.encargoService.findEncargoById(SolicitudControllerTests.TEST_ENCARGO_ID)).willReturn(this.encargo);
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(null);
        BDDMockito.given(this.solicitudService.existSolicitudByBeaver(this.beaver2, this.encargo)).willReturn(false);
        BDDMockito.given(this.solicitudService.isCollectionAllURL(ArgumentMatchers.any(Solicitud.class))).willReturn(true);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/solicitudes/{engId}/create", SolicitudControllerTests.TEST_ENCARGO_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("descripcion", "esta es la descripcion"))
            .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("accesoNoAutorizado"));
    }

    @Test
    @WithMockUser(value = "spring") //Model attribute listaSolicitudes no existe, no encaja el back con el front
    public void listarSolicitudesTest() throws Exception {
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/list"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().attributeExists("hayEncargos"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("haySolicitudes"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("listaSolicitudesRecibidas"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("listaSolicitudesEnviadas"))
            .andExpect(MockMvcResultMatchers.view().name("solicitudes/listadoSolicitudes"));
    }

    @Test
    @WithMockUser(value = "spring")
    public void listarSolicitudesBeaverWithAnunciosTest() throws Exception {
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver2);
        Collection<Anuncio> listaAnuncios2 = new ArrayList<>();
        listaAnuncios2.add(anuncio2);
        beaver2.setAnuncios(listaAnuncios2);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/list"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().attributeExists("hayEncargos"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("haySolicitudes"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("listaSolicitudesRecibidas"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("listaSolicitudesEnviadas"))
            .andExpect(MockMvcResultMatchers.view().name("solicitudes/listadoSolicitudes"));
    }

    @Test
    @WithMockUser(value = "spring")
    public void listarSolicitudesBeaverWithEncargosTest() throws Exception {
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver2);
        Collection<Encargo> listaEncargos2 = new ArrayList<>();
        listaEncargos2.add(encargo);
        beaver2.setEncargos(listaEncargos2);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/list"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().attributeExists("hayEncargos"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("haySolicitudes"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("listaSolicitudesRecibidas"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("listaSolicitudesEnviadas"))
            .andExpect(MockMvcResultMatchers.view().name("solicitudes/listadoSolicitudes"));
    }

    @Test
    @WithMockUser(value = "spring") //Model attribute listaSolicitudes no existe, no encaja el back con el front
    public void listarSolicitudesConEnviadasTest() throws Exception {
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver);
        Collection<Solicitud> res = new ArrayList<>();
        res.add(this.solicitud);
        this.beaver.setSolicitud(res);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/list")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("hayEncargos"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("haySolicitudes")).andExpect(MockMvcResultMatchers.model().attributeExists("listaSolicitudesRecibidas"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("listaSolicitudesEnviadas")).andExpect(MockMvcResultMatchers.view().name("solicitudes/listadoSolicitudes"));
    }

    @Test
    @WithMockUser(value = "spring") //Model attribute listaSolicitudes no existe, no encaja el back con el front
    public void listarSolicitudesSinEncargosTest() throws Exception {
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver2);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/list")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("haySolicitudes"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("listaSolicitudesRecibidas")).andExpect(MockMvcResultMatchers.model().attributeExists("listaSolicitudesEnviadas"))
            .andExpect(MockMvcResultMatchers.view().name("solicitudes/listadoSolicitudes"));
    }

    @Test
    @WithMockUser(value = "spring")
    public void mostrarSolicitudesTest() throws Exception {
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver);
        Optional<Solicitud> sol = Optional.of(this.solicitud);
        BDDMockito.given(this.solicitudService.findSolicitudById(ArgumentMatchers.anyInt())).willReturn(sol);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/solicitudInfo/{solicitudId}", SolicitudControllerTests.TEST_SOLICITUD_ID)).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("solicitudes/solicitudesDetails"));
    }

    @Test
    @WithMockUser(value = "spring")
    public void mostrarSolicitudesWithAnuncioTest() throws Exception {
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver);
        Optional<Solicitud> sol = Optional.of(this.solicitud);
        BDDMockito.given(this.solicitudService.findSolicitudById(ArgumentMatchers.anyInt())).willReturn(sol);
        sol.get().setAnuncio(anuncio);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/solicitudInfo/{solicitudId}", SolicitudControllerTests.TEST_SOLICITUD_ID))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("solicitudes/solicitudesDetails"));
    }

    @Test
    @WithMockUser(value = "spring")
    public void mostrarSolicitudesAceptadaTest() throws Exception {
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver);
        Optional<Solicitud> sol = Optional.of(this.solicitud);
        BDDMockito.given(this.solicitudService.findSolicitudById(ArgumentMatchers.anyInt())).willReturn(sol);
        this.solicitud.setEstado(Estados.ACEPTADO);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/solicitudInfo/{solicitudId}", SolicitudControllerTests.TEST_SOLICITUD_ID)).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("solicitudes/solicitudesDetails"));
    }

    @Test
    @WithMockUser(value = "spring")
    public void mostrarSolicitudesOtroBeaverTest() throws Exception {
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver2);
        Optional<Solicitud> sol = Optional.of(this.solicitud);
        BDDMockito.given(this.solicitudService.findSolicitudById(ArgumentMatchers.anyInt())).willReturn(sol);
        this.solicitud.setEstado(Estados.ACEPTADO);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/solicitudInfo/{solicitudId}", SolicitudControllerTests.TEST_SOLICITUD_ID)).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("solicitudes/solicitudesDetails"));
    }

    @WithMockUser(value = "spring")
    @Test
    void testAceptarSolicitud() throws Exception {
        Mockito.doNothing().when(this.emailSender).sendEmail(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/accept/{solId}", SolicitudControllerTests.TEST_SOLICITUD_ID)).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("solicitudes/aceptarSuccess"));
    }

    @WithMockUser(value = "spring")
    @Test
    void acceptSolicitudOfAnuncioTest() throws Exception {
        Mockito.doNothing().when(this.emailSender).sendEmail(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver);
        BDDMockito.given(this.solicitudService.findById(TEST_SOLICITUD_ID2)).willReturn(solicitud2);
        BDDMockito.given(this.anuncioService.findAnuncioById(ArgumentMatchers.anyInt())).willReturn(anuncio);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/accept/{solId}", SolicitudControllerTests.TEST_SOLICITUD_ID2)).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("solicitudes/aceptarSuccess"));
    }

    @WithMockUser(value = "spring")
    @Test
    void testAceptarSolicitudWithWrongUser() throws Exception {
        Mockito.doNothing().when(this.emailSender).sendEmail(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver2);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/accept/{solId}", SolicitudControllerTests.TEST_SOLICITUD_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("solicitudes/errorAceptar"));
    }

    @WithMockUser(value = "spring")
    @Test
    void acceptSolicitudOfAnuncioWithWrongUserTest() throws Exception {
        Mockito.doNothing().when(this.emailSender).sendEmail(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver2);
        BDDMockito.given(this.solicitudService.findById(TEST_SOLICITUD_ID2)).willReturn(solicitud2);
        BDDMockito.given(this.anuncioService.findAnuncioById(ArgumentMatchers.anyInt())).willReturn(anuncio);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/accept/{solId}", SolicitudControllerTests.TEST_SOLICITUD_ID2)).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("solicitudes/errorAceptar"));
    }

    @WithMockUser(value = "spring")
    @Test
    void testRechazarSolicitud() throws Exception {
        Mockito.doNothing().when(this.emailSender).sendEmail(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/decline/{solId}", SolicitudControllerTests.TEST_SOLICITUD_ID)).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("solicitudes/rechazarSuccess"));
    }

    @WithMockUser(value = "spring")
    @Test
    void declineSolicitudOfAnuncioTest() throws Exception {
        Mockito.doNothing().when(this.emailSender).sendEmail(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver);
        BDDMockito.given(this.solicitudService.findById(TEST_SOLICITUD_ID2)).willReturn(solicitud2);
        BDDMockito.given(this.anuncioService.findAnuncioById(ArgumentMatchers.anyInt())).willReturn(anuncio);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/decline/{solId}", SolicitudControllerTests.TEST_SOLICITUD_ID2)).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("solicitudes/rechazarSuccess"));
    }

    @WithMockUser(value = "spring")
    @Test
    void testRechazarSolicitudWithWrongUser() throws Exception {
        Mockito.doNothing().when(this.emailSender).sendEmail(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver2);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/decline/{solId}", SolicitudControllerTests.TEST_SOLICITUD_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("solicitudes/errorRechazar"));
    }

    @WithMockUser(value = "spring")
    @Test
    void declineSolicitudOfAnuncioWithWrongUserTest() throws Exception {
        Mockito.doNothing().when(this.emailSender).sendEmail(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver2);
        BDDMockito.given(this.solicitudService.findById(TEST_SOLICITUD_ID2)).willReturn(solicitud2);
        BDDMockito.given(this.anuncioService.findAnuncioById(ArgumentMatchers.anyInt())).willReturn(anuncio);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/decline/{solId}", SolicitudControllerTests.TEST_SOLICITUD_ID2)).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("solicitudes/errorRechazar"));
    }

    @WithMockUser(value = "spring")
    @Test
    void testFinalizarSolicitud() throws Exception {
        Mockito.doNothing().when(this.emailSender).sendEmail(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver);
        Solicitud solicitudFin = this.solicitud;
        solicitudFin.setEstado(Estados.ACEPTADO);
        BDDMockito.given(this.solicitudService.findById(ArgumentMatchers.anyInt())).willReturn(solicitudFin);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/finish/{solId}", SolicitudControllerTests.TEST_SOLICITUD_ID))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("solicitudes/aceptarSuccess"));
    }

    @WithMockUser(value = "spring")
    @Test
    void finishSolicitudOfAnuncioTest() throws Exception {
        Mockito.doNothing().when(this.emailSender).sendEmail(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver);
        BDDMockito.given(this.solicitudService.findById(TEST_SOLICITUD_ID2)).willReturn(solicitud2);
        BDDMockito.given(this.anuncioService.findAnuncioById(ArgumentMatchers.anyInt())).willReturn(anuncio);
        solicitud2.setEstado(Estados.ACEPTADO);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/finish/{solId}", SolicitudControllerTests.TEST_SOLICITUD_ID2))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("solicitudes/aceptarSuccess"));
    }

    @WithMockUser(value = "spring")
    @Test
    void testFinalizarSolicitudWithWrongUser() throws Exception {
        Mockito.doNothing().when(this.emailSender).sendEmail(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver2);
        Solicitud solicitudFin = this.solicitud;
        solicitudFin.setEstado(Estados.ACEPTADO);
        BDDMockito.given(this.solicitudService.findById(ArgumentMatchers.anyInt())).willReturn(solicitudFin);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/finish/{solId}", SolicitudControllerTests.TEST_SOLICITUD_ID))
            .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("solicitudes/errorAceptar"));
    }

    @WithMockUser(value = "spring")
    @Test
    void finishSolicitudOfAnuncioWithWrongUserTest() throws Exception {
        Mockito.doNothing().when(this.emailSender).sendEmail(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver2);
        BDDMockito.given(this.solicitudService.findById(TEST_SOLICITUD_ID2)).willReturn(solicitud2);
        BDDMockito.given(this.anuncioService.findAnuncioById(ArgumentMatchers.anyInt())).willReturn(anuncio);
        solicitud2.setEstado(Estados.ACEPTADO);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/finish/{solId}", SolicitudControllerTests.TEST_SOLICITUD_ID2))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("solicitudes/errorAceptar"));
    }

    @WithMockUser(value = "spring")
    @Test
    void testFinalizarSolicitudForSolicitudNotAccepted() throws Exception {
        Mockito.doNothing().when(this.emailSender).sendEmail(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver);
        Solicitud solicitudFin = this.solicitud;
        solicitudFin.setEstado(Estados.PENDIENTE);
        BDDMockito.given(this.solicitudService.findById(ArgumentMatchers.anyInt())).willReturn(solicitudFin);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/finish/{solId}", SolicitudControllerTests.TEST_SOLICITUD_ID))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("accesoNoAutorizado"));
    }

    @WithMockUser(value = "spring")
    @Test
    void finishSolicitudOfAnuncioForSolicitudNotAcceptedTest() throws Exception {
        Mockito.doNothing().when(this.emailSender).sendEmail(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver);
        BDDMockito.given(this.solicitudService.findById(TEST_SOLICITUD_ID2)).willReturn(solicitud2);
        BDDMockito.given(this.anuncioService.findAnuncioById(ArgumentMatchers.anyInt())).willReturn(anuncio);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/finish/{solId}", SolicitudControllerTests.TEST_SOLICITUD_ID2))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("accesoNoAutorizado"));
    }

    @Test
    @WithMockUser(value = "spring2")
    public void testInitCrearSolicitudAnuncios() throws Exception {
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver2);

        mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/{anuncioId}/new", TEST_ANUNCIO_ID))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().attributeExists("anuncio"))
            .andExpect(MockMvcResultMatchers.view().name("solicitudes/creationFormAnuncios"));
    }

    @Test
    @WithMockUser(value = "spring")
    public void testInitCrearSolicitudAnunciosHasErrors() throws Exception {
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/{anuncioId}/new", TEST_ANUNCIO_ID))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("accesoNoAutorizado"));
    }

    @Test
    @WithMockUser(value = "spring")
    public void testInitCrearSolicitudAnunciosHasErrors2() throws Exception {
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(anuncio.getBeaver());

        mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/{anuncioId}/new", TEST_ANUNCIO_ID))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("accesoNoAutorizado"));
    }

    @Test
    @WithMockUser(value = "spring")
    public void testProcessCrearSolicitudAnuncios() throws Exception {
        BDDMockito.given(this.anuncioService.findAnuncioById(TEST_ANUNCIO_ID)).willReturn(anuncio);
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver2);
        BDDMockito.given(this.solicitudService.existSolicitudAnuncioByBeaver(beaver2, anuncio)).willReturn(false);
        BDDMockito.given(this.solicitudService.isCollectionAllURL(ArgumentMatchers.any(Solicitud.class))).willReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/solicitudes/{anuncioId}/new", TEST_ANUNCIO_ID)
            .with(csrf())
            .param("estado", "PENDIENTE")
            .param("precio", "11.0")
            .param("descripcion", "Esta es la descripcion"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("solicitudes/solicitudSuccess"));
    }

    @Test
    @WithMockUser(value = "spring")
    public void testProcessCrearSolicitudAnunciosHasErrors() throws Exception {
        BDDMockito.given(this.anuncioService.findAnuncioById(TEST_ANUNCIO_ID)).willReturn(anuncio);
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(null);
        BDDMockito.given(this.solicitudService.existSolicitudAnuncioByBeaver(beaver, anuncio)).willReturn(false);
        BDDMockito.given(this.solicitudService.isCollectionAllURL(ArgumentMatchers.any(Solicitud.class))).willReturn(true);

        solicitudAnunciosHasErrors1Y3();
    }

    @Test
    @WithMockUser(value = "spring")
    public void testProcessCrearSolicitudAnunciosHasErrors2() throws Exception {
        BDDMockito.given(this.anuncioService.findAnuncioById(TEST_ANUNCIO_ID)).willReturn(anuncio);
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver2);
        BDDMockito.given(this.solicitudService.existSolicitudAnuncioByBeaver(beaver2, anuncio)).willReturn(true);
        BDDMockito.given(this.solicitudService.isCollectionAllURL(ArgumentMatchers.any(Solicitud.class))).willReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/solicitudes/{anuncioId}/new", TEST_ANUNCIO_ID)
            .with(csrf())
            .param("estado", "PENDIENTE")
            .param("precio", "11.0")
            .param("descripcion", "Esta es la descripcion"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().attributeExists("solicitud"))
            .andExpect(MockMvcResultMatchers.view().name("solicitudes/creationFormAnuncios"));
    }

    @Test
    @WithMockUser(value = "spring")
    public void testProcessCrearSolicitudAnunciosHasErrors3() throws Exception {
        BDDMockito.given(this.anuncioService.findAnuncioById(TEST_ANUNCIO_ID)).willReturn(anuncio);
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver);
        BDDMockito.given(this.solicitudService.existSolicitudAnuncioByBeaver(beaver, anuncio)).willReturn(true);
        BDDMockito.given(this.solicitudService.isCollectionAllURL(ArgumentMatchers.any(Solicitud.class))).willReturn(true);

        solicitudAnunciosHasErrors1Y3();
    }

    public void solicitudAnunciosHasErrors1Y3() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/solicitudes/{anuncioId}/new", TEST_ANUNCIO_ID)
            .with(csrf())
            .param("estado", "PENDIENTE")
            .param("precio", "11.0")
            .param("descripcion", "Esta es la descripcion"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("accesoNoAutorizado"));
    }

    @Test
    @WithMockUser(value = "spring")
    public void testProcessCrearSolicitudAnunciosHasErrors4() throws Exception {
        BDDMockito.given(this.anuncioService.findAnuncioById(TEST_ANUNCIO_ID)).willReturn(anuncio);
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver);

        mockMvc.perform(MockMvcRequestBuilders.post("/solicitudes/{anuncioId}/new", TEST_ANUNCIO_ID)
            .with(csrf())
            .param("descripcion", ""))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().attributeExists("solicitud"))
            .andExpect(MockMvcResultMatchers.view().name("solicitudes/creationFormAnuncios"));
    }

}
