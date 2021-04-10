package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
import org.springframework.samples.petclinic.model.Valoracion;
import org.springframework.samples.petclinic.service.BeaverService;
import org.springframework.samples.petclinic.service.ValoracionService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ValoracionController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
@AutoConfigureMockMvc
public class ValoracionControllerTests {
    
    @Autowired
	private MockMvc				mockMvc;

	@MockBean
    private ValoracionService	valoracionService; 

	@MockBean
	private BeaverService		beaverService;

	private static final int	TEST_BEAVER_ID		= 99;

	@BeforeEach
	public void setUp() {
        User user = new User();
		user.setUsername("User123");
		user.setPassword("supersecretpass");
		user.setEnabled(true);

		Beaver beaver = new Beaver();
		beaver.setFirstName("Nombre");
		beaver.setLastName("Apellidos");
		beaver.setEmail("valid@gmail.com");
		beaver.setId(99);
		Collection<Especialidad> espe = new HashSet<>();
		espe.add(Especialidad.ESCULTURA);
		beaver.setEspecialidades(espe);
		beaver.setDni("12345678Q");
		beaver.setUser(user);

		beaver.setValoraciones(new HashSet<>());

		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver);
		BDDMockito.given(this.beaverService.findBeaverByIntId(beaver.getId())).willReturn(beaver);

    } 

    @WithMockUser(value = "User123")
	@Test
	public void testListValoracionesEmpty() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/valoraciones/list", ValoracionControllerTests.TEST_BEAVER_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name(""))
			.andExpect(MockMvcResultMatchers.model().attributeExists("hayValoraciones"));
	}

    @WithMockUser(value = "User123")
	@Test
	public void testListValoraciones() throws Exception {

        User user = new User();
		user.setUsername("User12");
		user.setPassword("supersecretpass");
		user.setEnabled(true);

		Beaver beaver = new Beaver();
		beaver.setFirstName("Nombre2");
		beaver.setLastName("Apellidos");
		beaver.setEmail("vali2d@gmail.com");
		beaver.setDni("12345678Q");
		beaver.setId(7);
		beaver.setUser(user);

		Valoracion v = new Valoracion();
        v.setPuntuacion(3);
        v.setComentario("Que comentario más chulo");
		Set<Valoracion> valoraciones = new HashSet<>();
		valoraciones.add(v);
		beaver.setValoraciones(valoraciones);
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver);
		BDDMockito.given(this.beaverService.findBeaverByIntId(beaver.getId())).willReturn(beaver);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/valoraciones/list", 7)).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("")).andExpect(MockMvcResultMatchers.model().attributeExists("valoraciones"));
	}
}
