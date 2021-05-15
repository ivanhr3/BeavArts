
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Anuncio;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Encargo;
import org.springframework.samples.petclinic.model.Especialidad;
import org.springframework.samples.petclinic.model.Estados;
import org.springframework.samples.petclinic.model.Factura;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AnuncioService;
import org.springframework.samples.petclinic.service.BeaverService;
import org.springframework.samples.petclinic.service.EncargoService;
import org.springframework.samples.petclinic.service.FacturaService;
import org.springframework.samples.petclinic.service.SolicitudService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

// @RunWith(SpringRunner.class)
// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WebMvcTest(value = SolicitudController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
//@AutoConfigureMockMvc
public class SolicitudControllerTests {

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
	private AnuncioService		anuncioService;

	@MockBean
	private FacturaService		facturaService;

	@Autowired
	private MockMvc				mockMvc;

	private Beaver				beaver;
	private Encargo				encargo;
	private User				user;
	private Authorities			authorities;

	private Beaver				beaver2;
	private User				user2;
	private Authorities			authorities2;

	private Solicitud			solicitud;
	private Solicitud			solicitud2;

	private Anuncio				anuncio;
	private Anuncio				anuncio2;

	private Factura				factura;

	private static final int	TEST_BEAVER_ID		= 1;
	private static final int	TEST_ENCARGO_ID		= 1;

	private static final int	TEST_BEAVER_ID2		= 2;

	private static final int	TEST_SOLICITUD_ID	= 1;
	private static final int	TEST_SOLICITUD_ID2	= 2;
	private static final int	TEST_ANUNCIO_ID		= 1;


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

		this.anuncio = new Anuncio();
		this.anuncio.setBeaver(this.beaver);
		this.anuncio.setDescripcion("Esto es una descripción");
		this.anuncio.setPrecio(50.0);
		this.anuncio.setTitulo("Esto es un título");
		this.anuncio.setDestacado(false);
		this.anuncio.setEspecialidad(Especialidad.ESCULTURA);
		this.anuncio.setId(90);

		this.anuncio2 = new Anuncio();
		this.anuncio2.setBeaver(this.beaver2);
		this.anuncio2.setDescripcion("Esto es una descripción 2");
		this.anuncio2.setPrecio(40.0);
		this.anuncio2.setTitulo("Esto es un título 2");
		this.anuncio2.setDestacado(true);
		this.anuncio2.setEspecialidad(Especialidad.RESINA);
		this.anuncio2.setId(91);

		this.solicitud2 = new Solicitud();
		this.solicitud2.setId(SolicitudControllerTests.TEST_SOLICITUD_ID);
		this.solicitud2.setEstado(Estados.PENDIENTE);
		this.solicitud2.setAnuncio(this.anuncio);
		this.solicitud2.setBeaver(this.beaver2);
		this.solicitud2.setPrecio(199.0);
		this.solicitud2.setDescripcion("descripcion");
		this.solicitudService.saveSolicitud(this.solicitud2);

		this.factura = new Factura();
		this.factura.setEmailBeaver("dummy@email.com");
		this.factura.setEmailPayer("dummy@email.com");
		this.factura.setEstado(Estados.PENDIENTE);
		this.factura.setPaymentDate(LocalDate.now());
		this.factura.setId(1);
		this.factura.setPrecio(50.0);
		this.factura.setSolicitud(this.solicitud);

		BDDMockito.given(this.solicitudService.findById(SolicitudControllerTests.TEST_SOLICITUD_ID)).willReturn(this.solicitud);
		BDDMockito.given(this.encargoService.findEncargoById(SolicitudControllerTests.TEST_ENCARGO_ID)).willReturn(this.encargo);
		BDDMockito.given(this.userService.findUserByUsername("spring")).willReturn(this.user);
		BDDMockito.given(this.userService.findUserByUsername("spring2")).willReturn(this.user2);
		BDDMockito.given(this.anuncioService.findAnuncioById(SolicitudControllerTests.TEST_ANUNCIO_ID)).willReturn(this.anuncio);
		Mockito.doNothing().when(this.facturaService).crearFactura(ArgumentMatchers.any(Factura.class));
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

		this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/{engId}/create", SolicitudControllerTests.TEST_ENCARGO_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("accesoNoAutorizado"));
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

	public void setupBeaver2SinSolicitudConEnlaces() throws Exception {
		BDDMockito.given(this.encargoService.findEncargoById(SolicitudControllerTests.TEST_ENCARGO_ID)).willReturn(this.encargo);
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver2);
		BDDMockito.given(this.solicitudService.existSolicitudByBeaver(this.beaver2, this.encargo)).willReturn(false);
		BDDMockito.given(this.solicitudService.isCollectionAllURL(ArgumentMatchers.any(Solicitud.class))).willReturn(true);
	}

	@Test
	@WithMockUser(value = "spring")
	public void crearSolicitudPost() throws Exception {
		this.setupBeaver2SinSolicitudConEnlaces();

		this.mockMvc.perform(MockMvcRequestBuilders.post("/solicitudes/{engId}/create", SolicitudControllerTests.TEST_ENCARGO_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("descripcion", "esta es la descripcion"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("solicitudes/solicitudSuccess"));
	}

	@Test
	@WithMockUser(value = "spring")
	public void crearSolicitudSinDescripcionPost() throws Exception {
		this.setupBeaver2SinSolicitudConEnlaces();

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

	public void setupBeaver2ConEnlaces() throws Exception {
		BDDMockito.given(this.encargoService.findEncargoById(SolicitudControllerTests.TEST_ENCARGO_ID)).willReturn(this.encargo);
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver);
		BDDMockito.given(this.solicitudService.existSolicitudByBeaver(this.beaver2, this.encargo)).willReturn(false);
		BDDMockito.given(this.solicitudService.isCollectionAllURL(ArgumentMatchers.any(Solicitud.class))).willReturn(true);
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

		this.performCheckListarSolicitudes();
	}

	@Test
	@WithMockUser(value = "spring")
	public void listarSolicitudesBeaverWithAnunciosTest() throws Exception {
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver2);
		Collection<Anuncio> listaAnuncios2 = new ArrayList<>();
		listaAnuncios2.add(this.anuncio2);
		this.beaver2.setAnuncios(listaAnuncios2);

		this.performCheckListarSolicitudes();
	}

	@Test
	@WithMockUser(value = "spring")
	public void listarSolicitudesBeaverWithEncargosTest() throws Exception {
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver2);
		Collection<Encargo> listaEncargos2 = new ArrayList<>();
		listaEncargos2.add(this.encargo);
		this.beaver2.setEncargos(listaEncargos2);

		this.performCheckListarSolicitudes();
	}

	@Test
	@WithMockUser(value = "spring") //Model attribute listaSolicitudes no existe, no encaja el back con el front
	public void listarSolicitudesConEnviadasTest() throws Exception {
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver);
		Collection<Solicitud> res = new ArrayList<>();
		res.add(this.solicitud);
		this.beaver.setSolicitud(res);

		this.performCheckListarSolicitudes();
	}

	public void performCheckListarSolicitudes() throws Exception {
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
		BDDMockito.given(this.facturaService.findFacturaBySolicitud(ArgumentMatchers.any())).willReturn(this.factura);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/solicitudInfo/{solicitudId}", SolicitudControllerTests.TEST_SOLICITUD_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("solicitudes/solicitudesDetails"));
	}

	@Test
	@WithMockUser(value = "spring")
	public void mostrarSolicitudesWithAnuncioTest() throws Exception {
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver);
		Optional<Solicitud> sol = Optional.of(this.solicitud);
		BDDMockito.given(this.solicitudService.findSolicitudById(ArgumentMatchers.anyInt())).willReturn(sol);
		BDDMockito.given(this.facturaService.findFacturaBySolicitud(ArgumentMatchers.any())).willReturn(this.factura);
		sol.get().setAnuncio(this.anuncio);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/solicitudInfo/{solicitudId}", SolicitudControllerTests.TEST_SOLICITUD_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("solicitudes/solicitudesDetails"));
	}

	@Test
	@WithMockUser(value = "spring")
	public void mostrarSolicitudesAceptadaTest() throws Exception {
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver);
		Optional<Solicitud> sol = Optional.of(this.solicitud);
		BDDMockito.given(this.solicitudService.findSolicitudById(ArgumentMatchers.anyInt())).willReturn(sol);
		BDDMockito.given(this.facturaService.findFacturaBySolicitud(ArgumentMatchers.any())).willReturn(this.factura);
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
		BDDMockito.given(this.facturaService.findFacturaBySolicitud(ArgumentMatchers.any())).willReturn(this.factura);
		this.solicitud.setEstado(Estados.ACEPTADO);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/solicitudInfo/{solicitudId}", SolicitudControllerTests.TEST_SOLICITUD_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("solicitudes/solicitudesDetails"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testAceptarSolicitud() throws Exception {
		Mockito.doNothing().when(this.emailSender).sendEmail(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/accept/{solId}", SolicitudControllerTests.TEST_SOLICITUD_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("solicitudes/aceptarSuccess"));
	}

	@WithMockUser(value = "spring")
	@Test
	void acceptSolicitudOfAnuncioTest() throws Exception {
		Mockito.doNothing().when(this.emailSender).sendEmail(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver);
		BDDMockito.given(this.solicitudService.findById(SolicitudControllerTests.TEST_SOLICITUD_ID2)).willReturn(this.solicitud2);
		BDDMockito.given(this.anuncioService.findAnuncioById(ArgumentMatchers.anyInt())).willReturn(this.anuncio);

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
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver2);
		BDDMockito.given(this.solicitudService.findById(SolicitudControllerTests.TEST_SOLICITUD_ID2)).willReturn(this.solicitud2);
		BDDMockito.given(this.anuncioService.findAnuncioById(ArgumentMatchers.anyInt())).willReturn(this.anuncio);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/accept/{solId}", SolicitudControllerTests.TEST_SOLICITUD_ID2)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("solicitudes/errorAceptar"));
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
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver);
		BDDMockito.given(this.solicitudService.findById(SolicitudControllerTests.TEST_SOLICITUD_ID2)).willReturn(this.solicitud2);
		BDDMockito.given(this.anuncioService.findAnuncioById(ArgumentMatchers.anyInt())).willReturn(this.anuncio);

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
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver2);
		BDDMockito.given(this.solicitudService.findById(SolicitudControllerTests.TEST_SOLICITUD_ID2)).willReturn(this.solicitud2);
		BDDMockito.given(this.anuncioService.findAnuncioById(ArgumentMatchers.anyInt())).willReturn(this.anuncio);

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

		this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/finish/{solId}", SolicitudControllerTests.TEST_SOLICITUD_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("solicitudes/finalizarSuccess"));
	}

	@WithMockUser(value = "spring")
	@Test
	void finishSolicitudOfAnuncioTest() throws Exception {
		Mockito.doNothing().when(this.emailSender).sendEmail(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver);
		BDDMockito.given(this.solicitudService.findById(SolicitudControllerTests.TEST_SOLICITUD_ID2)).willReturn(this.solicitud2);
		BDDMockito.given(this.anuncioService.findAnuncioById(ArgumentMatchers.anyInt())).willReturn(this.anuncio);
		this.solicitud2.setEstado(Estados.ACEPTADO);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/finish/{solId}", SolicitudControllerTests.TEST_SOLICITUD_ID2)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("solicitudes/finalizarSuccess"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testFinalizarSolicitudWithWrongUser() throws Exception {
		Mockito.doNothing().when(this.emailSender).sendEmail(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver2);
		Solicitud solicitudFin = this.solicitud;
		solicitudFin.setEstado(Estados.ACEPTADO);
		BDDMockito.given(this.solicitudService.findById(ArgumentMatchers.anyInt())).willReturn(solicitudFin);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/finish/{solId}", SolicitudControllerTests.TEST_SOLICITUD_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("solicitudes/errorAceptar"));
	}

	@WithMockUser(value = "spring")
	@Test
	void finishSolicitudOfAnuncioWithWrongUserTest() throws Exception {
		Mockito.doNothing().when(this.emailSender).sendEmail(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver2);
		BDDMockito.given(this.solicitudService.findById(SolicitudControllerTests.TEST_SOLICITUD_ID2)).willReturn(this.solicitud2);
		BDDMockito.given(this.anuncioService.findAnuncioById(ArgumentMatchers.anyInt())).willReturn(this.anuncio);
		this.solicitud2.setEstado(Estados.ACEPTADO);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/finish/{solId}", SolicitudControllerTests.TEST_SOLICITUD_ID2)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("solicitudes/errorAceptar"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testFinalizarSolicitudForSolicitudNotAccepted() throws Exception {
		Mockito.doNothing().when(this.emailSender).sendEmail(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver);
		Solicitud solicitudFin = this.solicitud;
		solicitudFin.setEstado(Estados.PENDIENTE);
		BDDMockito.given(this.solicitudService.findById(ArgumentMatchers.anyInt())).willReturn(solicitudFin);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/finish/{solId}", SolicitudControllerTests.TEST_SOLICITUD_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("accesoNoAutorizado"));
	}

	@WithMockUser(value = "spring")
	@Test
	void finishSolicitudOfAnuncioForSolicitudNotAcceptedTest() throws Exception {
		Mockito.doNothing().when(this.emailSender).sendEmail(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver);
		BDDMockito.given(this.solicitudService.findById(SolicitudControllerTests.TEST_SOLICITUD_ID2)).willReturn(this.solicitud2);
		BDDMockito.given(this.anuncioService.findAnuncioById(ArgumentMatchers.anyInt())).willReturn(this.anuncio);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/finish/{solId}", SolicitudControllerTests.TEST_SOLICITUD_ID2)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("accesoNoAutorizado"));
	}

	@Test
	@WithMockUser(value = "spring2")
	public void testInitCrearSolicitudAnuncios() throws Exception {
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver2);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/{anuncioId}/new", SolicitudControllerTests.TEST_ANUNCIO_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("anuncio"))
			.andExpect(MockMvcResultMatchers.view().name("solicitudes/creationFormAnuncios"));
	}

	@Test
	@WithMockUser(value = "spring")
	public void testInitCrearSolicitudAnunciosHasErrors() throws Exception {
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(null);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/{anuncioId}/new", SolicitudControllerTests.TEST_ANUNCIO_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("accesoNoAutorizado"));
	}

	@Test
	@WithMockUser(value = "spring")
	public void testInitCrearSolicitudAnunciosHasErrors2() throws Exception {
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.anuncio.getBeaver());

		this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/{anuncioId}/new", SolicitudControllerTests.TEST_ANUNCIO_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("accesoNoAutorizado"));
	}

	@Test
	@WithMockUser(value = "spring")
	public void testProcessCrearSolicitudAnuncios() throws Exception {
		BDDMockito.given(this.anuncioService.findAnuncioById(SolicitudControllerTests.TEST_ANUNCIO_ID)).willReturn(this.anuncio);
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver2);
		BDDMockito.given(this.solicitudService.existSolicitudAnuncioByBeaver(this.beaver2, this.anuncio)).willReturn(false);
		BDDMockito.given(this.solicitudService.isCollectionAllURL(ArgumentMatchers.any(Solicitud.class))).willReturn(true);

		this.mockMvc.perform(MockMvcRequestBuilders.post("/solicitudes/{anuncioId}/new", SolicitudControllerTests.TEST_ANUNCIO_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("estado", "PENDIENTE").param("precio", "11.0").param("descripcion",
			"Esta es la descripcion")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("solicitudes/solicitudSuccess"));
	}

	@Test
	@WithMockUser(value = "spring")
	public void testProcessCrearSolicitudAnunciosHasErrors() throws Exception {
		BDDMockito.given(this.anuncioService.findAnuncioById(SolicitudControllerTests.TEST_ANUNCIO_ID)).willReturn(this.anuncio);
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(null);
		BDDMockito.given(this.solicitudService.existSolicitudAnuncioByBeaver(this.beaver, this.anuncio)).willReturn(false);
		BDDMockito.given(this.solicitudService.isCollectionAllURL(ArgumentMatchers.any(Solicitud.class))).willReturn(true);

		this.solicitudAnunciosHasErrors1Y3();
	}

	@Test
	@WithMockUser(value = "spring")
	public void testProcessCrearSolicitudAnunciosHasErrors2() throws Exception {
		BDDMockito.given(this.anuncioService.findAnuncioById(SolicitudControllerTests.TEST_ANUNCIO_ID)).willReturn(this.anuncio);
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver2);
		BDDMockito.given(this.solicitudService.existSolicitudAnuncioByBeaver(this.beaver2, this.anuncio)).willReturn(true);
		BDDMockito.given(this.solicitudService.isCollectionAllURL(ArgumentMatchers.any(Solicitud.class))).willReturn(true);

		this.mockMvc.perform(MockMvcRequestBuilders.post("/solicitudes/{anuncioId}/new", SolicitudControllerTests.TEST_ANUNCIO_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("estado", "PENDIENTE").param("precio", "11.0").param("descripcion",
			"Esta es la descripcion")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("solicitud")).andExpect(MockMvcResultMatchers.view().name("solicitudes/creationFormAnuncios"));
	}

	@Test
	@WithMockUser(value = "spring")
	public void testProcessCrearSolicitudAnunciosHasErrors3() throws Exception {
		BDDMockito.given(this.anuncioService.findAnuncioById(SolicitudControllerTests.TEST_ANUNCIO_ID)).willReturn(this.anuncio);
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver);
		BDDMockito.given(this.solicitudService.existSolicitudAnuncioByBeaver(this.beaver, this.anuncio)).willReturn(true);
		BDDMockito.given(this.solicitudService.isCollectionAllURL(ArgumentMatchers.any(Solicitud.class))).willReturn(true);

		this.solicitudAnunciosHasErrors1Y3();
	}

	public void solicitudAnunciosHasErrors1Y3() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/solicitudes/{anuncioId}/new", SolicitudControllerTests.TEST_ANUNCIO_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("estado", "PENDIENTE").param("precio", "11.0").param("descripcion",
			"Esta es la descripcion")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("accesoNoAutorizado"));
	}

	@Test
	@WithMockUser(value = "spring")
	public void testProcessCrearSolicitudAnunciosHasErrors4() throws Exception {
		BDDMockito.given(this.anuncioService.findAnuncioById(SolicitudControllerTests.TEST_ANUNCIO_ID)).willReturn(this.anuncio);
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver);

		this.mockMvc.perform(MockMvcRequestBuilders.post("/solicitudes/{anuncioId}/new", SolicitudControllerTests.TEST_ANUNCIO_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("descripcion", "")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("solicitud")).andExpect(MockMvcResultMatchers.view().name("solicitudes/creationFormAnuncios"));
	}

}
