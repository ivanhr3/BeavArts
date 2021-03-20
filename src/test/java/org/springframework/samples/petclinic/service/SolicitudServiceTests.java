
package org.springframework.samples.petclinic.service;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Encargo;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.model.User;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

        BDDMockito.given(this.userService.findUserByUsername("testuser")).willReturn(createDummyBeaver().getUser());

		Encargo encargo1 = new Encargo();
		encargo1.setBeaver(this.userService.findUserByUsername("testuser").getBeaver());
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



		return encargo1;
	}

    /*
    @BeforeEach
    void setup(){

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
        encargo1.setBeaver(this.userService.findUserByUsername("testuser").getBeaver());
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

    }
    */
    @Test
    public void solicitudCountTest(){
	    Assert.isTrue(this.solicitudService.solicitudCount() == 2);
    }

    @Test
    public void addNewSolicitudForEncargo(){

	    Encargo encargo = createDummyEncargo();
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

}
