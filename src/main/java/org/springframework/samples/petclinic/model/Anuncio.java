package org.springframework.samples.petclinic.model;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@Entity
public class Anuncio extends BaseEntity {

    @NotBlank
    private String titulo;

    @Min(0)
    @Digits(fraction=2,integer=6)
    @NotNull
    private Double precio;

    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;

    @NotBlank
    private String descripcion;

    @URL
    private String photo;

    @ManyToOne
    @JoinColumn(name="beaver_id")
    private Beaver beaver;

    @OneToMany(mappedBy = "anuncio")
    private Collection<Solicitud> solicitud;

}
