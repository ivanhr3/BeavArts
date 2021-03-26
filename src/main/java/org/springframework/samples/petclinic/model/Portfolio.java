package org.springframework.samples.petclinic.model;

import java.util.Collection;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Portfolio extends BaseEntity{
    
    @ElementCollection(targetClass=String.class)
    private Collection<String> photos;

    private String sobreMi;

    @OneToOne(mappedBy = "portfolio")
    private Beaver beaver;
}
