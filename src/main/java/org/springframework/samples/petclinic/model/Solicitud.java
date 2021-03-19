package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Solicitud extends BaseEntity{
    
    @Enumerated(EnumType.STRING)
    private Estado estado;

    @Min(0)
    @Digits(fraction=2,integer=6)
    @NotNull
    private double precio;
    
}

