package org.springframework.samples.petclinic.service;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.model.User;

@SpringBootTest
public class SolicitudServiceTests {
    @Autowired
    protected SolicitudService solicitudService;
    @Autowired
    protected BeaverService beaverService;


    @BeforeEach
    void setUp(){
        Beaver beaver = new Beaver();
        beaver.setFirstName("Nombre");
        beaver.setLastName("Apellidos");
        beaver.setEmail("valid@gmail.com");
        beaver.setEspecialidades("Pintura");
        beaver.setDni("12345678Q");
            User user = new User();
            user.setUsername("User");
            user.setPassword("supersecretpass");
            user.setEnabled(true);
            beaver.setUser(user);
        this.beaverService.saveBeaver(beaver);

    }

    @Test
    @Transactional
    void testAcceptSolicitud(){

    }
}
