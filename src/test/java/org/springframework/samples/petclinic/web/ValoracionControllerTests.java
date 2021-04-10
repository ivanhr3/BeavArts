package org.springframework.samples.petclinic.web;

import static org.mockito.Mockito.doNothing;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
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
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Especialidad;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Valoracion;
import org.springframework.samples.petclinic.service.BeaverService;
import org.springframework.samples.petclinic.service.ValoracionService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
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

		this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/valoraciones/list", ValoracionControllerTests.TEST_BEAVER_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("valoracion/lista"))
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
		this.beaverService.saveBeaver(beaver);
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver);
		BDDMockito.given(this.beaverService.findBeaverByIntId(beaver.getId())).willReturn(beaver);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/valoraciones/list", 7)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("valoracion/lista"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("valoraciones"));
	}

	@WithMockUser(value= "spring")
	@Test
	public void initCreationFormConExito() throws Exception{
		User user = new User();
		user.setUsername("Reciever");
		user.setPassword("supersecretpass");
		user.setEnabled(true);

		Beaver beaver = new Beaver();
		beaver.setFirstName("Nombre");
		beaver.setLastName("Apellidos");
		beaver.setEmail("valid2@gmail.com");
		beaver.setId(98);
		Collection<Especialidad> espe = new HashSet<>();
		espe.add(Especialidad.ESCULTURA);
		beaver.setEspecialidades(espe);
		beaver.setDni("12345678F");
		beaver.setUser(user);

		BDDMockito.given(beaverService.findBeaverByIntId(Mockito.anyInt())).willReturn(beaver);

		this.mockMvc
		.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/valoraciones/create", 98))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attribute("myBeaverId", 99))
		.andExpect(MockMvcResultMatchers.model().attributeExists("valoracion"))
		.andExpect(MockMvcResultMatchers.view().name("valoracion/createValoracion")); //TODO: Front avisad cuando cambieis el nombre de la vista

	}

	@WithMockUser(value= "spring")
	@Test
	public void initCreationFormUserValorarseSiMismoFallo() throws Exception{

		this.mockMvc
		.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/valoraciones/create", 99))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("accesoNoAutorizado")); //TODO: Front avisad cuando cambieis el nombre de la vista

	}

	@WithMockUser(value = "spring")
	@Test
	public void initCreationFormAnonimoValoraFallo() throws Exception{
		BDDMockito.given(beaverService.getCurrentBeaver()).willReturn(null);

		this.mockMvc
		.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/valoraciones/create", 99))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("accesoNoAutorizado")); //TODO: Front avisad cuando cambieis el nombre de la vista

	}

	@Test
	public void initCreationFormAnonimoValora2Fallo() throws Exception{
		BDDMockito.given(beaverService.getCurrentBeaver()).willReturn(null);

		this.mockMvc
		.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/valoraciones/create", 99))
		.andExpect(MockMvcResultMatchers.status().isUnauthorized()); //TODO: Front avisad cuando cambieis el nombre de la vista

	}
	
	@WithMockUser(value = "spring")
	@Test
	public void processCreationFormExito() throws Exception{
		User user = new User();
		user.setUsername("Reciever");
		user.setPassword("supersecretpass");
		user.setEnabled(true);

		Beaver beaver = new Beaver();
		beaver.setFirstName("Nombre");
		beaver.setLastName("Apellidos");
		beaver.setEmail("valid2@gmail.com");
		beaver.setId(98);
		Collection<Especialidad> espe = new HashSet<>();
		espe.add(Especialidad.ESCULTURA);
		beaver.setEspecialidades(espe);
		beaver.setDni("12345678F");
		beaver.setUser(user);

		BDDMockito.given(beaverService.findBeaverByIntId(Mockito.anyInt())).willReturn(beaver);
		BDDMockito.doNothing().when(this.valoracionService).crearValoracion(ArgumentMatchers.any(Valoracion.class), ArgumentMatchers.any(Beaver.class));

		this.mockMvc.perform(
			MockMvcRequestBuilders.post("/beavers/{beaverId}/valoraciones/create", 98)
			.with(SecurityMockMvcRequestPostProcessors.csrf())
			.param("puntuacion", "3.5")
			.param("comentario", "Buen vendedor, mejor persona"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/beavers/beaverInfo/"+98)); //TODO FRONT: AVISAD CUANDO TENGAIS EL NOMBRE DE LA VISTA
	}

	@WithMockUser(value = "spring")
	@Test
	public void processCreationFormConErrores() throws Exception{
		User user = new User();
		user.setUsername("Reciever");
		user.setPassword("supersecretpass");
		user.setEnabled(true);

		Beaver beaver = new Beaver();
		beaver.setFirstName("Nombre");
		beaver.setLastName("Apellidos");
		beaver.setEmail("valid2@gmail.com");
		beaver.setId(98);
		Collection<Especialidad> espe = new HashSet<>();
		espe.add(Especialidad.ESCULTURA);
		beaver.setEspecialidades(espe);
		beaver.setDni("12345678F");
		beaver.setUser(user);

		BDDMockito.given(beaverService.findBeaverByIntId(Mockito.anyInt())).willReturn(beaver);
		BDDMockito.doNothing().when(this.valoracionService).crearValoracion(ArgumentMatchers.any(Valoracion.class), ArgumentMatchers.any(Beaver.class));

		this.mockMvc.perform(
			MockMvcRequestBuilders.post("/beavers/{beaverId}/valoraciones/create", 98)
			.with(SecurityMockMvcRequestPostProcessors.csrf())
			.param("puntuacion", "2.5") //Las puntuaciones en el front no pueden ser menores a 1 ni exceder de 5
			.param("comentario", "Corto")) //Tiene menos de 10 caracteres
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("valoracion"))
			.andExpect(MockMvcResultMatchers.view().name("valoracion/createValoracion")); //TODO FRONT: AVISAD CUANDO TENGAIS EL NOMBRE DE LA VISTA
	}

	@WithMockUser(value= "spring")
	@Test
	public void processCreationFormUserValorarseSiMismoFallo() throws Exception{

		this.mockMvc
		.perform(MockMvcRequestBuilders.post("/beavers/{beaverId}/valoraciones/create", 99)
		.with(SecurityMockMvcRequestPostProcessors.csrf())
		.param("puntuacion", "2.5")
		.param("comentario", "Buen vendedor, mejor persona. Trust me Bro."))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("accesoNoAutorizado")); //TODO: Front avisad cuando cambieis el nombre de la vista

	}

	@WithMockUser(value= "spring")
	@Test
	public void processCreationFormUserAnonimoValoraFallo() throws Exception{

		BDDMockito.given(beaverService.getCurrentBeaver()).willReturn(null);

		this.mockMvc
		.perform(MockMvcRequestBuilders.post("/beavers/{beaverId}/valoraciones/create", 99)
		.with(SecurityMockMvcRequestPostProcessors.csrf())
		.param("puntuacion", "2.5")
		.param("comentario", "Buen vendedor, mejor persona. Trust me Bro."))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("accesoNoAutorizado")); //TODO: Front avisad cuando cambieis el nombre de la vista

	}

	@Test
	public void processCreationFormUserAnonimo2ValoraFallo() throws Exception{

		BDDMockito.given(beaverService.getCurrentBeaver()).willReturn(null);

		this.mockMvc
		.perform(MockMvcRequestBuilders.post("/beavers/{beaverId}/valoraciones/create", 99)
		.with(SecurityMockMvcRequestPostProcessors.csrf())
		.param("puntuacion", "2.5")
		.param("comentario", "Buen vendedor, mejor persona. Trust me Bro."))
		.andExpect(MockMvcResultMatchers.status().isUnauthorized()); //TODO: Front avisad cuando cambieis el nombre de la vista

	}
}
