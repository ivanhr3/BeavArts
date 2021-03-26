package org.springframework.samples.petclinic.model;

import java.util.Collection;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table (name = "perfil")
public class Perfil extends BaseEntity{

    private String descripcion;

    @ElementCollection(targetClass=String.class)
    private Collection<String> portfolio;

    @OneToOne
    private Beaver beaver;

    /*

    @Min(0)
    @Digits(fraction=2,integer=6)
    @NotNull
    private double precio;

     */
}
