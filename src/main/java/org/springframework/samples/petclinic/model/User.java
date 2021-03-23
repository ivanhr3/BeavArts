package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User{
	@Id
	String username;

	String password;

	String nombre;

	String apellidos;

	boolean enabled;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Authorities> authorities;

	@OneToOne(cascade = CascadeType.ALL)
    private Beaver beaver;
}
