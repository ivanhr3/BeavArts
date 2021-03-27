package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Encargo;
import org.springframework.samples.petclinic.model.Especialidad;
import org.springframework.samples.petclinic.model.User;

@SpringBootTest
public class EncargoServiceTests {
    @Autowired
    protected EncargoService encargoService;
    @Autowired
    protected BeaverService beaverService;

    private Beaver beaver;
    private Encargo encargo1;
    private Encargo encargo2;

    @BeforeEach
    public void setUp(){
        beaver = new Beaver();
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

        beaver.setEncargos(new HashSet<>());
            this.beaverService.saveBeaver(beaver);

        
            encargo1 = new Encargo();
            encargo1.setTitulo("Testing save encargo");
            encargo1.setPrecio(39.90);
            encargo1.setDisponibilidad(true);
            encargo1.setDescripcion("Testing save encargo que debe estar entre 30 y 3000");
            encargo1.setPhoto("https://cnnespanol.cnn.com/wp-content/uploads/2019/12/mejores-imagenes-del-ancc83o-noticias-2019-galeria10.jpg?quality=100&strip=info&w=320&h=240&crop=1");
            encargo1.setBeaver(beaver);
    
            this.encargoService.saveEncargo(encargo1);

            encargo2 = new Encargo();
            encargo2.setTitulo("Testing save encargo 2");
            encargo2.setPrecio(59.90);
            encargo2.setDisponibilidad(true);
            encargo2.setDescripcion("Testing save encargo (2) que debe estar entre 30 y 3000");
            encargo2.setPhoto("https://cnnespanol.cnn.com/wp-content/uploads/2019/12/mejores-imagenes-del-ancc83o-noticias-2019-galeria10.jpg?quality=100&strip=info&w=320&h=240&crop=1");
            encargo2.setBeaver(beaver);
    
            this.encargoService.saveEncargo(encargo2); 
    }

    @Test
    @Transactional
    void testSaveEncargo(){
        assertThat(encargo1.getId().longValue()).isNotEqualTo(0);
    }

    @Test
    @Transactional
    void testFindEncargoById(){
        int idEncargo = encargo1.getId();

        Encargo encargo2 = this.encargoService.findEncargoById(idEncargo);

        assertEquals(encargo1.getId(), encargo2.getId());
    }

    @Test
    @Transactional
    void testFindEncargoByBeaverId(){
 
        int beaverId = beaver.getId();
        List<Encargo> encargosLista = new ArrayList<Encargo>();

        Iterable<Encargo> encargosBeaver = this.encargoService.findEncargoByBeaverId(beaverId);
        for(Encargo e : encargosBeaver){
            encargosLista.add(e);
        }

        assertEquals(encargosLista.size(), 2);
        assertEquals(encargosLista.get(0).getId(), encargo1.getId());
        assertEquals(encargosLista.get(1).getId(), encargo2.getId());
    }

    @Test
    @Transactional
    void testDeleteEncargoById(){
        Integer id = encargo1.getId();
        this.encargoService.deleteEncargoById(id);
        assertEquals(this.encargoService.encargosCount(), 1);
     }
}

