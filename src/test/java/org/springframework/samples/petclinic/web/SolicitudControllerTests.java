
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.samples.petclinic.model.Encargo;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.SolicitudService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WebMvcTest(value = SolicitudController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
@AutoConfigureMockMvc

class SolicitudControllerTests {

	@Autowired
	private MockMvc				mockMvc;

	@MockBean
	private SolicitudService	solicitudService;

	@MockBean
	private UserService			userService;

	@MockBean
	private AuthoritiesService	authoritiesService;

	private static final int	TEST_SOLICITUD_ID	= 1;


	@BeforeEach
	void setup() {

		User user1 = new User();
		user1.setUsername("testuser");
		user1.setApellidos("Perez");
		user1.setNombre("testuser");
		user1.setPassword("pass123");

		Beaver beaver1 = new Beaver();
		beaver1.setDni("29519811N");
		beaver1.setEmail("testemail@hotmail.com");
		beaver1.setEspecialidades("Pintura");
		beaver1.setFirstName("testbeaver");
		beaver1.setLastName("Perez");
		beaver1.setUser(user1);

		user1.setBeaver(beaver1);

		Encargo encargo1 = new Encargo();
		encargo1.setBeaver(beaver1);
		encargo1.setDescripcion("Encargo1 correcto");
		encargo1.setTitulo("Encargo1");
		encargo1.setPrecio(50);
		encargo1.setDisponibilidad(true);

		Encargo encargo2 = new Encargo();
		encargo1.setDescripcion("Encargo2 no");
		encargo1.setTitulo("Encargo2");
		encargo1.setPrecio(30);
		encargo1.setDisponibilidad(true);

		Solicitud solicitud1 = new Solicitud();
		solicitud1.setEncargo(encargo1);
		solicitud1.setEstado(true);
		solicitud1.setPrecio(50);
		solicitud1.setId(1);

		Solicitud solicitud2 = new Solicitud();
		solicitud2.setEncargo(encargo1);
		solicitud2.setEstado(true);
		solicitud2.setPrecio(50);
		solicitud2.setId(2);

		List<Solicitud> list = new ArrayList<Solicitud>();
		list.add(solicitud1);
		list.add(solicitud2);
		encargo1.setSolicitudes(list);
		List<Encargo> list2 = new ArrayList<Encargo>();
		list2.add(encargo1);
		beaver1.setEncargos(list2);

		Solicitud solicitud3 = new Solicitud();
		solicitud3.setEncargo(encargo2);
		solicitud3.setEstado(true);
		solicitud3.setPrecio(50);
		solicitud3.setId(3);

		BDDMockito.given(this.userService.findUserByUsername("testuser")).willReturn(user1);
	}

	@Test
	@WithMockUser("testuser")
	public void listarSolicitudesTest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/list")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("listaSolicitudes"))
			.andExpect(MockMvcResultMatchers.view().name("solicitudes/listadoSolicitudes"));
	}

	@Test
	@WithMockUser("testuser")
	public void mostrarSolicitudesTest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/solicitudes/solicitudInfo/{solicitudId}", SolicitudControllerTests.TEST_SOLICITUD_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("solicitud")).andExpect(MockMvcResultMatchers.view().name("solicitudes/solicitudesDetails"));
	}
}
