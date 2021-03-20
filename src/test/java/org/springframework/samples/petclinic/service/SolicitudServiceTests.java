
package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Encargo;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.model.User;
import org.springframework.util.Assert;

@SpringBootTest
@Transactional
@SuppressWarnings("deprecation")
public class SolicitudServiceTests {

	@Autowired
	private SolicitudService	solicitudService;

	@Autowired
	private UserService			userService;


	public Beaver createDummyBeaver() {

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

		return beaver1;
	}

	public Encargo createDummyEncargo() {

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

		this.solicitudService.saveSolicitud(solicitud1);
		this.solicitudService.saveSolicitud(solicitud2);

		return encargo1;
	}

	/*
	 * @BeforeEach
	 * void setup(){
	 *
	 * User user1 = new User();
	 * user1.setUsername("testuser");
	 * user1.setApellidos("Perez");
	 * user1.setNombre("testuser");
	 * user1.setPassword("pass123");
	 *
	 * Beaver beaver1 = new Beaver();
	 * beaver1.setDni("29519811N");
	 * beaver1.setEmail("testemail@hotmail.com");
	 * beaver1.setEspecialidades("Pintura");
	 * beaver1.setFirstName("testbeaver");
	 * beaver1.setLastName("Perez");
	 * beaver1.setUser(user1);
	 *
	 * user1.setBeaver(beaver1);
	 *
	 * Encargo encargo1 = new Encargo();
	 * encargo1.setBeaver(this.userService.findUserByUsername("testuser").getBeaver());
	 * encargo1.setDescripcion("Encargo1 correcto");
	 * encargo1.setTitulo("Encargo1");
	 * encargo1.setPrecio(50);
	 * encargo1.setDisponibilidad(true);
	 *
	 * Solicitud solicitud1 = new Solicitud();
	 * solicitud1.setEncargo(encargo1);
	 * solicitud1.setEstado(true);
	 * solicitud1.setPrecio(50);
	 * solicitud1.setId(1);
	 *
	 * Solicitud solicitud2 = new Solicitud();
	 * solicitud2.setEncargo(encargo1);
	 * solicitud2.setEstado(true);
	 * solicitud2.setPrecio(50);
	 * solicitud2.setId(2);
	 *
	 * List<Solicitud> list = new ArrayList<Solicitud>();
	 * list.add(solicitud1);
	 * list.add(solicitud2);
	 * encargo1.setSolicitudes(list);
	 *
	 * }
	 */
	@Test
	@Transactional
	public void solicitudCountTest() {

		int res = this.solicitudService.solicitudCount();
		Solicitud solicitud = new Solicitud();
		solicitud.setEstado(true);
		solicitud.setPrecio(25.00);
		solicitud.setId(5);

		Solicitud solicitud2 = new Solicitud();
		solicitud2.setEstado(true);
		solicitud2.setPrecio(25.00);
		solicitud2.setId(6);

		this.solicitudService.saveSolicitud(solicitud);
		this.solicitudService.saveSolicitud(solicitud2);
		Assert.isTrue(this.solicitudService.solicitudCount() == res + 2);
	}

	@Test
	@Transactional
	public void addNewSolicitudForEncargo() {
		Encargo encargo = this.createDummyEncargo();
		Collection<Solicitud> listaSolicitudes = encargo.getSolicitudes();
		int numSolicitudes = listaSolicitudes.size();

		Solicitud solicitud = new Solicitud();
		solicitud.setEncargo(encargo);
		solicitud.setEstado(true);
		solicitud.setPrecio(25.00);
		solicitud.setId(3);
		this.solicitudService.saveSolicitud(solicitud);

		listaSolicitudes.add(solicitud);

		Assertions.assertEquals(numSolicitudes + 1, encargo.getSolicitudes().size());

	}

	//no funciona
	@Test
	@Transactional
	public void findSolicitudByIdTest() {
		Solicitud solicitud = new Solicitud();
		solicitud.setEstado(true);
		solicitud.setPrecio(25.00);
		solicitud.setId(7);
		this.solicitudService.saveSolicitud(solicitud);

		Optional<Solicitud> res = this.solicitudService.findSolicitudById(7);
		Solicitud sol = res.get();
		System.out.println("-----------------------------------------------" + solicitud.getId());

		System.out.println("*****************************************");

		Assertions.assertEquals(sol, solicitud);
	}

	//no funciona, quizas tenga que estar el EncargoService para que se mantengan en la base de datos (si no se hace save encargo, no estan en el repository)
	@Test
	@Transactional
	public void findSolicitudByEncargoIdTest() {
		Encargo encargo = this.createDummyEncargo();
		System.out.println("*******************************" + encargo.getSolicitudes());
		Collection<Solicitud> sol = encargo.getSolicitudes();
		List<Solicitud> res = this.solicitudService.findSolicitudByEncargoId(encargo.getId());
		System.out.println("-----------------------------" + res);
		Assertions.assertEquals(sol, res);
	}

	@Test
	@Transactional
	public void deleteSolicitudTest() {

		Solicitud solicitud = new Solicitud();
		solicitud.setEstado(true);
		solicitud.setPrecio(25.00);
		solicitud.setId(5);

		Solicitud solicitud2 = new Solicitud();
		solicitud2.setEstado(true);
		solicitud2.setPrecio(25.00);
		solicitud2.setId(6);

		this.solicitudService.saveSolicitud(solicitud);
		this.solicitudService.saveSolicitud(solicitud2);
		int res = this.solicitudService.solicitudCount();
		this.solicitudService.deleteSolicitud(solicitud2);

		Assertions.assertEquals(this.solicitudService.solicitudCount(), res - 1);
	}

	@Test
	@Transactional
	public void deleteSolicitudByIdTest() {

		Solicitud solicitud = new Solicitud();
		solicitud.setEstado(true);
		solicitud.setPrecio(25.00);
		solicitud.setId(5);

		Solicitud solicitud2 = new Solicitud();
		solicitud2.setEstado(true);
		solicitud2.setPrecio(25.00);
		solicitud2.setId(6);

		this.solicitudService.saveSolicitud(solicitud);
		this.solicitudService.saveSolicitud(solicitud2);
		int res = this.solicitudService.solicitudCount();
		System.out.println("**************************" + solicitud2.getId());
		this.solicitudService.deleteSolicitudById(6);

		Assertions.assertEquals(this.solicitudService.solicitudCount(), res - 1);
	}

}
