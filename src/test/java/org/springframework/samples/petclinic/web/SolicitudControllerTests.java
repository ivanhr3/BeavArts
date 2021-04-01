
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
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Encargo;
import org.springframework.samples.petclinic.model.Especialidad;
import org.springframework.samples.petclinic.model.Estados;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.BeaverService;
import org.springframework.samples.petclinic.service.EncargoService;
import org.springframework.samples.petclinic.service.SolicitudService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WebMvcTest(value = SolicitudController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
@AutoConfigureMockMvc
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
	private AuthoritiesService	authoritiesService;

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

	private static final int	TEST_BEAVER_ID			= 1;
	private static final int	TEST_USER_ID			= 1;
	private static final int	TEST_AUTHORITIES_ID		= 1;
	private static final int	TEST_ENCARGO_ID			= 1;

	private static final int	TEST_BEAVER_ID2			= 2;
	private static final int	TEST_AUTHORITIES_ID2	= 2;

	private static final int	TEST_SOLICITUD_ID		= 1;


	@BeforeEach
	void setup() {
		this.beaver = new Beaver();
		this.beaver.setId(SolicitudControllerTests.TEST_BEAVER_ID);
		this.beaver.setFirstName("Nombre");
		this.beaver.setLastName("Apellidos");
		this.beaver.setEmail("valid@gmail.com");
		Collection<Especialidad> especialidad = new HashSet<Especialidad>();
		especialidad.add(Especialidad.FOTOGRAFÍA);
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
		this.encargo.setPrecio(199);
		this.encargo.setBeaver(this.beaver);

		this.solicitud = new Solicitud();
		this.solicitud.setId(SolicitudControllerTests.TEST_SOLICITUD_ID);
		this.solicitud.setEstado(Estados.PENDIENTE);
		this.solicitud.setEncargo(this.encargo);
		this.solicitud.setBeaver(this.beaver2);
		this.solicitud.setPrecio(199);
		this.solicitud.setDescripcion("descripcion");
		this.solicitudService.saveSolicitud(this.solicitud);

		List<Solicitud> list = new ArrayList<Solicitud>();
		list.add(this.solicitud);
		this.encargo.setSolicitud(list);
		List<Encargo> list2 = new ArrayList<Encargo>();
		list2.add(this.encargo);
		this.beaver.setEncargos(list2);

		BDDMockito.given(this.solicitudService.findById(ArgumentMatchers.anyInt())).willReturn(this.solicitud);
		BDDMockito.given(this.encargoService.findEncargoById(SolicitudControllerTests.TEST_ENCARGO_ID)).willReturn(this.encargo);
		BDDMockito.given(this.userService.findUserByUsername("spring")).willReturn(this.user);
	}

	@WithMockUser(value = "spring")
	@Test
	void testAceptarSolicitud() throws Exception {
		Mockito.doNothing().when(this.emailSender).sendEmail(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/accept/{solId}", SolicitudControllerTests.TEST_SOLICITUD_ID))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("solicitudes/listadoSolicitudes"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testRechazarSolicitud() throws Exception {
		Mockito.doNothing().when(this.emailSender).sendEmail(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/decline/{solId}", SolicitudControllerTests.TEST_SOLICITUD_ID))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("solicitudes/listadoSolicitudes"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testAceptarSolicitudWithWrongUser() throws Exception {
		Mockito.doNothing().when(this.emailSender).sendEmail(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver2);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/accept/{solId}", SolicitudControllerTests.TEST_SOLICITUD_ID))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("solicitudes/errorAceptar"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testRechazarSolicitudWithWrongUser() throws Exception {
		Mockito.doNothing().when(this.emailSender).sendEmail(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver2);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/decline/{solId}", SolicitudControllerTests.TEST_SOLICITUD_ID))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("solicitudes/errorRechazar"));
	}

	@Test
	@WithMockUser(value = "spring") //Model attribute listaSolicitudes no existe, no encaja el back con el front
	public void listarSolicitudesTest() throws Exception {
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/list")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("hayEncargos"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("haySolicitudes")).andExpect(MockMvcResultMatchers.model().attributeExists("listaSolicitudesRecibidas"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("listaSolicitudesEnviadas")).andExpect(MockMvcResultMatchers.view().name("solicitudes/listadoSolicitudes"));
	}

	@Test
	@WithMockUser(value = "spring")
	public void mostrarSolicitudesTest() throws Exception {
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver);
		Optional<Solicitud> sol = Optional.of(solicitud);
		BDDMockito.given(this.solicitudService.findSolicitudById(ArgumentMatchers.anyInt())).willReturn(sol);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/solicitudInfo/{solicitudId}", SolicitudControllerTests.TEST_SOLICITUD_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("solicitudes/solicitudesDetails"));
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

		this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/{engId}/create", SolicitudControllerTests.TEST_ENCARGO_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("solicitudes/creationForm"));
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
	public void crearSolicitudPost() throws Exception {
		BDDMockito.given(this.encargoService.findEncargoById(SolicitudControllerTests.TEST_ENCARGO_ID)).willReturn(this.encargo);
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver2);
		BDDMockito.given(this.solicitudService.existSolicitudByBeaver(this.beaver2, this.encargo)).willReturn(false);
		BDDMockito.given(this.solicitudService.isCollectionAllURL(ArgumentMatchers.any(Solicitud.class))).willReturn(true);

		this.mockMvc.perform(MockMvcRequestBuilders.post("/solicitudes/{engId}/create", SolicitudControllerTests.TEST_ENCARGO_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("descripcion", "esta es la descripcion"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("solicitudes/solicitudSuccess"));
	}
}
