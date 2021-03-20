
package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Encargo;
import org.springframework.samples.petclinic.model.User;

@SpringBootTest
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

		Encargo encargo1 = new Encargo();
		encargo1.setBeaver(this.userService.findUserByUsername("testuser").getBeaver());
		encargo1.setDescripcion("Encargo1 correcto");
		encargo1.setTitulo("Encargo1");
		encargo1.setPrecio(50);
		encargo1.setDisponibilidad(true);

		return encargo1;
	}

}
