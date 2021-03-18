package org.springframework.samples.petclinic.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "beavers")
@Getter
@Setter
public class Beaver extends Person {
    
    @Email
	String email;
    
    String especialidades;

    @Pattern(regexp="^[0-9]{8}[aA-zZ]{1}",message="introduce un DNI correcto") 
	String dni;

	//Double valoracion;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
	private User user;
    
}
