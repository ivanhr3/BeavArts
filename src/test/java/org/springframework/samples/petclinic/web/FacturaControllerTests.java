
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
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
import org.springframework.samples.petclinic.model.Factura;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.BeaverService;
import org.springframework.samples.petclinic.service.EncargoService;
import org.springframework.samples.petclinic.service.FacturaService;
import org.springframework.samples.petclinic.service.SolicitudService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(value = FacturaController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
@AutoConfigureMockMvc
public class FacturaControllerTests {

	@Autowired
	private MockMvc				mockMvc;

	@MockBean
	private BeaverService		beaverService;

	@MockBean
	private FacturaService		facturaService;

	@MockBean
	private EncargoService		encargoService;

	@MockBean
	private SolicitudService	solicitudService;

	private static final int	TEST_FACTURA_ID	= 1;

	private Factura				factura1;
	private Factura				factura2;


	@BeforeEach
	public void setUp() {
		User user1 = new User();
		user1.setUsername("testuser");
		user1.setPassword("pass123");
		Authorities au = new Authorities();
		Set<Authorities> col = new HashSet<>();
		col.add(au);
		au.setAuthority("admin");
		au.setUser(user1);
		user1.setAuthorities(col);
		List<Authorities> lista = new ArrayList<Authorities>();
		lista.add(au);

		Beaver beaver1 = new Beaver();
		beaver1.setDni("29519811N");
		beaver1.setEmail("testemail@hotmail.com");
		Collection<Especialidad> especialidad = new HashSet<Especialidad>();
		especialidad.add(Especialidad.FOTOGRAFIA);
		beaver1.setEspecialidades(especialidad);
		beaver1.setFirstName("testbeaver");
		beaver1.setLastName("Perez");
		beaver1.setUser(user1);
		this.beaverService.saveBeaver(beaver1);

		Encargo encargo1 = new Encargo();
		encargo1.setBeaver(beaver1);
		encargo1.setDescripcion("Encargo1 correcto mira que largo tiene que ser quien ha sido el que puso");
		encargo1.setTitulo("Encargo1");
		encargo1.setPrecio(50.00);
		encargo1.setDisponibilidad(true);
		this.encargoService.saveEncargo(encargo1);

		Solicitud solicitud1 = new Solicitud();
		solicitud1.setEncargo(encargo1);
		solicitud1.setEstado(Estados.PENDIENTE);
		solicitud1.setPrecio(50.00);
		solicitud1.setDescripcion("descripcion");

		this.factura1 = new Factura();
		this.factura1.setId(1);
		this.factura1.setEmailBeaver("emailejemplo1@gmail.com");
		this.factura1.setEmailPayer("emailejemplo2@gmail.com");
		this.factura1.setSolicitud(solicitud1);
		this.solicitudService.saveSolicitud(solicitud1);
		this.facturaService.crearFactura(this.factura1);

		this.factura2 = new Factura();
		this.factura2.setId(2);
		this.factura2.setEmailBeaver("emailejemplo3@gmail.com");
		this.factura2.setEmailPayer("emailejemplo4@gmail.com");
		this.factura2.setSolicitud(solicitud1);
		this.solicitudService.saveSolicitud(solicitud1);
		this.facturaService.crearFactura(this.factura2);

		BDDMockito.given(this.beaverService.findUserAuthorities(user1)).willReturn(lista);
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver1);
		BDDMockito.given(this.facturaService.findFacturaById(FacturaControllerTests.TEST_FACTURA_ID)).willReturn(this.factura1);
	}

	@WithMockUser(value = "testuser")
	@Test
	public void testListFacturas() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/facturas/list")).andExpect(MockMvcResultMatchers.model().attributeExists("facturas")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("facturas/listFacturas"));
	}

	@WithMockUser(value = "testuser")
	@Test
	public void testListFacturasNoAutorizado() throws Exception {
		User user2 = new User();
		user2.setUsername("testuser");
		user2.setPassword("pass123");
		Authorities au2 = new Authorities();
		Set<Authorities> col2 = new HashSet<>();
		col2.add(au2);
		au2.setAuthority("user");
		au2.setUser(user2);
		user2.setAuthorities(col2);
		List<Authorities> lista2 = new ArrayList<Authorities>();
		lista2.add(au2);

		Beaver beaver2 = new Beaver();
		beaver2.setDni("29519811N");
		beaver2.setEmail("testemail@hotmail.com");
		Collection<Especialidad> especialidad2 = new HashSet<Especialidad>();
		especialidad2.add(Especialidad.FOTOGRAFIA);
		beaver2.setEspecialidades(especialidad2);
		beaver2.setFirstName("testbeaver");
		beaver2.setLastName("Perez");
		beaver2.setUser(user2);
		this.beaverService.saveBeaver(beaver2);

		BDDMockito.given(this.beaverService.findUserAuthorities(user2)).willReturn(lista2);
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver2);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/facturas/list")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("accesoNoAutorizado"));
	}

	@WithMockUser(value = "testuser")
	@Test
	public void testMostrarFactura() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/facturas/{facturaId}", FacturaControllerTests.TEST_FACTURA_ID)).andExpect(MockMvcResultMatchers.model().attributeExists("factura")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("facturas/facturasDetails"));
	}

	@WithMockUser(value = "testuser")
	@Test
	public void testMostrarFacturaNoAutorizado() throws Exception {
		User user2 = new User();
		user2.setUsername("testuser");
		user2.setPassword("pass123");
		Authorities au2 = new Authorities();
		Set<Authorities> col2 = new HashSet<>();
		col2.add(au2);
		au2.setAuthority("user");
		au2.setUser(user2);
		user2.setAuthorities(col2);
		List<Authorities> lista2 = new ArrayList<Authorities>();
		lista2.add(au2);

		Beaver beaver2 = new Beaver();
		beaver2.setDni("29519811N");
		beaver2.setEmail("testemail@hotmail.com");
		Collection<Especialidad> especialidad2 = new HashSet<Especialidad>();
		especialidad2.add(Especialidad.FOTOGRAFIA);
		beaver2.setEspecialidades(especialidad2);
		beaver2.setFirstName("testbeaver");
		beaver2.setLastName("Perez");
		beaver2.setUser(user2);
		this.beaverService.saveBeaver(beaver2);

		BDDMockito.given(this.beaverService.findUserAuthorities(user2)).willReturn(lista2);
		BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver2);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/facturas/{facturaId}", FacturaControllerTests.TEST_FACTURA_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("accesoNoAutorizado"));
	}

}
