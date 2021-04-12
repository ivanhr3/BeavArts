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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Especialidad;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Valoracion;

@SpringBootTest
public class ValoracionServiceTests {
    @Autowired
    protected ValoracionService valoracionService;
    @Autowired
    protected BeaverService beaverService;

    private Beaver beaver1;
    private Beaver beaver2;

    private Valoracion valoracion1;
    private Valoracion valoracion2;

    @BeforeEach
    public void setUp(){
        beaver1 = new Beaver();
        beaver1.setFirstName("Nombre");
        beaver1.setLastName("Apellidos");
        beaver1.setEmail("valid@gmail.com");
        Collection<Especialidad> espe = new HashSet<>();
        espe.add(Especialidad.ESCULTURA);
        Collection<Valoracion> valos1 = new ArrayList<>();
        beaver1.setValoraciones(valos1);
        beaver1.setEspecialidades(espe);
        beaver1.setDni("12345678Q");
            User user = new User();
            user.setUsername("User123");
            user.setPassword("supersecretpass");
            user.setEnabled(true);
            beaver1.setUser(user);
        this.beaverService.saveBeaver(beaver1);

        beaver2 = new Beaver();
        beaver2.setFirstName("Nombre");
        beaver2.setLastName("Apellidos");
        beaver2.setEmail("valid2@gmail.com");
        Collection<Especialidad> espe2 = new HashSet<>();
        espe2.add(Especialidad.ESCULTURA);
        Collection<Valoracion> valos2 = new ArrayList<>();
        beaver2.setValoraciones(valos2);
        beaver2.setEspecialidades(espe);
        beaver2.setDni("12345678F");
            User user2 = new User();
            user2.setUsername("User2");
            user2.setPassword("supersecretpass");
            user2.setEnabled(true);
            beaver2.setUser(user2);
            this.beaverService.saveBeaver(beaver2);

        valoracion1 = new Valoracion();
        valoracion1.setPuntuacion(3);
        valoracion1.setComentario("Comentario nuevo");
        valoracion1.setBeaver(beaver1);
        this.valoracionService.saveValoracion(valoracion1);

        valoracion2 = new Valoracion();
        valoracion2.setPuntuacion(3);
        valoracion2.setComentario("Comentario nuevísimo");
        valoracion2.setBeaver(beaver1);
        this.valoracionService.saveValoracion(valoracion2);    
    }
		

    @Test
    @Transactional
    void testCrearValoracion(){
        Valoracion val = new Valoracion();
        val.setComentario("Hace muy buenas esculturas, tiempo de envío rápido");
        val.setPuntuacion(4.0);
        valoracionService.crearValoracion(val, beaver1, beaver2);

        Iterable<Valoracion> valoraciones = valoracionService.findValoracionesByBeaverId(beaver1.getId());
        assertThat(valoraciones).isNotEmpty();
        assertThat(beaver1.getValoraciones()).isNotEmpty();
        assertThat(beaver2.getValoracionesCreadas()).isNotEmpty();

        assertThat(beaver1.getValoraciones().contains(val));
        assertThat(beaver2.getValoraciones().contains(val));
        
    }


    @Test
    @Transactional
    void testSaveValoracion(){
        assertThat(valoracion1.getId().longValue()).isNotEqualTo(0);
    }
    
    @Test
    @Transactional
    void testFindValoracionesByBeaverId(){

        int beaverId = beaver1.getId();
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
