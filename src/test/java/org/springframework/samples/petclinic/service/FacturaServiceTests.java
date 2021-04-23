
package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Encargo;
import org.springframework.samples.petclinic.model.Especialidad;
import org.springframework.samples.petclinic.model.Estados;
import org.springframework.samples.petclinic.model.Factura;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.model.User;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class FacturaServiceTests {

	@Autowired
	private FacturaService		facturaService;
	@Autowired
	private SolicitudService	solicitudService;
	@Autowired
	private EncargoService		encargoService;
	@Autowired
	private BeaverService		beaverService;

	private Factura				factura1;
	private Factura				factura2;
	private Solicitud			solicitud1;
	private Solicitud			solicitud2;


	@BeforeEach
	public void setUp() {
		User user1 = new User();
		user1.setUsername("testuser");
		user1.setPassword("pass123");

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

		this.solicitud1 = new Solicitud();
		this.solicitud1.setEncargo(encargo1);
		this.solicitud1.setEstado(Estados.PENDIENTE);
		this.solicitud1.setPrecio(50.00);
		this.solicitud1.setDescripcion("descripcion");

		this.solicitud2 = new Solicitud();
		this.solicitud2.setEncargo(encargo1);
		this.solicitud2.setEstado(Estados.PENDIENTE);
		this.solicitud2.setPrecio(50.00);
		this.solicitud2.setDescripcion("descripcions");

		this.factura1 = new Factura();
		this.factura1.setId(1);
		this.factura1.setEmailBeaver("emailejemplo1@gmail.com");
		this.factura1.setEmailPayer("emailejemplo2@gmail.com");
		this.factura1.setSolicitud(this.solicitud1);
		this.solicitudService.saveSolicitud(this.solicitud1);
		this.facturaService.crearFactura(this.factura1);

		this.factura2 = new Factura();
		this.factura2.setId(51);
		this.factura2.setEmailBeaver("emailejemplo3@gmail.com");
		this.factura2.setEmailPayer("emailejemplo4@gmail.com");
		this.factura2.setSolicitud(this.solicitud2);
		this.solicitudService.saveSolicitud(this.solicitud2);
		this.facturaService.crearFactura(this.factura2);
	}

	@Test
	@Transactional
	void testFindFacturaById() {
		int facturaId = this.factura1.getId();
		Factura factura = this.facturaService.findFacturaById(facturaId);
		Assertions.assertEquals(facturaId, factura.getId());
	}

	@Test
	@Transactional
	void testFindAllFacturas() {
		List<Factura> facturas = (List<Factura>) this.facturaService.findAllFacturas();
		int total = facturas.size();
		Assertions.assertEquals(total, 2);
	}

	@Test
	@Transactional
	void testFindFacturaBySolicitud() {
		Factura res = this.facturaService.findFacturaBySolicitud(this.solicitud1);
		Integer id = res.getSolicitud().getId();
		Assertions.assertEquals(id, this.solicitud1.getId());
	}

}
