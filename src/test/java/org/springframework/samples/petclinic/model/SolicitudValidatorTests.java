package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class SolicitudValidatorTests {
    
    private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

    @Test
	@DisplayName("No debe validarse con el atributo 'precio' menor que 0.")
	void shouldNotValidateWhenPrecioLessThan0() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		//ARRANGE
        Solicitud s = new Solicitud();
        s.setEstado(Estados.PENDIENTE);
		s.setPrecio(-50.00);	
        s.setDescripcion("This is a description.");			
				
		//ACT
		Validator v = createValidator();
		Set<ConstraintViolation<Solicitud>> c = v.validate(s);
				
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Solicitud> con = c.iterator().next();
		assertThat(con.getPropertyPath().toString()).isEqualTo("precio");
		assertThat(con.getMessage()).isEqualTo("must be greater than or equal to 0");
	}

    @Test
	@DisplayName("No debe validarse con el atributo 'precio' con más de 2 dígitos en su parte decimal")
	void shouldNotValidateWhenPRecioHasMoreThan2DigitsInHisFractionPart(){

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		//ARRANGE
		Solicitud s = new Solicitud();
        s.setEstado(Estados.PENDIENTE);
		s.setPrecio(50.445);	
        s.setDescripcion("This is a description.");	

		//ACT
		Validator v = createValidator();
		Set<ConstraintViolation<Solicitud>> c = v.validate(s);

		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Solicitud> violation = c.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("precio");
		assertThat(violation.getMessage()).isEqualTo("numeric value out of bounds (<6 digits>.<2 digits> expected)");
	}

    @Test
	@DisplayName("No debe validarse con el atributo 'precio' con más de 6 dígitos en su parte entera")
	void shouldNotValidateWhenPRecioHasMoreThan6DigitsInHisIntegerPart(){

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		//ARRANGE
		Solicitud s = new Solicitud();
        s.setEstado(Estados.PENDIENTE);
		s.setPrecio(5555555.40);	
        s.setDescripcion("This is a description.");	

		//ACT
		Validator v = createValidator();
		Set<ConstraintViolation<Solicitud>> c = v.validate(s);

		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Solicitud> violation = c.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("precio");
		assertThat(violation.getMessage()).isEqualTo("numeric value out of bounds (<6 digits>.<2 digits> expected)");
	}

    @Test
	@DisplayName("No debe validarse con el atributo 'estado' a null")
	void shouldNotValidateWhenEstadoIsNull(){

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		//ARRANGE
		Solicitud s = new Solicitud();
        s.setEstado(null);
		s.setPrecio(50.00);	
        s.setDescripcion("This is a description.");	

		//ACT
		Validator v = createValidator();
		Set<ConstraintViolation<Solicitud>> c = v.validate(s);

		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Solicitud> violation = c.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("estado");
		assertThat(violation.getMessage()).isEqualTo("must not be null");
	}

    @Test
	@DisplayName("No debe validarse con el atributo 'descripcion' a blank")
	void shouldNotValidateWhenDescripcionIsBlank(){

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		//ARRANGE
		Solicitud s = new Solicitud();
        s.setEstado(Estados.PENDIENTE);
		s.setPrecio(50.00);	
        s.setDescripcion("");	

		//ACT
		Validator v = createValidator();
		Set<ConstraintViolation<Solicitud>> c = v.validate(s);

		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Solicitud> violation = c.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("descripcion");
		assertThat(violation.getMessage()).isEqualTo("must not be blank");
	}

}
