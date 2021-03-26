package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Especialidad;
import org.springframework.samples.petclinic.model.User;


@SpringBootTest
public class BeaverServiceTests {
    @Autowired
    protected BeaverService beaverService;

    @Test
    @Transactional
    void shouldInsertBeaver(){
        Beaver beaver = new Beaver();
        beaver.setFirstName("Nombre");
        beaver.setLastName("Apellidos");
        beaver.setEmail("valid@gmail.com");
        Collection<Especialidad> espe = new ArrayList<>();
        espe.add(Especialidad.ACRÍLICO);
        beaver.setEspecialidades(espe);
        beaver.setDni("12345678Q");
            User user = new User();
            user.setUsername("User");
            user.setPassword("supersecretpass");
            user.setEnabled(true);
            beaver.setUser(user);
        
            this.beaverService.saveBeaver(beaver);
            assertThat(beaver.getId().longValue()).isNotEqualTo(0);
    }

    
}
