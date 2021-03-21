package org.springframework.samples.petclinic.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "encargos")
public class Encargo extends BaseEntity {

    @NotBlank
    private String titulo;

    @Min(0)
    @NotNull
    @Digits(fraction = 2,integer = 6)
    private double precio;

    @NotNull
    private boolean disponibilidad;

    @NotBlank
    @Size(min = 30, max = 3000)
    private String descripcion;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "beaver", referencedColumnName = "id")
    private Beaver beaver;


    //Tipo string?? Mirar como añadir la foto
    @URL
    private String photo;

    @ManyToOne
    @JoinColumn(name = "beaver_id")
    private Beaver beaver;

}
