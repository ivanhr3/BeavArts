package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Especialidad;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Valoracion;
import org.springframework.samples.petclinic.repository.BeaverRepository;
import org.springframework.samples.petclinic.repository.ValoracionRepository;

@SpringBootTest
public class ValoracionServiceTests {

    @Autowired
    protected ValoracionService valoracionService;

    @Autowired
    protected BeaverService beaverService;

    private Beaver beaver;
    private Valoracion valoracion1;
    private Valoracion valoracion2;

    @BeforeEach
    public void setUp(){
        User user = new User();
        user.setUsername("User123");
        user.setPassword("supersecretpass");
        user.setEnabled(true);

        beaver = new Beaver();
        beaver.setFirstName("Nombre");
        beaver.setLastName("Apellidos");
        beaver.setEmail("valid@gmail.com");
        Collection<Especialidad> espe = new HashSet<>();
        espe.add(Especialidad.ESCULTURA);
        beaver.setEspecialidades(espe);
        beaver.setDni("12345678Q");
        beaver.setUser(user);
        
        beaver.setValoraciones(new HashSet<>());
        this.beaverService.saveBeaver(beaver);

        valoracion1 = new Valoracion();
        valoracion1.setPuntuacion(3);
        valoracion1.setComentario("Comentario nuevo");
        valoracion1.setBeaver(beaver);
        this.valoracionService.saveValoracion(valoracion1);

        valoracion2 = new Valoracion();
        valoracion2.setPuntuacion(3);
        valoracion2.setComentario("Comentario nuevísimo");
        valoracion2.setBeaver(beaver);
        this.valoracionService.saveValoracion(valoracion2);
            
    }

    @Test
    @Transactional
    void testSaveValoracion(){
        assertThat(valoracion1.getId().longValue()).isNotEqualTo(0);
    }

    @Test
    @Transactional
    void testFindValoracionesByBeaverId(){

        int beaverId = beaver.getId();
        List<Valoracion> valoraciones = new ArrayList<Valoracion>();

        Iterable<Valoracion> valoracionesBeaver = this.valoracionService.findValoracionesByBeaverId(beaverId);
        for(Valoracion v : valoracionesBeaver){
            valoraciones.add(v);
        }

        assertEquals(valoraciones.size(), 2);
        assertEquals(valoraciones.get(0).getId(), valoracion1.getId());
        assertEquals(valoraciones.get(1).getId(), valoracion2.getId());
    }



}
