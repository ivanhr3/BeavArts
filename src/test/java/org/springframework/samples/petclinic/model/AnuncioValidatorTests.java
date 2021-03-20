package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

		//ARRANGE
		Anuncio a = new Anuncio();
		a.setPrecio(50.00);
		a.setTitulo("");

		//ACT
		Validator v = createValidator();
		Set<ConstraintViolation<Anuncio>> c = v.validate(a);

		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Anuncio> violation = c.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("titulo");
		assertThat(violation.getMessage()).isEqualTo("no puede estar vacío");
	}

	@Test
	@DisplayName("No debe validarse con el atributo 'precio' con más de 6 dígitos en su parte entera")
	void shouldNotValidateWhenPRecioHasMoreThan6DigitsInHisIntegerPart(){

		//ARRANGE
		Anuncio a = new Anuncio();
		a.setPrecio(500000000.00);
		a.setTitulo("The best anuncio ever");

		//ACT
		Validator v = createValidator();
		Set<ConstraintViolation<Anuncio>> c = v.validate(a);

		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Anuncio> violation = c.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("precio");
		assertThat(violation.getMessage()).isEqualTo("valor numérico fuera de los límites (se esperaba <6 dígitos>.<2 dígitos)");
	}

	@Test
	@DisplayName("No debe validarse con el atributo 'precio' con más de 2 dígitos en su parte decimal")
	void shouldNotValidateWhenPRecioHasMoreThan6DigitsInHisFractionPart(){

		//ARRANGE
		Anuncio a = new Anuncio();
		a.setPrecio(500.455);
		a.setTitulo("The best anuncio ever");

		//ACT
		Validator v = createValidator();
		Set<ConstraintViolation<Anuncio>> c = v.validate(a);

		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Anuncio> violation = c.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("precio");
		assertThat(violation.getMessage()).isEqualTo("valor numérico fuera de los límites (se esperaba <6 dígitos>.<2 dígitos)");
	}

	@Test
	@DisplayName("No debe validarse con el atributo 'precio' menor que 0.")
	void shouldNotValidateWhenPrecioLessThan0() {
		
		//ARRANGE
		Anuncio a = new Anuncio();
		a.setPrecio(-50.00);
		a.setTitulo("The best anuncio ever");
				
				
		//ACT
		Validator v = createValidator();
		Set<ConstraintViolation<Anuncio>> c = v.validate(a);
				
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Anuncio> con = c.iterator().next();
		assertThat(con.getPropertyPath().toString()).isEqualTo("precio");
		assertThat(con.getMessage()).isEqualTo("tiene que ser mayor o igual que 0");
	}
}
