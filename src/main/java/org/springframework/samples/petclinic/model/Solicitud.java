package org.springframework.samples.petclinic.model;

import java.util.Collection;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Solicitud extends BaseEntity{
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private Estados estado;

    @Min(0)
    @Digits(fraction=2,integer=6)
    @NotNull
    private double precio;

    //Con @URL el validador de constraints comprueba que sean URLs
    @URL
    @ElementCollection(targetClass = String.class)
    @CollectionTable(name = "solicitud_fotos", joinColumns = {@JoinColumn(name="solicitud_id")})
    private Collection<String> fotos;

    @NotBlank
    private String descripcion;

    @ManyToOne
    @JoinColumn(name="beaver_id")
    private Beaver beaver;

    @ManyToOne
    @JoinColumn(name="encargo_id")
    private Encargo encargo;
    
}
