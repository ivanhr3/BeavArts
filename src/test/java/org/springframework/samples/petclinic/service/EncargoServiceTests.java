package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Encargo;
import org.springframework.samples.petclinic.model.User;


@SpringBootTest
public class EncargoServiceTests {
    @Autowired
    protected EncargoService encargoService;
    @Autowired
    protected BeaverService beaverService;

    public Beaver createDummyBeaver(){
        Beaver beaver = new Beaver();
        beaver.setFirstName("Nombre");
        beaver.setLastName("Apellidos");
        beaver.setEmail("valid@gmail.com");
        beaver.setEspecialidades("Pintura");
        beaver.setDni("12345678Q");
            User user = new User();
            user.setUsername("User123");
            user.setPassword("supersecretpass");
            user.setEnabled(true);
            beaver.setUser(user);

        beaver.setEncargos(new HashSet<>());
            this.beaverService.saveBeaver(beaver);

        return beaver;
    }

    public Encargo createDummyEncargo(Beaver beaver){
        Encargo encargo = new Encargo();
        encargo.setTitulo("Testing save encargo");
        encargo.setPrecio(39.90);
        encargo.setDisponibilidad(true);
        encargo.setDescripcion("Testing save encargo que debe estar entre 30 y 3000");
        encargo.setPhoto("https://cnnespanol.cnn.com/wp-content/uploads/2019/12/mejores-imagenes-del-ancc83o-noticias-2019-galeria10.jpg?quality=100&strip=info&w=320&h=240&crop=1");
        encargo.setBeaver(beaver);

        this.encargoService.saveEncargo(encargo);
        return encargo;
    }

    public Encargo createDummyEncargo2(Beaver beaver){
        Encargo encargo = new Encargo();
        encargo.setTitulo("Testing save encargo 2");
        encargo.setPrecio(59.90);
        encargo.setDisponibilidad(true);
        encargo.setDescripcion("Testing save encargo (2) que debe estar entre 30 y 3000");
        encargo.setPhoto("https://cnnespanol.cnn.com/wp-content/uploads/2019/12/mejores-imagenes-del-ancc83o-noticias-2019-galeria10.jpg?quality=100&strip=info&w=320&h=240&crop=1");
        encargo.setBeaver(beaver);

        this.encargoService.saveEncargo(encargo);
        return encargo;
    }


    @Test
    @Transactional
    void testSaveEncargo(){
        Beaver beaver = this.createDummyBeaver();
        Encargo encargo = this.createDummyEncargo(beaver);

        assertThat(encargo.getId().longValue()).isNotEqualTo(0);
    }

    @Test
    @Transactional
    void testFindEncargoById(){
        Beaver beaver = this.createDummyBeaver();
        Encargo encargo = this.createDummyEncargo(beaver);
        int idEncargo = encargo.getId();

        Encargo encargo2 = this.encargoService.findEncargoById(idEncargo);

        assertEquals(encargo.getId(), encargo2.getId());
    }

    @Test
    @Transactional
    void testFindEncargoByBeaverId(){
        Beaver beaver = this.createDummyBeaver();
        Encargo encargo = this.createDummyEncargo(beaver);
        Encargo encargo2 = this.createDummyEncargo2(beaver);

        int beaverId = beaver.getId();
        List<Encargo> encargosLista = new ArrayList<Encargo>();

        Iterable<Encargo> encargosBeaver = this.encargoService.findEncargoByBeaverId(beaverId);
        for(Encargo e : encargosBeaver){
            encargosLista.add(e);
        }

        assertEquals(encargosLista.size(), 2);
        assertEquals(encargosLista.get(0).getId(), encargo.getId());
        assertEquals(encargosLista.get(1).getId(), encargo2.getId());
    }

    @Test
    @Transactional
    void testDeleteEncargoById(){
        Beaver beaver = this.createDummyBeaver();
        Encargo encargo = this.createDummyEncargo(beaver);
        Encargo encargo2 = this.createDummyEncargo2(beaver);
        Integer id = encargo.getId();

        this.encargoService.deleteEncargoById(id);


        assertEquals(this.encargoService.encargosCount(), 1);
     }
}
