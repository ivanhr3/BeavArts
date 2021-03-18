package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.Assert.assertTrue;


import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.User;

import org.springframework.security.crypto.password.PasswordEncoder;


import javax.validation.ConstraintViolationException;



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
        beaver.setEspecialidades("Pintura");
        beaver.setDni("12345678Q");
            User user = new User();
            user.setUsername("User");
            user.setPassword("supersecretpass");
            user.setEnabled(true);
            beaver.setUser(user);
        
            this.beaverService.saveBeaver(beaver);
            assertThat(beaver.getId().longValue()).isNotEqualTo(0);
    }

    @Test
    @Transactional
    void shouldInsertBeaverInvalidEmail(){
        Beaver beaver = new Beaver();
        beaver.setFirstName("Nombre");
        beaver.setLastName("Apellidos");
        beaver.setEmail("nonvalidemail");
        beaver.setEspecialidades("Pintura");
        beaver.setDni("12345678Q");
            User user = new User();
            user.setUsername("User2");
            user.setPassword("supersecretpass");
            user.setEnabled(true);
            beaver.setUser(user);
        
        ConstraintViolationException thrown = assertThrows(ConstraintViolationException.class,
		() -> beaverService.saveBeaver(beaver), "no es una dirección de correo bien formada");

		assertTrue(thrown.getMessage().contains("no es una dirección de correo bien formada"));
    }
    
}
