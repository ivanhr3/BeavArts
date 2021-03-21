package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import javax.mail.MessagingException;
import javax.transaction.Transactional;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Encargo;
import org.springframework.samples.petclinic.model.Estado;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.model.User;



@SpringBootTest
public class SolicitudServiceTests {
    @Autowired
    protected SolicitudService solicitudService;
    @Autowired
    protected BeaverService beaverService;
    @Autowired
    protected EncargoService encargoService;


    Beaver beaver;
    Encargo encargo;
    User user;

    @BeforeEach
    void setDummyData(){
        beaver = new Beaver();
        beaver.setFirstName("Nombre");
        beaver.setLastName("Apellidos");
        beaver.setEmail("valid@gmail.com");
        beaver.setEspecialidades("Pintura");
        beaver.setDni("12345678Q");
            user = new User();
            user.setUsername("User");
            user.setPassword("supersecretpass");
            user.setEnabled(true);
            beaver.setUser(user);
        this.beaverService.saveBeaver(beaver);

        encargo = new Encargo();
        encargo.setTitulo("Encargo chulisimo");
        encargo.setDescripcion("mira que wapo mi encargo reshulon porque tienen que ser tantos caracteres");
        encargo.setDisponibilidad(true);
        encargo.setPrecio(199);
        this.encargoService.saveEncargo(encargo);
    }

    @Test
    @Transactional
    void testSaveSolicitud(){
        Solicitud solicitud = new Solicitud();
        solicitud.setEstado(Estado.PENDIENTE);
        solicitud.setEncargo(encargo);
        solicitud.setBeaver(beaver);
        solicitudService.saveSolicitud(solicitud);

        assertThat(solicitud.getId().longValue()).isNotEqualTo(0);
        assertThat(solicitudService.existsSol(solicitud.getId())).isTrue();
    }

    @Test
    @Transactional
    void testAcceptSolicitud() throws MessagingException{
        Solicitud solicitud = new Solicitud();
        solicitud.setEstado(Estado.PENDIENTE);
        solicitud.setEncargo(encargo);
        solicitud.setBeaver(beaver);
        solicitudService.saveSolicitud(solicitud);
        
        solicitudService.aceptarSolicitud(solicitud, beaver);

        assertThat(solicitud.getEstado()).isEqualTo(Estado.ACEPTADA);
    }

    @Test
    @Transactional
    void testRejectSolicitud() throws MessagingException{
        Solicitud solicitud = new Solicitud();
        solicitud.setEstado(Estado.PENDIENTE);
        solicitud.setEncargo(encargo);
        solicitud.setBeaver(beaver);
        solicitudService.saveSolicitud(solicitud);

        solicitudService.rechazarSolicitud(solicitud, beaver);

        assertThat(solicitud.getEstado()).isEqualTo(Estado.RECHAZADA);
    }

    @Test
    @Transactional
    void shouldFindById(){
        Solicitud solicitud = new Solicitud();
        solicitud.setEstado(Estado.PENDIENTE);
        solicitud.setEncargo(encargo);
        solicitud.setBeaver(beaver);
        solicitud.setPrecio(199);
        solicitudService.saveSolicitud(solicitud);
      
        
        int id = solicitud.getId();
        assertThat(solicitudService.existsSol(id)).isTrue();
        Solicitud test = solicitudService.findById(id);
        assertThat(solicitud).isEqualTo(test);
    }
}
