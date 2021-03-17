package org.springframework.samples.petclinic.model;

import java.util.Collection;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Portfolio extends BaseEntity{
    
    @ElementCollection(targetClass=String.class)
    private Collection<String> photos;

    private double precio;
}
