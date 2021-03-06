
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.*;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.BeaverService;
import org.springframework.samples.petclinic.service.EncargoService;
import org.springframework.samples.petclinic.service.FacturaService;
import org.springframework.samples.petclinic.service.SolicitudService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EncargoController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
@AutoConfigureMockMvc
public class EncargoControllerTests {

	@Autowired
	private MockMvc				mockMvc;

	@MockBean
	private EncargoService		encargoService;

	@MockBean
	private BeaverService		beaverService;

	@MockBean
	private SolicitudService		solicitudService;

	@MockBean
	private AuthoritiesService		authoritiesService;

	@MockBean
	private FacturaService		facturaService;

	private static final int	TEST_BEAVER_ID		= 99;

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
		Authorities au = new Authorities();
		Set<Authorities> col = new HashSet<>();
		col.add(au);
		au.setAuthority("user");
		au.setUser(user);
		user.setAuthorities(col);
		user.setEnabled(true);
		beaver.setUser(user);

		beaver.setEncargos(new HashSet<>());
		List<Authorities> lista = new ArrayList<Authorities>();
		lista.add(au);

		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver);
		BDDMockito.given(this.beaverService.findBeaverByIntId(beaver.getId())).willReturn(beaver);
		BDDMockito.given(this.beaverService.findUserAuthorities(user)).willReturn(lista);
		BDDMockito.doNothing().when(this.facturaService).saveFactura(ArgumentMatchers.any());
	}

	public void createBeaverWithEncargo() {
		Beaver beaver = new Beaver();
		beaver.setFirstName("Nombre2");
		beaver.setLastName("Apellidos");
		beaver.setEmail("vali2d@gmail.com");
		beaver.setDni("12345678Q");
		beaver.setId(7);
		User user = new User();
		user.setUsername("User12");
		user.setPassword("supersecretpass");
		user.setEnabled(true);
		beaver.setUser(user);

		Encargo e = new Encargo();
		Set<Encargo> s = new HashSet<>();
		s.add(e);
		beaver.setEncargos(s);
		this.beaverService.saveBeaver(beaver);
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver);
	}

	public void createBeaverWithEncargoAndGetId() {
		Beaver beaver = new Beaver();
		beaver.setFirstName("Nombre2");
		beaver.setLastName("Apellidos");
		beaver.setEmail("vali2d@gmail.com");
		beaver.setDni("12345678Q");
		beaver.setId(7);
		User user = new User();
		user.setUsername("User12");
		user.setPassword("supersecretpass");
		user.setEnabled(true);
		beaver.setUser(user);

		Encargo e = new Encargo();
		Set<Encargo> s = new HashSet<>();
		s.add(e);
		beaver.setEncargos(s);
		this.beaverService.saveBeaver(beaver);
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver);
		BDDMockito.given(this.beaverService.findBeaverByIntId(beaver.getId())).willReturn(beaver);
	}
	

	@WithMockUser(value = "User123")
	@Test
	public void testInitCreationSucces() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/encargos/new", EncargoControllerTests.TEST_BEAVER_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("encargos/createEncargosForm"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("encargo"));
	}

	@WithMockUser(value = "User12")
	@Test
	public void testInitCreationNoSucces() throws Exception {

		Beaver beaver = new Beaver();
		beaver.setFirstName("Nombre2");
		beaver.setLastName("Apellidos");
		beaver.setEmail("vali2d@gmail.com");
		beaver.setDni("12345678Q");
		Collection<Especialidad> a = new ArrayList<>();
		beaver.setEspecialidades(a);
		User user = new User();
		user.setUsername("User12");
		user.setPassword("supersecretpass");
		user.setEnabled(true);
		beaver.setUser(user);

		beaver.setEncargos(new HashSet<>());
		this.beaverService.saveBeaver(beaver);
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/encargos/new", 7)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("encargos/listEncargos"));

	}

	@WithMockUser(value = "User123")
	@Test
	public void testPostCreationSucces() throws Exception {

		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/beavers/{beaverId}/encargos/new", EncargoControllerTests.TEST_BEAVER_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("titulo", "Encargo Pinturas").param("id", "60").param("precio", "35.50")
				.param("disponibilidad", "true").param("descripcion", "Descripci??n del encargo de las pinturas del nuevo Beaver a 35 euros")
				.param("photo", "https://previews.123rf.com/images/max5799/max57991508/max5799150800006/44259458-paisaje-de-la-pintura-al-%C3%B3leo-ramo-de-flores-en-el-fondo-del-mar-mediterr%C3%A1neo-cerca-de-las-monta%C3%B1as-oast.jpg"))
			.andExpect(MockMvcResultMatchers.view().name("redirect:/beavers/99/encargos/60"));

	}

	@WithMockUser(value = "User123")
	@Test
	public void testPostCreationResultErrors() throws Exception {

		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/beavers/{beaverId}/encargos/new", EncargoControllerTests.TEST_BEAVER_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("titulo", "").param("id", "60").param("precio", "35.50")
				.param("disponibilidad", "true").param("descripcion", "Descripci??n del encargo de las pinturas del nuevo Beaver a 35 euros")
				.param("photo", "https://previews.123rf.com/images/max5799/max57991508/max5799150800006/44259458-paisaje-de-la-pintura-al-%C3%B3leo-ramo-de-flores-en-el-fondo-del-mar-mediterr%C3%A1neo-cerca-de-las-monta%C3%B1as-oast.jpg"))
			.andExpect(MockMvcResultMatchers.view().name("encargos/createEncargosForm"));

	}

	@WithMockUser(value = "User12")
	@Test
	public void testPostCreationNoEncargos() throws Exception {

		this.createBeaverWithEncargo();
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/beavers/{beaverId}/encargos/new", 7).with(SecurityMockMvcRequestPostProcessors.csrf()).param("titulo", "Titulo ejemplo").param("id", "60").param("precio", "35.50").param("disponibilidad", "true")
				.param("descripcion", "Descripci??n del encargo de las pinturas del nuevo Beaver a 35 euros")
				.param("photo", "https://previews.123rf.com/images/max5799/max57991508/max5799150800006/44259458-paisaje-de-la-pintura-al-%C3%B3leo-ramo-de-flores-en-el-fondo-del-mar-mediterr%C3%A1neo-cerca-de-las-monta%C3%B1as-oast.jpg"))
			.andExpect(MockMvcResultMatchers.view().name("redirect:/beavers/7/encargos/60"));

	}

	@WithMockUser(value = "User123")
	@Test
	public void testListarEncargoEmpty() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/encargos/list", EncargoControllerTests.TEST_BEAVER_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("encargos/listEncargos"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("hayEncargos"));
	}

	@WithMockUser(value = "User123")
	@Test
	public void testListarEncargo() throws Exception {

		this.createBeaverWithEncargoAndGetId();

		this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/encargos/list", 7)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("encargos/listEncargos"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("encargos"));
	}

    @WithMockUser(value = "User123")
    @Test
    public void testListarEncargoAnotherBeaver() throws Exception {

        Beaver beaver = new Beaver();
        beaver.setFirstName("Nombre2");
        beaver.setLastName("Apellidos");
        beaver.setEmail("vali2d@gmail.com");
        beaver.setDni("12345678Q");
        beaver.setId(7);
        User user = new User();
        user.setUsername("User12");
        user.setPassword("supersecretpass");
        user.setEnabled(true);
        beaver.setUser(user);

        Beaver beaver2 = new Beaver();
        beaver2.setFirstName("Nombre3");
        beaver2.setLastName("Apellidos");
        beaver2.setEmail("vali5d@gmail.com");
        beaver2.setDni("12341678Q");
        beaver2.setId(70);
        User user2 = new User();
        user2.setUsername("User1234");
        user2.setPassword("supersecretpass");
        user2.setEnabled(true);
        beaver2.setUser(user2);

        Encargo e = new Encargo();
        Set<Encargo> s = new HashSet<>();
        s.add(e);
        beaver.setEncargos(s);
        this.beaverService.saveBeaver(beaver);
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver2);
        BDDMockito.given(this.beaverService.findBeaverByIntId(beaver.getId())).willReturn(beaver);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/encargos/list", 7)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("encargos/listEncargos"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("encargos"));
    }

    @WithMockUser(value = "testuser")
    @Test
    public void testListarEncargoUserNull() throws Exception {

        Beaver beaver = new Beaver();
        beaver.setFirstName("Nombre2");
        beaver.setLastName("Apellidos");
        beaver.setEmail("vali2d@gmail.com");
        beaver.setDni("12345678Q");
        beaver.setId(7);
        User user = new User();
        user.setUsername("User12");
        user.setPassword("supersecretpass");
        user.setEnabled(true);
        beaver.setUser(user);

        Encargo e = new Encargo();
        Set<Encargo> s = new HashSet<>();
        s.add(e);
        beaver.setEncargos(s);
        this.beaverService.saveBeaver(beaver);
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(null);
        BDDMockito.given(this.beaverService.findBeaverByIntId(beaver.getId())).willReturn(beaver);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/encargos/list", 7)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("accesoNoAutorizado"));
    }

	@WithMockUser(value = "User123")
	@Test
	public void testMostrarEncargoError() throws Exception {

		Beaver beaver = new Beaver();
		beaver.setFirstName("Nombre2");
		beaver.setLastName("Apellidos");
		beaver.setEmail("vali2d@gmail.com");
		beaver.setDni("12345678Q");
		beaver.setId(7);
		User user = new User();
		user.setUsername("User12");
		user.setPassword("supersecretpass");
		user.setEnabled(true);
		beaver.setUser(user);

		Encargo e = new Encargo();
		e.setId(55);
		Set<Encargo> s = new HashSet<>();
		s.add(e);
		beaver.setEncargos(s);
		this.beaverService.saveBeaver(beaver);
		BDDMockito.given(this.encargoService.findEncargoById(55)).willReturn(e);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/encargos//{encargoId}", EncargoControllerTests.TEST_BEAVER_ID, 55)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("encargos/encargosDetails")).andExpect(MockMvcResultMatchers.model().attributeExists("encargo"));
	}

	@WithMockUser(value = "User123")
	@Test
	public void testMostrarEncargo() throws Exception {

		Beaver beaver = new Beaver();
		beaver.setFirstName("Nombre2");
		beaver.setLastName("Apellidos");
		beaver.setEmail("vali2d@gmail.com");
		beaver.setDni("12345678Q");
		beaver.setId(7);
		User user = new User();
		user.setUsername("User12");
		user.setPassword("supersecretpass");
		Authorities au = new Authorities();
		Set<Authorities> col = new HashSet<>();
		col.add(au);
		au.setAuthority("user");
		au.setUser(user);
		user.setAuthorities(col);
		user.setEnabled(true);
		beaver.setUser(user);

		Encargo e = new Encargo();
		e.setId(55);
		e.setBeaver(beaver);
		Set<Encargo> s = new HashSet<>();
		s.add(e);
		beaver.setEncargos(s);
		this.beaverService.saveBeaver(beaver);
		List<Authorities> lista = new ArrayList<Authorities>();
		lista.add(au);
		BDDMockito.given(this.encargoService.findEncargoById(55)).willReturn(e);
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver);
		BDDMockito.given(this.beaverService.findUserAuthorities(user)).willReturn(lista);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/encargos//{encargoId}", 7, 55)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("encargos/encargosDetails"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("encargo"));
	}

    @WithMockUser(value = "User123")
    @Test
    public void testMostrarEncargoUserAdmin() throws Exception {

        Beaver beaver = new Beaver();
        beaver.setFirstName("Nombre2");
        beaver.setLastName("Apellidos");
        beaver.setEmail("vali2d@gmail.com");
        beaver.setDni("12345678Q");
        beaver.setId(7);
        User user = new User();
        user.setUsername("User12");
        user.setPassword("supersecretpass");
        Authorities au = new Authorities();
        Set<Authorities> col = new HashSet<>();
        col.add(au);
        au.setAuthority("admin");
        au.setUser(user);
        user.setAuthorities(col);
        user.setEnabled(true);
        beaver.setUser(user);

        Beaver beaver2 = new Beaver();
        beaver2.setFirstName("Nombre3");
        beaver2.setLastName("Apellidos");
        beaver2.setEmail("vali5d@gmail.com");
        beaver2.setDni("12341678Q");
        beaver2.setId(70);
        User user2 = new User();
        user2.setUsername("User1234");
        user2.setPassword("supersecretpass");
        user2.setEnabled(true);
        beaver2.setUser(user2);

        Encargo e = new Encargo();
        e.setId(55);
        e.setBeaver(beaver2);
        Set<Encargo> s = new HashSet<>();
        s.add(e);
        beaver2.setEncargos(s);
        this.beaverService.saveBeaver(beaver2);
        List<Authorities> lista = new ArrayList<Authorities>();
        lista.add(au);
        BDDMockito.given(this.encargoService.findEncargoById(55)).willReturn(e);
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver);
        BDDMockito.given(this.beaverService.findUserAuthorities(user)).willReturn(lista);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/encargos//{encargoId}", 70, 55)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("encargos/encargosDetails"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("encargo"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("esAdmin"));
    }

	@WithMockUser(value = "User123")
	@Test
	public void testInitEditSucces() throws Exception {

		Beaver beaver = new Beaver();
		beaver.setFirstName("Nombre2");
		beaver.setLastName("Apellidos");
		beaver.setEmail("vali2d@gmail.com");
		beaver.setDni("12345678Q");
		beaver.setId(7);
		User user = new User();
		user.setUsername("User12");
		user.setPassword("supersecretpass");
		user.setEnabled(true);
		beaver.setUser(user);

		Encargo e = new Encargo();
		e.setId(55);
		e.setBeaver(beaver);
		Set<Encargo> s = new HashSet<>();
		s.add(e);
		beaver.setEncargos(s);
		this.beaverService.saveBeaver(beaver);
		BDDMockito.given(this.encargoService.findEncargoById(55)).willReturn(e);
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/encargos/{encargoId}/edit", 7, 55)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("encargos/createEncargosForm"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("encargo"));
	}

	@WithMockUser(value = "User123")
	@Test
	public void testInitEditAccessDenied() throws Exception {

		Beaver beaver = new Beaver();
		beaver.setFirstName("Nombre2");
		beaver.setLastName("Apellidos");
		beaver.setEmail("vali2d@gmail.com");
		beaver.setDni("12345678Q");
		beaver.setId(7);
		User user = new User();
		user.setUsername("User12");
		user.setPassword("supersecretpass");
		user.setEnabled(true);
		beaver.setUser(user);

		Beaver beaver2 = new Beaver();
		beaver2.setFirstName("Nombre3");
		beaver2.setLastName("Apellidos");
		beaver2.setEmail("vali3d@gmail.com");
		beaver2.setDni("12345618Q");
		beaver2.setId(11);
		User user2 = new User();
		user2.setUsername("User12");
		user2.setPassword("supersecretpass");
		user2.setEnabled(true);
		beaver2.setUser(user);

		Encargo e = new Encargo();
		e.setId(55);
		e.setBeaver(beaver2);
		Set<Encargo> s = new HashSet<>();
		s.add(e);
		beaver2.setEncargos(s);
		this.beaverService.saveBeaver(beaver);
		this.beaverService.saveBeaver(beaver2);
		BDDMockito.given(this.encargoService.findEncargoById(55)).willReturn(e);
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/encargos/{encargoId}/edit", 11, 55)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("accesoNoAutorizado"));
	}

	@WithMockUser(value = "User123")
	@Test
	public void testPostEditSucces() throws Exception {

		Beaver beaver = new Beaver();
		beaver.setFirstName("Nombre2");
		beaver.setLastName("Apellidos");
		beaver.setEmail("vali2d@gmail.com");
		beaver.setDni("12345678Q");
		beaver.setId(7);
		User user = new User();
		user.setUsername("User12");
		user.setPassword("supersecretpass");
		user.setEnabled(true);
		beaver.setUser(user);

		Encargo e = new Encargo();
		e.setId(55);
		e.setBeaver(beaver);
		Set<Encargo> s = new HashSet<>();
		s.add(e);
		beaver.setEncargos(s);
		this.beaverService.saveBeaver(beaver);
		BDDMockito.given(this.encargoService.findEncargoById(55)).willReturn(e);
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver);

		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/beavers/{beaverId}/encargos//{encargoId}/edit", 7, 55).with(SecurityMockMvcRequestPostProcessors.csrf()).param("titulo", "Encargo Pinturas").param("id", "60").param("precio", "35.50")
				.param("disponibilidad", "false").param("descripcion", "Descripci??n del encargo de las pinturas del nuevo Beaver a 35 euros")
				.param("photo", "https://previews.123rf.com/images/max5799/max57991508/max5799150800006/44259458-paisaje-de-la-pintura-al-%C3%B3leo-ramo-de-flores-en-el-fondo-del-mar-mediterr%C3%A1neo-cerca-de-las-monta%C3%B1as-oast.jpg"))
			.andExpect(MockMvcResultMatchers.view().name("redirect:/beavers/{beaverId}/encargos/55"));

	}

	@WithMockUser(value = "User123")
	@Test
	public void testPostEditErrorDisponibilidadTrue() throws Exception {

		Beaver beaver = new Beaver();
		beaver.setFirstName("Nombre2");
		beaver.setLastName("Apellidos");
		beaver.setEmail("vali2d@gmail.com");
		beaver.setDni("12345678Q");
		beaver.setId(7);
		User user = new User();
		user.setUsername("User12");
		user.setPassword("supersecretpass");
		user.setEnabled(true);
		beaver.setUser(user);

		Encargo e = new Encargo();
		e.setDisponibilidad(true);
		e.setId(55);
		e.setBeaver(beaver);
		Set<Encargo> s = new HashSet<>();
		s.add(e);
		beaver.setEncargos(s);
		this.beaverService.saveBeaver(beaver);
		BDDMockito.given(this.encargoService.findEncargoById(55)).willReturn(e);
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver);

		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/beavers/{beaverId}/encargos//{encargoId}/edit", 7, 55).with(SecurityMockMvcRequestPostProcessors.csrf()).param("titulo", "Encargo Pinturas").param("id", "60").param("precio", "35.50")
				.param("disponibilidad", "true").param("descripcion", "Descripci??n del encargo de las pinturas del nuevo Beaver a 35 euros")
				.param("photo", "https://previews.123rf.com/images/max5799/max57991508/max5799150800006/44259458-paisaje-de-la-pintura-al-%C3%B3leo-ramo-de-flores-en-el-fondo-del-mar-mediterr%C3%A1neo-cerca-de-las-monta%C3%B1as-oast.jpg"))
			.andExpect(MockMvcResultMatchers.view().name("encargos/createEncargosForm"));

	}

	@WithMockUser(value = "User123")
	@Test
	public void testPostEditError() throws Exception {

		Beaver beaver = new Beaver();
		beaver.setFirstName("Nombre2");
		beaver.setLastName("Apellidos");
		beaver.setEmail("vali2d@gmail.com");
		beaver.setDni("12345678Q");
		beaver.setId(7);
		User user = new User();
		user.setUsername("User12");
		user.setPassword("supersecretpass");
		user.setEnabled(true);
		beaver.setUser(user);

		Encargo e = new Encargo();
		e.setId(55);
		e.setBeaver(beaver);
		Set<Encargo> s = new HashSet<>();
		s.add(e);
		beaver.setEncargos(s);
		this.beaverService.saveBeaver(beaver);
		BDDMockito.given(this.encargoService.findEncargoById(55)).willReturn(e);
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver);

		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/beavers/{beaverId}/encargos//{encargoId}/edit", 7, 55).with(SecurityMockMvcRequestPostProcessors.csrf()).param("titulo", "").param("id", "60").param("precio", "35.50").param("disponibilidad", "false")
				.param("descripcion", "Descripci??n del encargo de las pinturas del nuevo Beaver a 35 euros")
				.param("photo", "https://previews.123rf.com/images/max5799/max57991508/max5799150800006/44259458-paisaje-de-la-pintura-al-%C3%B3leo-ramo-de-flores-en-el-fondo-del-mar-mediterr%C3%A1neo-cerca-de-las-monta%C3%B1as-oast.jpg"))
			.andExpect(MockMvcResultMatchers.view().name("encargos/createEncargosForm"));

	}

	@WithMockUser(value = "User123")
	@Test
	public void testEncargoDeleteSucces() throws Exception {

		Beaver beaver = new Beaver();
		beaver.setFirstName("Nombre2");
		beaver.setLastName("Apellidos");
		beaver.setEmail("vali2d@gmail.com");
		beaver.setDni("12345678Q");
		beaver.setId(7);
		User user = new User();
		user.setUsername("User12");
		user.setPassword("supersecretpass");
		Authorities au = new Authorities();
		Set<Authorities> col = new HashSet<>();
		col.add(au);
		au.setAuthority("user");
		au.setUser(user);
		user.setAuthorities(col);
		user.setEnabled(true);
		beaver.setUser(user);

		Encargo e = new Encargo();
		e.setId(55);
		e.setBeaver(beaver);
		Set<Encargo> s = new HashSet<>();
		s.add(e);
		beaver.setEncargos(s);
		this.beaverService.saveBeaver(beaver);
		List<Authorities> lista = new ArrayList<Authorities>();
		lista.add(au);

		BDDMockito.given(this.encargoService.findEncargoById(55)).willReturn(e);
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver);
		BDDMockito.given(this.beaverService.findBeaverByIntId(beaver.getId())).willReturn(beaver);
		BDDMockito.given(this.beaverService.findUserAuthorities(user)).willReturn(lista);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/encargos/{encargoId}/delete", 7, 55)).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/beavers/7/misPublicaciones"));
	}

	@WithMockUser(value = "User123")
	@Test
	public void testEncargoDeleteNotAuthorized() throws Exception {

		Beaver beaver = new Beaver();
		beaver.setFirstName("Nombre2");
		beaver.setLastName("Apellidos");
		beaver.setEmail("vali2d@gmail.com");
		beaver.setDni("12345678Q");
		beaver.setId(7);
		User user = new User();
		user.setUsername("User12");
		user.setPassword("supersecretpass");
		Authorities au = new Authorities();
		Set<Authorities> col = new HashSet<>();
		col.add(au);
		au.setAuthority("user");
		au.setUser(user);
		user.setAuthorities(col);
		user.setEnabled(true);
		beaver.setUser(user);

		Encargo e = new Encargo();
		e.setId(55);
		e.setBeaver(beaver);
		Set<Encargo> s = new HashSet<>();
		s.add(e);
		beaver.setEncargos(s);
		this.beaverService.saveBeaver(beaver);
		List<Authorities> lista = new ArrayList<Authorities>();
		lista.add(au);

		BDDMockito.given(this.encargoService.findEncargoById(55)).willReturn(e);
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver);
		BDDMockito.given(this.beaverService.findBeaverByIntId(beaver.getId())).willReturn(beaver);
		BDDMockito.given(this.beaverService.findUserAuthorities(user)).willReturn(lista);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/encargos/{encargoId}/delete", 1, 55)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("accesoNoAutorizado"));
	}

	@WithMockUser(value = "User123")
	@Test
	public void testListarEncargoOtroUsuario() throws Exception {

		this.createBeaverWithEncargoAndGetId();

		this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/encargos/list", 7)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("encargos/listEncargos"));
	}

	@WithMockUser(value = "User123")
	@Test
	public void testEncargoAdminDeleteSucces() throws Exception {

		Beaver beaver = new Beaver();
		beaver.setFirstName("Nombre2");
		beaver.setLastName("Apellidos");
		beaver.setEmail("vali2d@gmail.com");
		beaver.setDni("12345678Q");
		beaver.setId(7);
		User user = new User();
		user.setUsername("User12");
		user.setPassword("supersecretpass");
		Authorities au = new Authorities();
		Set<Authorities> col = new HashSet<>();
		col.add(au);
		au.setAuthority("admin");
		au.setUser(user);
		user.setAuthorities(col);
		user.setEnabled(true);
		beaver.setUser(user);

		Encargo e = new Encargo();
		e.setId(55);
		e.setBeaver(beaver);
		Set<Encargo> s = new HashSet<>();
		s.add(e);
		beaver.setEncargos(s);
		this.beaverService.saveBeaver(beaver);
		Solicitud sol = new Solicitud();
		Collection<Solicitud> sols = new ArrayList<>();
		sols.add(sol);
		e.setSolicitud(sols);
		List<Authorities> lista = new ArrayList<Authorities>();
		lista.add(au);

		BDDMockito.given(this.encargoService.findEncargoById(55)).willReturn(e);
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver);
		BDDMockito.given(this.beaverService.findBeaverByIntId(beaver.getId())).willReturn(beaver);
		BDDMockito.given(this.beaverService.findUserAuthorities(user)).willReturn(lista);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/encargos/{encargoId}/delete", 7, 55)).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/beavers/7/encargos/list"));
	}


    @WithMockUser(value = "User123")
    @Test
    public void testEncargoAdminDeleteSuccesFactura() throws Exception {

        Beaver beaver = new Beaver();
        beaver.setFirstName("Nombre2");
        beaver.setLastName("Apellidos");
        beaver.setEmail("vali2d@gmail.com");
        beaver.setDni("12345678Q");
        beaver.setId(7);
        User user = new User();
        user.setUsername("User12");
        user.setPassword("supersecretpass");
        Authorities au = new Authorities();
        Set<Authorities> col = new HashSet<>();
        col.add(au);
        au.setAuthority("admin");
        au.setUser(user);
        user.setAuthorities(col);
        user.setEnabled(true);
        beaver.setUser(user);

        Encargo e = new Encargo();
        e.setId(55);
        e.setBeaver(beaver);
        Set<Encargo> s = new HashSet<>();
        s.add(e);
        beaver.setEncargos(s);
        this.beaverService.saveBeaver(beaver);
        Solicitud sol = new Solicitud();
        Factura factura = new Factura();
        factura.setSolicitud(sol);
        Collection<Solicitud> sols = new ArrayList<>();
        sols.add(sol);
        e.setSolicitud(sols);
        List<Authorities> lista = new ArrayList<Authorities>();
        lista.add(au);

        BDDMockito.given(this.encargoService.findEncargoById(55)).willReturn(e);
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver);
        BDDMockito.given(this.beaverService.findBeaverByIntId(beaver.getId())).willReturn(beaver);
        BDDMockito.given(this.beaverService.findUserAuthorities(user)).willReturn(lista);
        BDDMockito.given(this.facturaService.findFacturaBySolicitud(sol)).willReturn(factura);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/encargos/{encargoId}/delete", 7, 55)).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.view().name("redirect:/beavers/7/encargos/list"));
    }



}
