package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Valoracion extends BaseEntity{
    
    @Min(1)
    @Max(5)
    private double puntuacion;

    private String comentario;
}
