package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Solicitud extends BaseEntity{
    
    private boolean estado;

    private double precio;
    
}
