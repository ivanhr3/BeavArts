
package org.springframework.samples.petclinic.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "solicitud")
@Getter
@Setter
public class Solicitud extends BaseEntity{

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @Min(0)
    @Digits(fraction=2,integer=6)
    @NotNull
    private double precio;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "encargo", referencedColumnName = "titulo")
    private Encargo encargo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "beaver", referencedColumnName = "id")
    private Beaver beaver;
    
}

