package org.springframework.samples.petclinic.model;

import java.util.Collection;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Portfolio extends BaseEntity{
    
    @ElementCollection(targetClass=String.class)
    @CollectionTable(name = "portfolio_photos", joinColumns = {@JoinColumn(name="portfolio_id")})
    private Collection<String> photos;

    private String sobreMi;

    @OneToOne(mappedBy = "portfolio")
    private Beaver beaver;
}
