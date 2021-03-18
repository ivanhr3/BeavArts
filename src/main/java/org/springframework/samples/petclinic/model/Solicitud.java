package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Solicitud extends BaseEntity{

    private boolean estado;

    @Min(0)
    @Digits(fraction=2,integer=6)
    @NotNull
    private double precio;

    @ManyToOne
    private Encargo encargo;
}
