package org.springframework.samples.petclinic.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.Collection;
import java.util.Set;


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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "beaver")
    private Set<Encargo> encargos;


    public void addEncargo(Encargo encargo) {
        getEncargos().add(encargo);
        encargo.setBeaver(this);
    }


}
