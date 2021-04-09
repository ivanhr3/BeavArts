package org.springframework.samples.petclinic.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.samples.petclinic.model.Anuncio;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Especialidad;
import org.springframework.samples.petclinic.model.User;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@SpringBootTest
public class AnuncioServiceTests {

    @Autowired
    private AnuncioService anuncioService;

    @Autowired
    private BeaverService beaverService;

    private Beaver beaver;
    private Anuncio anuncio;
    private Anuncio anuncio3;

    @BeforeEach
    public void setUp() {
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

        anuncio = new Anuncio();
        anuncio.setBeaver(beaver);
        anuncio.setDescripcion("Esto es una descripción");
        anuncio.setPrecio(50.0);
        anuncio.setDestacado(false);
        anuncio.setTitulo("Esto es un título");
        anuncio.setEspecialidad(Especialidad.ESCULTURA);

        anuncio3 = new Anuncio();
        anuncio3.setBeaver(beaver);
        anuncio3.setDescripcion("Esto es una descripción 2");
        anuncio3.setDestacado(false);
        anuncio3.setPrecio(30.0);
        anuncio3.setTitulo("Esto es un título 2");
        anuncio3.setEspecialidad(Especialidad.ACRILICO);

        this.beaverService.saveBeaver(beaver);
        this.anuncioService.saveAnuncio(anuncio);
        this.anuncioService.saveAnuncio(anuncio3);
    }

    @Test
    @Transactional
    void testFindAnuncioByBeaverId() {
        int beaverId = anuncio.getBeaver().getId();
        List<Anuncio> listaAnuncios = new ArrayList<>();
        Iterable<Anuncio> anuncios = this.anuncioService.findAnuncioByBeaverId(beaverId);
        for(Anuncio a: anuncios){
            listaAnuncios.add(a);
        }
        Assertions.assertEquals(listaAnuncios.size(), 2);
        Assertions.assertEquals(listaAnuncios.get(0).getBeaver().getId(), beaverId);
    }

    @Test
    @Transactional
    void testFindAnuncioByBeaverIdHasErrors() {
        int beaverId = anuncio.getBeaver().getId();
        List<Anuncio> listaAnuncios = new ArrayList<>();
        Iterable<Anuncio> anuncios = this.anuncioService.findAnuncioByBeaverId(beaverId);
        for(Anuncio a: anuncios){
            listaAnuncios.add(a);
        }

        Assertions.assertNotEquals(listaAnuncios.size(), 1788464);
        Assertions.assertNotEquals(listaAnuncios.get(0).getBeaver().getId(), 1788464);
    }

    @Test
    @Transactional
    void testFindAnuncioById() {
        int anuncioId = anuncio.getId();
        Anuncio anuncio2 = this.anuncioService.findAnuncioById(anuncioId);
        Assertions.assertEquals(anuncioId, anuncio2.getId());
    }

    @Test
    @Transactional
    void testFindAnuncioByIdHasErrors() {
        int anuncioId = anuncio.getId();
        Anuncio anuncio2 = this.anuncioService.findAnuncioById(anuncio3.getId());
        Assertions.assertNotEquals(anuncioId, anuncio2.getId());
    }

    @Test
    @Transactional
    void testFindAnuncioByEspecialidad() {
        Especialidad especialidad = anuncio.getEspecialidad();
        List<Anuncio> anuncios = this.anuncioService.findAnunciosByEspecialidad(especialidad);
        Assertions.assertEquals(especialidad, anuncios.get(0).getEspecialidad());
    }

    @Test
    @Transactional
    void testFindAnuncioByEspecialidadHasErrors() {
        Especialidad especialidad = anuncio.getEspecialidad();
        List<Anuncio> anuncios = this.anuncioService.findAnunciosByEspecialidad(anuncio3.getEspecialidad());
        Assertions.assertNotEquals(especialidad, anuncios.get(0).getEspecialidad());
    }

    @Test
    @Transactional
    void testCrearAnuncio() {
        int numAnuncios = this.anuncioService.anunciosCount();

        Anuncio anuncio2 = new Anuncio();
        anuncio2.setBeaver(beaver);
        anuncio2.setDescripcion("Esto es una descripción 2");
        anuncio2.setPrecio(30.0);
        anuncio2.setTitulo("Esto es un título 2");
        anuncio2.setEspecialidad(Especialidad.ACRILICO);
        this.anuncioService.saveAnuncio(anuncio2);
        int numAnuncios2 = this.anuncioService.anunciosCount();

        Assertions.assertEquals(numAnuncios2, numAnuncios + 1);
    }

    @Test
    @Transactional
    void testCrearAnuncioHasErrors() {

        Assertions.assertThrows(ConstraintViolationException.class, () -> {

            Anuncio anuncio2 = new Anuncio();
            anuncio2.setBeaver(beaver);
            anuncio2.setDescripcion("");
            anuncio2.setTitulo("");
            anuncio2.setPrecio(30.0);
            anuncio2.setEspecialidad(Especialidad.ACRILICO);
            this.anuncioService.saveAnuncio(anuncio2);

        });
    }

    @Test
    @Transactional
    void testDeleteAnuncio() {
        int numAnuncios = this.anuncioService.anunciosCount();

        this.anuncioService.deleteAnuncio(anuncio.getId());
        int numAnuncios2 = this.anuncioService.anunciosCount();

        Assertions.assertEquals(numAnuncios2, numAnuncios - 1);
    }

    @Test
    @Transactional
    void testDeleteAnuncioHasErrors() {

        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {

            this.anuncioService.deleteAnuncio(123456789);

        });
    }
}
