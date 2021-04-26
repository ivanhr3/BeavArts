package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Especialidad;
import org.springframework.samples.petclinic.model.User;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class UserServiceTests {
    
    @Autowired
    protected UserService userService;

    @Autowired
    protected BeaverService beaverService;

    @Test
    @Transactional
    void shouldInsertUser(){
        Beaver beaver = new Beaver();
        beaver.setFirstName("Nombre");
        beaver.setLastName("Apellidos");
        beaver.setEmail("valid@gmail.com");
        Collection<Especialidad> espe = new HashSet<>();
        espe.add(Especialidad.ESCULTURA);
        beaver.setEspecialidades(espe);
        beaver.setDni("12345678Q");

        User user = new User();
        user.setUsername("User123");
        user.setPassword("supersecretpass");
        user.setEnabled(true);
        beaver.setUser(user);

        this.userService.saveUser(user, beaver);

        assertEquals("User123", user.getUsername());
    }

    @Test
    @Transactional
    void getJson() throws JsonProcessingException{
        User user = this.userService.findUserByUsername("Cib3r");
        String json = this.userService.getUserEntitiesJson(user);
        System.out.println(json);
        assertThat(json).isNotNull();
    }
}
