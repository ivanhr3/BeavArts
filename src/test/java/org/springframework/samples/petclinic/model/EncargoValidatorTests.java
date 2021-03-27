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

public class EncargoValidatorTests {
    
    private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

    @Test
	@DisplayName("No debe validarse con el atributo 'titulo' a blank")
	void shouldNotValidateWhenTituloIsBlank(){

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		//ARRANGE
        Encargo e = new Encargo();
        e.setDescripcion("This is a description babe and we need 30 chars so this is huge and big.");
		e.setDisponibilidad(true);
        e.setPrecio(50.00);
        e.setTitulo("");	

		//ACT
		Validator v = createValidator();
		Set<ConstraintViolation<Encargo>> c = v.validate(e);

		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Encargo> violation = c.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("titulo");
		assertThat(violation.getMessage()).isEqualTo("must not be blank");
	}

    @Test
	@DisplayName("No debe validarse con el atributo 'descripcion' con menos de 30 chars")
	void shouldNotValidateWhenDescripcionHasLessThan30Chars(){

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		//ARRANGE
		Encargo e = new Encargo();
        e.setDescripcion("Short descrption");
		e.setDisponibilidad(true);
        e.setPrecio(50.00);
        e.setTitulo("titulo");		

		//ACT
		Validator v = createValidator();
		Set<ConstraintViolation<Encargo>> c = v.validate(e);

		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Encargo> violation = c.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("descripcion");
		assertThat(violation.getMessage()).isEqualTo("size must be between 30 and 3000");
	}

    @Test
	@DisplayName("No debe validarse con el atributo 'precio' con más de 6 dígitos en su parte entera")
	void shouldNotValidateWhenPRecioHasMoreThan6DigitsInHisIntegerPart(){

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		//ARRANGE
		Encargo e = new Encargo();
        e.setDescripcion("Short descrption with at least 30 chars so thats why this is huge and long.");
		e.setDisponibilidad(true);
        e.setPrecio(5555555.00);
        e.setTitulo("titulo");	

		//ACT
		Validator v = createValidator();
		Set<ConstraintViolation<Encargo>> c = v.validate(e);

		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Encargo> violation = c.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("precio");
		assertThat(violation.getMessage()).isEqualTo("numeric value out of bounds (<6 digits>.<2 digits> expected)");
	}

	@Test
	@DisplayName("No debe validarse con el atributo 'precio' con más de 2 dígitos en su parte decimal")
	void shouldNotValidateWhenPRecioHasMoreThan6DigitsInHisFractionPart(){

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		//ARRANGE
		Encargo e = new Encargo();
        e.setDescripcion("Short descrption with at least 30 chars so thats why this is huge and long.");
		e.setDisponibilidad(true);
        e.setPrecio(555.445);
        e.setTitulo("titulo");

		//ACT
		Validator v = createValidator();
		Set<ConstraintViolation<Encargo>> c = v.validate(e);

		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Encargo> violation = c.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("precio");
		assertThat(violation.getMessage()).isEqualTo("numeric value out of bounds (<6 digits>.<2 digits> expected)");
	}

	@Test
	@DisplayName("No debe validarse con el atributo 'precio' menor que 0.")
	void shouldNotValidateWhenPrecioLessThan0() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		//ARRANGE
		Encargo e = new Encargo();
        e.setDescripcion("Short descrption with at least 30 chars so thats why this is huge and long.");
		e.setDisponibilidad(true);
        e.setPrecio(-555.00);
        e.setTitulo("titulo");
				
				
		//ACT
		Validator v = createValidator();
		Set<ConstraintViolation<Encargo>> c = v.validate(e);
				
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Encargo> con = c.iterator().next();
		assertThat(con.getPropertyPath().toString()).isEqualTo("precio");
		assertThat(con.getMessage()).isEqualTo("must be greater than or equal to 0");
	}
}
