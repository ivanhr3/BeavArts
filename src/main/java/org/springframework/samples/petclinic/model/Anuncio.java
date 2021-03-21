package org.springframework.samples.petclinic.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "anuncio")
@Getter
@Setter

public class Anuncio extends BaseEntity {

    @NotBlank
    private String titulo;

    @Min(0)
    @Digits(fraction=2,integer=6)
    @NotNull
    private double precio;

    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;

    //Tipo string?? Mirar como añadir la foto
    @URL
    private String photo;
    
}
