
package org.springframework.samples.petclinic.web;

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
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.BeaverService;
import org.springframework.samples.petclinic.service.EncargoService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = EncargoController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class EncargoControllerTests {

	@Autowired
	private MockMvc				mockMvc;

	@MockBean
	private EncargoService		encargoService;

	@MockBean
	private BeaverService		beaverService;

	@MockBean
	private UserService			userService;

	@MockBean
	private AuthoritiesService	authoritiesService;

	@Autowired
	private EncargoController	encargoController;

	private static final int	TEST_BEAVER_ID		= 1;
	private static final int	TEST_ENCARGO_ID		= 1;
	private static final String	TEST_BEAVERUSERNAME	= "beaver1";

	private Beaver				beaver1;


	@BeforeEach
	void setup() {

		User user = new User();
		user.setUsername("beaver1");
		user.setPassword("supersecretpass");
		user.setEnabled(true);

		this.beaver1 = new Beaver();
		this.beaver1.setId(EncargoControllerTests.TEST_BEAVER_ID);
		this.beaver1.setFirstName("beaver1");
		this.beaver1.setId(1);
		this.beaver1.setLastName("Apellidos");
		this.beaver1.setEmail("valid@gmail.com");
		this.beaver1.setEspecialidades("Pintura");
		this.beaver1.setDni("12345678Q");
		this.beaver1.setUser(user);

		BDDMockito.given(this.userService.findUserByUsername("Apellidos")).willReturn(user);
		BDDMockito.given(this.beaverService.findBeaverByIntId(EncargoControllerTests.TEST_BEAVER_ID)).willReturn(this.beaver1);
		BDDMockito.given(this.beaverService.findBeaverByIntId(ArgumentMatchers.anyInt())).willReturn(this.beaver1);

	}

	@WithMockUser("beaver1")
	@Test
	public void listarEncargosTest() throws Exception {

		System.out.println(this.beaver1.getId());
		System.out.println(this.beaver1.getFirstName());
		this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/list", EncargoControllerTests.TEST_BEAVER_ID)).andExpect(MockMvcResultMatchers.status().isOk());

	}

	@WithMockUser(value = "testuser")
	@Test
	public void testProcessCreationSuccess() throws Exception {

		Mockito.when(this.beaverService.findBeaverByIntId(ArgumentMatchers.anyInt())).thenReturn(this.beaver1);
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/beavers/{beaverId}/encargos/new", EncargoControllerTests.TEST_BEAVER_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("titulo", "Encargo Pinturas").param("precio", "35.50")
				.param("disponibilidad", "true").param("descripcion", "Descripción del encargo de las pinturas del nuevo Beaver a 35 euros")
				.param("photo", "https://previews.123rf.com/images/max5799/max57991508/max5799150800006/44259458-paisaje-de-la-pintura-al-%C3%B3leo-ramo-de-flores-en-el-fondo-del-mar-mediterr%C3%A1neo-cerca-de-las-monta%C3%B1as-oast.jpg")
				.param("beaver.id", Integer.toString(EncargoControllerTests.TEST_BEAVER_ID)))
			/*
			 * .param("beaver.email", this.beaver1.getEmail())
			 * .param("beaver.especialidades", "test@gmail.com")
			 * .param("beaver.dni", "50506312Z")
			 * .param("beaver.person.firstname", "TestName")
			 * .param("beaver.person.lastname", "TestLastName")
			 * .param("beaver.user.username", "beaver2")
			 * .param("beaver.user.password", "superpasssecret")
			 * .param("beaver.user.enabled", "true"))
			 */
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/beavers/" + EncargoControllerTests.TEST_BEAVER_ID));
	}

	@WithMockUser(value = "testuser")
	@Test
	public void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/beavers/{beaverId}/encargos/nuevo", EncargoControllerTests.TEST_BEAVER_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("titulo", "Encargo Pinturas").param("precio", "40.0")
				.param("disponibilidad", "true").param("descripcion", "Descripción del encargo de las pinturas del nuevo Beaver a 35 euros"))
			//.param("photo", "https://previews.123rf.com/images/max5799/max57991508/max5799150800006/44259458-paisaje-de-la-pintura-al-%C3%B3leo-ramo-de-flores-en-el-fondo-del-mar-mediterr%C3%A1neo-cerca-de-las-monta%C3%B1as-oast.jpg"))
			//.andExpect(model().attributeHasErrors("encargo"))
			//.andExpect(model().attributeHasFieldErrors("encargo", "precio"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("encargos/nuevo"));
	}

}
