package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ValoracionValidatorTests {
    
    private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

    @Test
	@DisplayName("No debe validarse con el atributo 'puntuacion' menor que 1.")
	void shouldNotValidateWhenPuntuacionLessThan1() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		//ARRANGE
		Valoracion val = new Valoracion();
		val.setComentario("Gran valoracion de mi beaver favorito.");
        val.setPuntuacion(0.01);
				
				
		//ACT
		Validator v = createValidator();
		Set<ConstraintViolation<Valoracion>> c = v.validate(val);
				
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Valoracion> con = c.iterator().next();
		assertThat(con.getPropertyPath().toString()).isEqualTo("puntuacion");
		assertThat(con.getMessage()).isEqualTo("must be greater than or equal to 1");
	}

    @Test
	@DisplayName("No debe validarse con el atributo 'puntuacion' mayor que 5.")
	void shouldNotValidateWhenPuntuacionMoreThan5() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		//ARRANGE
		Valoracion val = new Valoracion();
		val.setComentario("Gran valoracion de mi beaver favorito.");
        val.setPuntuacion(6.25);
				
				
		//ACT
		Validator v = createValidator();
		Set<ConstraintViolation<Valoracion>> c = v.validate(val);
				
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Valoracion> con = c.iterator().next();
		assertThat(con.getPropertyPath().toString()).isEqualTo("puntuacion");
		assertThat(con.getMessage()).isEqualTo("must be less than or equal to 5");
	}

    @Test
	@DisplayName("No debe validarse con el atributo 'comentario' en vacío.")
	void shouldNotValidateWhenComentarioIsBlank() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		//ARRANGE
		Valoracion val = new Valoracion();
		val.setComentario("");
        val.setPuntuacion(4.50);
				
				
		//ACT
		Validator v = createValidator();
		Set<ConstraintViolation<Valoracion>> c = v.validate(val);
				
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Valoracion> con = c.iterator().next();
		assertThat(con.getPropertyPath().toString()).isEqualTo("comentario");
		assertThat(con.getMessage()).isEqualTo("size must be between 10 and 300");
	}

	@Test
	@DisplayName("Validar las relaciones del modelo")
	void entityRelationsTests() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		//ARRANGE
		Beaver beaver = new Beaver();
        beaver.setFirstName("Nombre");
        beaver.setLastName("Apellidos");
        beaver.setEmail("valid@gmail.com");
        Collection<Especialidad> espe = new ArrayList<>();
        espe.add(Especialidad.ACRILICO);
        beaver.setEspecialidades(espe);
        beaver.setDni("12345678Q");
            User user = new User();
            user.setUsername("User");
            user.setPassword("supersecretpass");
            user.setEnabled(true);
            beaver.setUser(user);
		
		Beaver beaver2 = new Beaver();
		beaver2.setFirstName("Nombre");
		beaver2.setLastName("Apellidos");
		beaver2.setEmail("valid2@gmail.com");
		Collection<Especialidad> espe2 = new ArrayList<>();
		espe2.add(Especialidad.ACRILICO);
		beaver2.setEspecialidades(espe2);
		beaver2.setDni("12345678Q");
			User user2 = new User();
			user2.setUsername("User2");
			user2.setPassword("supersecretpass");
			user2.setEnabled(true);
			beaver2.setUser(user2);
        

		Valoracion val = new Valoracion();
		val.setComentario("Gran Escultor, Mejor persona");
        val.setPuntuacion(4.50);
		val.setBeaver(beaver);
		val.setValAuthor(beaver2);
				
				
		//ACT
		Validator v = createValidator();
		Set<ConstraintViolation<Valoracion>> c = v.validate(val);
				
		//ASSERT
		assertThat(c.size()).isEqualTo(0);
	}
}
