package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class AnuncioValidatorTests {

    private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	@DisplayName("No debe validarse con el atributo 'titulo' vacío")
	void shouldNotValidateWhenTituloIsBlank(){
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		//ARRANGE
		Anuncio a = new Anuncio();
		a.setPrecio(50.00);
		a.setTitulo("");
		a.setEspecialidad(Especialidad.ACRÍLICO);
		a.setDescripcion("Descripcion muy bonita.");

		//ACT
		Validator v = createValidator();
		Set<ConstraintViolation<Anuncio>> c = v.validate(a);

		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Anuncio> violation = c.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("titulo");
		assertThat(violation.getMessage()).isEqualTo("must not be blank");
	}

	@Test
	@DisplayName("No debe validarse con el atributo 'precio' con más de 6 dígitos en su parte entera")
	void shouldNotValidateWhenPRecioHasMoreThan6DigitsInHisIntegerPart(){

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		//ARRANGE
		Anuncio a = new Anuncio();
		a.setPrecio(500000000.00);
		a.setTitulo("The best anuncio ever");
		a.setEspecialidad(Especialidad.ACRÍLICO);
		a.setDescripcion("Descripcion muy bonita.");

		//ACT
		Validator v = createValidator();
		Set<ConstraintViolation<Anuncio>> c = v.validate(a);

		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Anuncio> violation = c.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("precio");
		assertThat(violation.getMessage()).isEqualTo("Debe contener 6 dígitos y 2 decimales");
	}

	@Test
	@DisplayName("No debe validarse con el atributo 'precio' con más de 2 dígitos en su parte decimal")
	void shouldNotValidateWhenPRecioHasMoreThan6DigitsInHisFractionPart(){

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		//ARRANGE
		Anuncio a = new Anuncio();
		a.setPrecio(500.455);
		a.setTitulo("The best anuncio ever");
		a.setEspecialidad(Especialidad.ACRÍLICO);
		a.setDescripcion("Descripcion muy bonita.");

		//ACT
		Validator v = createValidator();
		Set<ConstraintViolation<Anuncio>> c = v.validate(a);

		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Anuncio> violation = c.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("precio");
		assertThat(violation.getMessage()).isEqualTo("Debe contener 6 dígitos y 2 decimales");
	}

	@Test
	@DisplayName("No debe validarse con el atributo 'precio' menor que 0.")
	void shouldNotValidateWhenPrecioLessThan0() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		//ARRANGE
		Anuncio a = new Anuncio();
		a.setPrecio(-50.00);
		a.setTitulo("The best anuncio ever");
		a.setEspecialidad(Especialidad.ACRÍLICO);
		a.setDescripcion("Descripcion muy bonita.");
				
				
		//ACT
		Validator v = createValidator();
		Set<ConstraintViolation<Anuncio>> c = v.validate(a);
				
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Anuncio> con = c.iterator().next();
		assertThat(con.getPropertyPath().toString()).isEqualTo("precio");
		assertThat(con.getMessage()).isEqualTo("must be greater than or equal to 0");
	}

	@Test
	@DisplayName("No debe validarse con el atributo 'descripcion' vacía.")
	void shouldNotValidateWhenDescripcionIsNull() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		//ARRANGE
		Anuncio a = new Anuncio();
		a.setPrecio(50.00);
		a.setTitulo("The best anuncio ever");
		a.setEspecialidad(Especialidad.ACRÍLICO);
		a.setDescripcion(null);
				
				
		//ACT
		Validator v = createValidator();
		Set<ConstraintViolation<Anuncio>> c = v.validate(a);
				
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Anuncio> con = c.iterator().next();
		assertThat(con.getPropertyPath().toString()).isEqualTo("descripcion");
		assertThat(con.getMessage()).isEqualTo("must not be blank");
	}
}
