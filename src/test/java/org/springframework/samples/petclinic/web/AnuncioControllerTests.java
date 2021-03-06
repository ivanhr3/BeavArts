
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import org.springframework.samples.petclinic.service.AnuncioService;
import org.springframework.samples.petclinic.service.BeaverService;
import org.springframework.samples.petclinic.service.EncargoService;
import org.springframework.samples.petclinic.service.FacturaService;
import org.springframework.samples.petclinic.service.SolicitudService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(value = AnuncioController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
@AutoConfigureMockMvc
public class AnuncioControllerTests {

	@Autowired
	private MockMvc						mockMvc;

	@MockBean
	private AnuncioService				anuncioService;

	@MockBean
	private BeaverService				beaverService;

	@MockBean
	private EncargoService				encargoService;

	@MockBean
	private SolicitudService			solicitudService;

	@MockBean
	private FacturaService				facturaService;

	private static final int			TEST_BEAVER_ID		= 99;
	private static final int			TEST_BEAVER_ID2		= 100;
	private static final int			TEST_ANUNCIO_ID		= 90;
	private static final int			TEST_ANUNCIO_ID2	= 91;
	private static final int			TEST_ANUNCIO_ID3	= 92;
	private static final Especialidad	TEST_ESPECIALIDAD	= Especialidad.ESCULTURA;

	private Beaver						beaver;
	private Beaver						beaver2;
    private Beaver						beaver3;
	private Anuncio						anuncio;
	private Anuncio						anuncio2;
	private Anuncio						anuncio3;
	private Solicitud					solicitud;
	private List<Anuncio>				listaAnunciosPorEspecialidad;
	private List<Anuncio>				listaAnunciosDestacados;
	private List<Anuncio>				listaAnunciosNoDestacados;


	@BeforeEach
	public void setUp() {
		this.beaver = new Beaver();
		this.beaver.setFirstName("Nombre");
		this.beaver.setLastName("Apellidos");
		this.beaver.setEmail("valid@gmail.com");
		this.beaver.setId(99);
		Collection<Especialidad> espe = new HashSet<>();
		espe.add(Especialidad.ESCULTURA);
		this.beaver.setEspecialidades(espe);
		this.beaver.setDni("12345678Q");
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
		this.beaver.setUser(user);
		this.beaver.setEncargos(new HashSet<>());

		this.beaver2 = new Beaver();
		this.beaver2.setFirstName("Nombre");
		this.beaver2.setLastName("Apellidos");
		this.beaver2.setEmail("valid@gmail.com");
		Collection<Especialidad> espe2 = new HashSet<>();
		espe2.add(Especialidad.ESCULTURA);
		this.beaver2.setEspecialidades(espe2);
		this.beaver2.setDni("12345678X");
		this.beaver2.setId(100);

		User user2 = new User();
		user2.setUsername("User123456");
		user2.setPassword("supersecretpass2");
		Authorities au2 = new Authorities();
		Set<Authorities> col2 = new HashSet<>();
		col2.add(au2);
		au2.setAuthority("user");
		au2.setUser(user2);
		user2.setAuthorities(col2);
		user2.setEnabled(true);
		this.beaver2.setUser(user2);

        this.beaver3 = new Beaver();
        this.beaver3.setFirstName("Nombre");
        this.beaver3.setLastName("Apellidos");
        this.beaver3.setEmail("valid3@gmail.com");
        Collection<Especialidad> espe3 = new HashSet<>();
        espe3.add(Especialidad.ESCULTURA);
        this.beaver3.setEspecialidades(espe2);
        this.beaver3.setDni("12345178X");
        this.beaver3.setId(109);

        User user3 = new User();
        user3.setUsername("User23456");
        user3.setPassword("supersecretpass22");
        Authorities au3 = new Authorities();
        Set<Authorities> col3 = new HashSet<>();
        col3.add(au3);
        au3.setAuthority("user");
        au3.setUser(user2);
        user3.setAuthorities(col3);
        user3.setEnabled(true);
        this.beaver2.setUser(user3);

		this.anuncio = new Anuncio();
		this.anuncio.setBeaver(this.beaver);
		this.anuncio.setDescripcion("Esto es una descripci??n");
		this.anuncio.setPrecio(50.0);
		this.anuncio.setTitulo("Esto es un t??tulo");
		this.anuncio.setDestacado(false);
		this.anuncio.setEspecialidad(Especialidad.ESCULTURA);
		this.anuncio.setId(90);

		this.anuncio2 = new Anuncio();
		this.anuncio2.setBeaver(this.beaver);
		this.anuncio2.setDescripcion("Esto es una descripci??n 2");
		this.anuncio2.setPrecio(40.0);
		this.anuncio2.setTitulo("Esto es un t??tulo 2");
		this.anuncio2.setDestacado(true);
		this.anuncio2.setEspecialidad(Especialidad.RESINA);
		this.anuncio2.setId(91);

		this.anuncio3 = new Anuncio();
		this.anuncio3.setBeaver(this.beaver2);
		this.anuncio3.setDescripcion("Esto es una descripci??n 2");
		this.anuncio3.setPrecio(40.0);
		this.anuncio3.setTitulo("Esto es un t??tulo 2");
		this.anuncio3.setDestacado(false);
		this.anuncio3.setEspecialidad(Especialidad.RESINA);
		this.anuncio3.setId(92);

        Solicitud sol = new Solicitud();
        Factura factura = new Factura();
        factura.setSolicitud(sol);
        Collection<Solicitud> cole = new ArrayList();
        cole.add(sol);
        this.anuncio3.setSolicitud(cole);

		this.solicitud = new Solicitud();
		this.solicitud.setPrecio(50.0);
		this.solicitud.setAnuncio(this.anuncio2);
		this.solicitud.setDescripcion("Esto es una descripci??n");
		this.solicitud.setEstado(Estados.ACEPTADO);
		Collection<Solicitud> solicitudes = new HashSet<>();
		solicitudes.add(this.solicitud);
		this.anuncio2.setSolicitud(solicitudes);

		this.listaAnunciosPorEspecialidad = new ArrayList<>();
		this.listaAnunciosPorEspecialidad.add(this.anuncio);

		this.listaAnunciosDestacados = new ArrayList<>();
		this.listaAnunciosDestacados.add(this.anuncio2);

		this.listaAnunciosNoDestacados = new ArrayList<>();
		this.listaAnunciosNoDestacados.add(this.anuncio);
		this.listaAnunciosNoDestacados.add(this.anuncio3);

		Collection<Anuncio> anunciosBeaver1 = new HashSet<>();
		anunciosBeaver1.add(this.anuncio);
		anunciosBeaver1.add(this.anuncio2);
		this.beaver.setAnuncios(anunciosBeaver1);
		List<Authorities> lista = new ArrayList<Authorities>();
		lista.add(au);

		BDDMockito.given(this.anuncioService.findAnuncioById(AnuncioControllerTests.TEST_ANUNCIO_ID)).willReturn(this.anuncio);
		BDDMockito.given(this.anuncioService.findAnuncioById(AnuncioControllerTests.TEST_ANUNCIO_ID2)).willReturn(this.anuncio2);
		BDDMockito.given(this.anuncioService.findAnuncioById(AnuncioControllerTests.TEST_ANUNCIO_ID3)).willReturn(this.anuncio3);
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver);
		BDDMockito.given(this.beaverService.findBeaverByIntId(AnuncioControllerTests.TEST_BEAVER_ID)).willReturn(this.beaver);
		BDDMockito.given(this.beaverService.findBeaverByIntId(AnuncioControllerTests.TEST_BEAVER_ID2)).willReturn(this.beaver2);
		BDDMockito.given(this.anuncioService.findAnunciosByEspecialidad(AnuncioControllerTests.TEST_ESPECIALIDAD)).willReturn(this.listaAnunciosPorEspecialidad);
		BDDMockito.given(this.anuncioService.findAnunciosDestacados()).willReturn(this.listaAnunciosDestacados);
		BDDMockito.given(this.anuncioService.findAnunciosNoDestacados()).willReturn(this.listaAnunciosNoDestacados);
		BDDMockito.given(this.beaverService.findUserAuthorities(user)).willReturn(lista);
		BDDMockito.doNothing().when(this.facturaService).saveFactura(ArgumentMatchers.any());

	}

	@WithMockUser(value = "User123")
	@Test
	public void testInitCreationSucces() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/anuncios/new", AnuncioControllerTests.TEST_BEAVER_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("anuncios/createAnunciosForm"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("anuncio"));
	}

	@WithMockUser(value = "User123")
	@Test
	public void testPostCreationSucces() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.post("/beavers/{beaverId}/anuncios/new", AnuncioControllerTests.TEST_BEAVER_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("titulo", "AnuncioTest").param("id", "60").param("precio", "35.50")
			.param("especialidad", "TEXTIL").param("descripcion", "Descripci??n del anuncio de prueba")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/beavers/99/anuncios/60"));

	}

    @WithMockUser(value = "User123")
    @Test
    public void testPostCreationAnunciosNull() throws Exception {
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver3);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/beavers/{beaverId}/anuncios/new", AnuncioControllerTests.TEST_BEAVER_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("titulo", "AnuncioTest").param("id", "60").param("precio", "35.50")
            .param("especialidad", "TEXTIL")
            .param("descripcion", "Descripci??n del anuncio de prueba"))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.view().name("redirect:/beavers/109/anuncios/60"));

    }

	@WithMockUser(value = "User123")
	@Test
	public void testPostCreationHasErrors() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.post("/beavers/{beaverId}/anuncios/new", AnuncioControllerTests.TEST_BEAVER_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("titulo", "AnuncioTest").param("id", "60").param("precio", "")
			.param("especialidad", "TEXTIL").param("descripcion", "Descripci??n del anuncio de prueba")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("anuncios/createAnunciosForm"));

	}

	@WithMockUser(value = "User123")
	@Test
	public void testPostCreationHasErrors2() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.post("/beavers/{beaverId}/anuncios/new", AnuncioControllerTests.TEST_BEAVER_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("titulo", "").param("id", "60").param("precio", "35.50")
			.param("especialidad", "TEXTIL").param("descripcion", "Descripci??n del anuncio de prueba")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("anuncios/createAnunciosForm"));

	}

	// TESTS DE EDITAR ANUNCIO

	@WithMockUser(value = "testuser")
	@Test
	public void testInitUpdateForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/anuncios/{anuncioId}/edit", AnuncioControllerTests.TEST_BEAVER_ID, AnuncioControllerTests.TEST_ANUNCIO_ID).with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("anuncio")).andExpect(MockMvcResultMatchers.view().name("anuncios/createAnunciosForm"));
	}

	@WithMockUser(value = "testuser")
	@Test
	public void testInitUpdateFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/anuncios/{anuncioId}/edit", AnuncioControllerTests.TEST_BEAVER_ID, AnuncioControllerTests.TEST_ANUNCIO_ID2).with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("anuncios/anunciosDetails"));
	}

	@WithMockUser(value = "testuser")
	@Test
	public void testInitUpdateFormHasErrors2() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/anuncios/{anuncioId}/edit", AnuncioControllerTests.TEST_BEAVER_ID, AnuncioControllerTests.TEST_ANUNCIO_ID3).with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("accesoNoAutorizado"));
	}

	@WithMockUser(value = "testuser")
	@Test
	public void testProcessUpdateForm() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/beavers/{beaverId}/anuncios/{anuncioId}/edit", AnuncioControllerTests.TEST_BEAVER_ID, AnuncioControllerTests.TEST_ANUNCIO_ID).with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("titulo", "Cambio en el titulo").param("precio", "49.0").param("descripcion", "Cambio en la descripcion"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/beavers/" + AnuncioControllerTests.TEST_BEAVER_ID + "/anuncios/" + AnuncioControllerTests.TEST_ANUNCIO_ID));
	}

	@WithMockUser(value = "testuser")
	@Test
	public void testProcessUpdateFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/beavers/{beaverId}/anuncios/{anuncioId}/edit", AnuncioControllerTests.TEST_BEAVER_ID, AnuncioControllerTests.TEST_ANUNCIO_ID2).with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.model().attributeExists("anuncio")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("anuncios/createAnunciosForm"));
	}

	// TESTS PARA EL DELETE DE ANUNCIOS

	@WithMockUser(value = "testuser")
	@Test
	public void testDeleteAnuncio() throws Exception {
		Solicitud sol = new Solicitud();
		Collection<Solicitud> col = new ArrayList();
		col.add(sol);
		this.anuncio.setSolicitud(col);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/anuncios/{anuncioId}/delete", AnuncioControllerTests.TEST_BEAVER_ID, AnuncioControllerTests.TEST_ANUNCIO_ID).with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/beavers/" + AnuncioControllerTests.TEST_BEAVER_ID + "/misPublicaciones"));
	}

	@WithMockUser(value = "testuser")
	@Test
	public void testDeleteAnuncioHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/anuncios/{anuncioId}/delete", AnuncioControllerTests.TEST_BEAVER_ID, AnuncioControllerTests.TEST_ANUNCIO_ID2).with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("anuncios/anunciosDetails"));
	}

	@WithMockUser(value = "testuser")
	@Test
	public void testDeleteHasErrors2() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/anuncios/{anuncioId}/delete", AnuncioControllerTests.TEST_BEAVER_ID2, AnuncioControllerTests.TEST_ANUNCIO_ID).with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("accesoNoAutorizado"));
	}

	@WithMockUser(value = "testuser")
	@Test
	public void testPromocionarAnuncio() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/beavers/{beaverId}/anuncios/{anuncioId}/promote", AnuncioControllerTests.TEST_BEAVER_ID, AnuncioControllerTests.TEST_ANUNCIO_ID).with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/anuncios/list"));
	}

	@WithMockUser(value = "testuser")
	@Test
	public void testPromocionarAnuncioNoAutorizado() throws Exception {
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(this.beaver2);

		this.mockMvc.perform(MockMvcRequestBuilders.post("/beavers/{beaverId}/anuncios/{anuncioId}/promote", AnuncioControllerTests.TEST_BEAVER_ID, AnuncioControllerTests.TEST_ANUNCIO_ID).with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("accesoNoAutorizado"));
	}

	@WithMockUser(value = "testuser")
	@Test
	public void testListAnuncios() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/anuncios/list")).andExpect(MockMvcResultMatchers.model().attributeExists("anuncios")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("anuncios/listAnuncios"));
	}

	@WithMockUser(value = "testuser")
	@Test
	public void testMostrarAnuncio() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/anuncios/{anuncioId}", AnuncioControllerTests.TEST_BEAVER_ID, AnuncioControllerTests.TEST_ANUNCIO_ID)).andExpect(MockMvcResultMatchers.model().attributeExists("anuncio"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("anuncios/anunciosDetails"));
	}

    @WithMockUser(value = "testuser")
    @Test
    public void testMostrarAnuncioAnotherBeaver() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/anuncios/{anuncioId}", AnuncioControllerTests.TEST_BEAVER_ID2, AnuncioControllerTests.TEST_ANUNCIO_ID3)).andExpect(MockMvcResultMatchers.model().attributeExists("anuncio"))
            .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("anuncios/anunciosDetails"));
    }

    @WithMockUser(value = "testuser")
    @Test
    public void testMostrarAnuncioUserNull() throws Exception {
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(null);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/anuncios/{anuncioId}", AnuncioControllerTests.TEST_BEAVER_ID, AnuncioControllerTests.TEST_ANUNCIO_ID)).andExpect(MockMvcResultMatchers.model().attributeExists("anuncio"))
            .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("anuncios/anunciosDetails"));
    }

    @WithMockUser(value = "testuser")
    @Test
    public void testMostrarAnuncioAdmin() throws Exception {
        Beaver beaver3 = new Beaver();
        beaver3.setFirstName("Nombre");
        beaver3.setLastName("Apellidos");
        beaver3.setEmail("validx@gmail.com");
        beaver3.setId(50);
        Collection<Especialidad> espe = new HashSet<>();
        espe.add(Especialidad.ESCULTURA);
        beaver3.setEspecialidades(espe);
        beaver3.setDni("12345672Q");
        User user = new User();
        user.setUsername("Admin123");
        user.setPassword("supersecretpass");
        user.setEnabled(true);
        Authorities au = new Authorities();
        Set<Authorities> col = new HashSet<>();
        au.setAuthority("admin");
        au.setUser(user);
        col.add(au);
        user.setAuthorities(col);
        beaver3.setUser(user);
        beaver3.setEncargos(new HashSet<>());
        List<Authorities> lista = new ArrayList<Authorities>();
        lista.add(au);

        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver3);
        BDDMockito.given(this.beaverService.findUserAuthorities(user)).willReturn(lista);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/anuncios/{anuncioId}", AnuncioControllerTests.TEST_BEAVER_ID, AnuncioControllerTests.TEST_ANUNCIO_ID)).andExpect(MockMvcResultMatchers.model().attributeExists("anuncio"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("esAdmin"))
            .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("anuncios/anunciosDetails"));
    }

	@WithMockUser(value = "testuser")
	@Test
	public void testListPublicaciones() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/misPublicaciones", AnuncioControllerTests.TEST_BEAVER_ID))
            .andExpect(MockMvcResultMatchers.model().attributeExists("anuncios"))
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("encargos"))
            .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("publicaciones/misPublicaciones"));
	}

    @WithMockUser(value = "testuser")
    @Test
    public void testListPublicaciones2() throws Exception {
        Beaver beaver3 = new Beaver();
        beaver3.setFirstName("Nombre");
        beaver3.setLastName("Apellidos");
        beaver3.setEmail("validx@gmail.com");
        beaver3.setId(50);
        Collection<Especialidad> espe = new HashSet<>();
        espe.add(Especialidad.ESCULTURA);
        beaver3.setEspecialidades(espe);
        beaver3.setDni("12345672Q");
        User user = new User();
        user.setUsername("user11123");
        user.setPassword("supersecretpass");
        user.setEnabled(true);
        beaver3.setUser(user);
        beaver3.setEncargos(new HashSet<>());

        Anuncio anuncio = new Anuncio();
        anuncio.setBeaver(beaver3);
        anuncio.setDescripcion("Esto es una descripci??n");
        anuncio.setPrecio(50.0);
        anuncio.setTitulo("Esto es un t??tulo");
        anuncio.setDestacado(false);
        anuncio.setEspecialidad(Especialidad.ESCULTURA);
        anuncio.setId(20);
        Set<Anuncio> anun = new HashSet<>();
        anun.add(anuncio);
        beaver3.setAnuncios(anun);
        Encargo e = new Encargo();
        e.setId(20);
        e.setBeaver(beaver3);
        Set<Encargo> s = new HashSet<>();
        s.add(e);
        beaver3.setEncargos(s);

        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver3);
        BDDMockito.given(this.beaverService.findBeaverByIntId(50)).willReturn(beaver3);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/misPublicaciones", 50))
            .andExpect(MockMvcResultMatchers.model().attributeExists("anuncios"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("encargos"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("publicaciones/misPublicaciones"));
    }

    @WithMockUser(value = "testuser")
    @Test
    public void testListPublicaciones3() throws Exception {
        Beaver beaver3 = new Beaver();
        beaver3.setFirstName("Nombre");
        beaver3.setLastName("Apellidos");
        beaver3.setEmail("validx@gmail.com");
        beaver3.setId(50);
        Collection<Especialidad> espe = new HashSet<>();
        espe.add(Especialidad.ESCULTURA);
        beaver3.setEspecialidades(espe);
        beaver3.setDni("12345672Q");
        User user = new User();
        user.setUsername("user11123");
        user.setPassword("supersecretpass");
        user.setEnabled(true);
        beaver3.setUser(user);
        beaver3.setAnuncios(new HashSet<>());
        Encargo e = new Encargo();
        e.setId(20);
        e.setBeaver(beaver3);
        Set<Encargo> s = new HashSet<>();
        s.add(e);
        beaver3.setEncargos(s);

        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver3);
        BDDMockito.given(this.beaverService.findBeaverByIntId(50)).willReturn(beaver3);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/misPublicaciones", 50))
            .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("anuncios"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("encargos"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("publicaciones/misPublicaciones"));
    }

    @WithMockUser(value = "testuser")
    @Test
    public void testListPublicaciones4() throws Exception {
        Beaver beaver3 = new Beaver();
        beaver3.setFirstName("Nombre");
        beaver3.setLastName("Apellidos");
        beaver3.setEmail("validx@gmail.com");
        beaver3.setId(50);
        Collection<Especialidad> espe = new HashSet<>();
        espe.add(Especialidad.ESCULTURA);
        beaver3.setEspecialidades(espe);
        beaver3.setDni("12345672Q");
        User user = new User();
        user.setUsername("user11123");
        user.setPassword("supersecretpass");
        user.setEnabled(true);
        beaver3.setUser(user);
        beaver3.setAnuncios(new HashSet<>());
        beaver3.setEncargos(new HashSet<>());

        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver3);
        BDDMockito.given(this.beaverService.findBeaverByIntId(50)).willReturn(beaver3);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/misPublicaciones", 50))
            .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("anuncios"))
            .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("encargos"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("publicaciones/misPublicaciones"));
    }

    @WithMockUser(value = "testuser")
    @Test
    public void testListPublicacionesUserNull() throws Exception {
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(null);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/misPublicaciones", AnuncioControllerTests.TEST_BEAVER_ID))
            .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("accesoNoAutorizado"));
    }

	@WithMockUser(value = "testuser")
	@Test
	public void testDeleteAnuncioAdmin() throws Exception {

		Beaver beaver3 = new Beaver();
		beaver3.setFirstName("Nombre");
		beaver3.setLastName("Apellidos");
		beaver3.setEmail("validx@gmail.com");
		beaver3.setId(50);
		Collection<Especialidad> espe = new HashSet<>();
		espe.add(Especialidad.ESCULTURA);
		beaver3.setEspecialidades(espe);
		beaver3.setDni("12345672Q");
		User user = new User();
		user.setUsername("Admin123");
		user.setPassword("supersecretpass");
		user.setEnabled(true);
		Authorities au = new Authorities();
		Set<Authorities> col = new HashSet<>();
		au.setAuthority("admin");
		au.setUser(user);
		col.add(au);
		user.setAuthorities(col);
		beaver3.setUser(user);
		beaver3.setEncargos(new HashSet<>());
		List<Authorities> lista = new ArrayList<Authorities>();
		lista.add(au);

		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver3);
		BDDMockito.given(this.beaverService.findUserAuthorities(user)).willReturn(lista);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/anuncios/{anuncioId}/delete", AnuncioControllerTests.TEST_BEAVER_ID, AnuncioControllerTests.TEST_ANUNCIO_ID2).with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/anuncios/list"));
	}

    @WithMockUser(value = "testuser")
    @Test
    public void testDeleteAnuncioAdminFactura() throws Exception {

        Beaver beaver3 = new Beaver();
        beaver3.setFirstName("Nombre");
        beaver3.setLastName("Apellidos");
        beaver3.setEmail("validx@gmail.com");
        beaver3.setId(50);
        Collection<Especialidad> espe = new HashSet<>();
        espe.add(Especialidad.ESCULTURA);
        beaver3.setEspecialidades(espe);
        beaver3.setDni("12345672Q");
        User user = new User();
        user.setUsername("Admin123");
        user.setPassword("supersecretpass");
        user.setEnabled(true);
        Authorities au = new Authorities();
        Set<Authorities> col = new HashSet<>();
        au.setAuthority("admin");
        au.setUser(user);
        col.add(au);
        user.setAuthorities(col);
        beaver3.setUser(user);
        beaver3.setEncargos(new HashSet<>());
        List<Authorities> lista = new ArrayList<Authorities>();
        lista.add(au);

        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver3);
        BDDMockito.given(this.beaverService.findUserAuthorities(user)).willReturn(lista);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/{beaverId}/anuncios/{anuncioId}/delete", AnuncioControllerTests.TEST_BEAVER_ID2, AnuncioControllerTests.TEST_ANUNCIO_ID3).with(SecurityMockMvcRequestPostProcessors.csrf()))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/anuncios/list"));
    }

}
