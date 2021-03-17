package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.URL;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Anuncio extends BaseEntity {

    private String titulo;

    private Double precio;

    //private Collection<Especialidad> especialidades;

    //Tipo string?? Mirar como añadir la foto
    @URL
    private String photo;
    
}
