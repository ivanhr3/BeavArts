package org.springframework.samples.petclinic.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    @NotNull(message = "no puede estar vacío")
    @Digits(fraction = 2,integer = 6, message = "Debe contener 6 dígitos y 2 decimales")
    private Double precio;

    @NotNull
    private boolean disponibilidad;

    @Size(min = 30, max = 3000)
    private String descripcion;

    @URL
    private String photo;

    @ManyToOne
    @JoinColumn(name="beaver_id")
    private Beaver beaver;

    @OneToMany(mappedBy = "encargo")
    private Collection<Solicitud> solicitud;
    
}
