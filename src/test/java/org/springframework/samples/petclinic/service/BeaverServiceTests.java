package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Especialidad;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Valoracion;


@SpringBootTest
public class BeaverServiceTests {
    @Autowired
    protected BeaverService beaverService;
    @Autowired
    protected ValoracionService valoracionService;
    


    @Test
    @Transactional
    void shouldInsertBeaver(){
        Beaver beaver = new Beaver();
        beaver.setFirstName("Nombre");
        beaver.setLastName("Apellidos");
        beaver.setEmail("valid@gmail.com");
        Collection<Especialidad> espe = new ArrayList<>();
        espe.add(Especialidad.ACRILICO);
        beaver.setEspecialidades(espe);
        beaver.setDni("12345678Q");
        beaver.setId(19);

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
	void testFindBeaverByIntId() {
    	Beaver beaver = new Beaver();
        beaver.setFirstName("Nombre");
        beaver.setLastName("Apellidos");
        beaver.setEmail("valid2@gmail.com");
        Collection<Especialidad> espe = new ArrayList<>();
        espe.add(Especialidad.ACRILICO);
        beaver.setEspecialidades(espe);
        beaver.setDni("12345678A");
        beaver.setId(20);

        User user = new User();
        user.setUsername("User");
        user.setPassword("supersecretpass");
        user.setEnabled(true);
        beaver.setUser(user);
    
        this.beaverService.saveBeaver(beaver);
        
		Integer beaverId = beaver.getId();
		Beaver beaver1 = this.beaverService.findBeaverByIntId(beaverId);
		Assertions.assertEquals(beaver1.getId(), beaver.getId());
	}
    
    
    @Test
	@Transactional
	void testFindBeaverByUsername() {
    	Beaver beaver = new Beaver();
        beaver.setFirstName("Nombre");
        beaver.setLastName("Apellidos");
        beaver.setEmail("valid2@gmail.com");
        Collection<Especialidad> espe = new ArrayList<>();
        espe.add(Especialidad.ACRILICO);
        beaver.setEspecialidades(espe);
        beaver.setDni("12345678A");
        beaver.setId(20);

        User user = new User();
        user.setUsername("User");
        user.setPassword("supersecretpass");
        user.setEnabled(true);
        beaver.setUser(user);
    
        this.beaverService.saveBeaver(beaver);
        
		String beaverName = beaver.getUser().getUsername();
		Beaver beaver1 = this.beaverService.findBeaverByUsername(beaverName);
		Assertions.assertEquals(beaver1.getId(), beaver.getId());
	}
    
    @Test
	@Transactional
	void testFindAllBeavers() {
    	Beaver beaver = new Beaver();
        beaver.setFirstName("Nombre");
        beaver.setLastName("Apellidos");
        beaver.setEmail("valid2@gmail.com");
        Collection<Especialidad> espe = new ArrayList<>();
        espe.add(Especialidad.ACRILICO);
        beaver.setEspecialidades(espe);
        beaver.setDni("12345678A");
        beaver.setId(20);

        User user = new User();
        user.setUsername("User");
        user.setPassword("supersecretpass");
        user.setEnabled(true);
        beaver.setUser(user);
    
        this.beaverService.saveBeaver(beaver);
        
		Iterable<Beaver> beav = this.beaverService.findAllBeavers();
		List<Beaver> list = (List<Beaver>) beav;
		Assertions.assertEquals(list.size(),45);
	}
    
    @Test
	@Transactional
	void testFindBeaverByEmail() {
    	Beaver beaver = new Beaver();
        beaver.setFirstName("Nombre");
        beaver.setLastName("Apellidos");
        beaver.setEmail("valid2@gmail.com");
        Collection<Especialidad> espe = new ArrayList<>();
        espe.add(Especialidad.ACRILICO);
        beaver.setEspecialidades(espe);
        beaver.setDni("12345678A");
        beaver.setId(20);

        User user = new User();
        user.setUsername("User");
        user.setPassword("supersecretpass");
        user.setEnabled(true);
        beaver.setUser(user);
    
        this.beaverService.saveBeaver(beaver);
        
		String beaverEmail = beaver.getEmail();
		Beaver beaver1 = this.beaverService.findBeaverByEmail(beaverEmail);
		Assertions.assertEquals(beaver1.getId(), beaver.getId());
	}
    
    @Test
	@Transactional
	void testFindUserAuthorities() {
    	Beaver beaver = new Beaver();
        beaver.setFirstName("Nombre");
        beaver.setLastName("Apellidos");
        beaver.setEmail("valid2@gmail.com");
        Collection<Especialidad> espe = new ArrayList<>();
        espe.add(Especialidad.ACRILICO);
        beaver.setEspecialidades(espe);
        beaver.setDni("12345678A");
        beaver.setId(20);

        User user = new User();
        user.setUsername("User");
        user.setPassword("supersecretpass");
        user.setEnabled(true);
        Authorities au2 = new Authorities();
		Set<Authorities> col2 = new HashSet<>();
		col2.add(au2);
		au2.setAuthority("user");
		au2.setUser(user);
		user.setAuthorities(col2);
        beaver.setUser(user);
    
        this.beaverService.saveBeaver(beaver);
        
		Set<Authorities> auth= beaver.getUser().getAuthorities();
		List<Authorities> list = this.beaverService.findUserAuthorities(beaver.getUser());
		List<Authorities> list2 = new ArrayList<Authorities>();
		for(Authorities a : auth) {
			list2.add(a);
		}
		Authorities a1 = list.get(0);
		Authorities a2 = list2.get(0);
		String res1 = a1.getAuthority();
		String res2 = a2.getAuthority();
		Assertions.assertEquals(res1, res2);
	}
    
    @Test
	@Transactional
	void testCalcularValoracion() {
    	Beaver beaver = new Beaver();
        beaver.setFirstName("Nombre");
        beaver.setLastName("Apellidos");
        beaver.setEmail("valid2@gmail.com");
        Collection<Especialidad> espe = new ArrayList<>();
        espe.add(Especialidad.ACRILICO);
        beaver.setEspecialidades(espe);
        beaver.setDni("12345678A");
        beaver.setId(20);
        
        Valoracion val = new Valoracion();
        val.setPuntuacion(3);
        val.setBeaver(beaver);
        Valoracion val2 = new Valoracion();
        val2.setPuntuacion(5);
        val2.setBeaver(beaver);
        Set<Valoracion> num = new HashSet<>();
        beaver.setValoraciones(num);

        User user = new User();
        user.setUsername("User");
        user.setPassword("supersecretpass");
        user.setEnabled(true);
        Authorities au2 = new Authorities();
		Set<Authorities> col2 = new HashSet<>();
		col2.add(au2);
		au2.setAuthority("user");
		au2.setUser(user);
		user.setAuthorities(col2);
        beaver.setUser(user);
    
        this.beaverService.saveBeaver(beaver);
        this.valoracionService.saveValoracion(val);
        this.valoracionService.saveValoracion(val2);

        Double res = this.beaverService.calculatePuntuacion(beaver);
		Assertions.assertEquals(res, 4);
	}
    
    @Test
	@Transactional
	void testNumValoraciones() {
    	Beaver beaver = new Beaver();
        beaver.setFirstName("Nombre");
        beaver.setLastName("Apellidos");
        beaver.setEmail("valid2@gmail.com");
        Collection<Especialidad> espe = new ArrayList<>();
        espe.add(Especialidad.ACRILICO);
        beaver.setEspecialidades(espe);
        beaver.setDni("12345678A");
        beaver.setId(20);
        
        Valoracion val = new Valoracion();
        val.setPuntuacion(3);
        val.setBeaver(beaver);
        Valoracion val2 = new Valoracion();
        val2.setPuntuacion(5);
        val2.setBeaver(beaver);
        Set<Valoracion> num = new HashSet<>();
        beaver.setValoraciones(num);

        User user = new User();
        user.setUsername("User");
        user.setPassword("supersecretpass");
        user.setEnabled(true);
        Authorities au2 = new Authorities();
		Set<Authorities> col2 = new HashSet<>();
		col2.add(au2);
		au2.setAuthority("user");
		au2.setUser(user);
		user.setAuthorities(col2);
        beaver.setUser(user);
    
        this.beaverService.saveBeaver(beaver);
        this.beaverService.guardarUsuario(beaver);
        this.valoracionService.saveValoracion(val);
        this.valoracionService.saveValoracion(val2);

        Integer res = this.beaverService.getNumValoraciones(beaver);
		Assertions.assertEquals(res, 2);
	}

    
}
