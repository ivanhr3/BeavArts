package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Perfil;
import org.springframework.samples.petclinic.model.User;

@SpringBootTest
public class PerfilServiceTests {

    @Autowired
    protected BeaverService beaverService;

    @Autowired
    protected PerfilService perfilService;

    //aux method
    public Beaver dummyBeaver(){
        User user = new User();
        user.setUsername("User");
        user.setPassword("supersecretpass");
        user.setEnabled(true);

        Beaver beaver = new Beaver();
        beaver.setFirstName("Nombre");
        beaver.setLastName("Apellidos");
        beaver.setEmail("valid@gmail.com");
        beaver.setEspecialidades("Pintura");
        beaver.setDni("12345678Q");
        beaver.setUser(user);
        beaver.setEncargos(new HashSet<>());

        this.beaverService.saveBeaver(beaver);
        return beaver; 
    }

    //aux method
    public Perfil dummyPerfil(Beaver beaver){
        Perfil perfil = new Perfil();
        perfil.setDescripcion("This is my perfil");
        perfil.setBeaver(beaver);
        return perfil;
    }

    @Test
    @Transactional
    public void shouldInsertPerfil(){
        Beaver beaver = this.dummyBeaver();
        Perfil perfil = this.dummyPerfil(beaver);
        this.perfilService.savePerfil(perfil);
        assertThat(perfil.getId().longValue()).isNotEqualTo(0);
    }

    @Test
    @Transactional
    public void findPerfilByBeaverId(){
        Beaver beaver = this.dummyBeaver();
        Perfil perfil = this.dummyPerfil(beaver);
        this.perfilService.savePerfil(perfil);
        int beaverId = beaver.getId();
        assertTrue(this.perfilService.findPerfilByBeaverId(beaverId).equals(perfil));
    }

}
