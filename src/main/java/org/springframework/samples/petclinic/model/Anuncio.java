package org.springframework.samples.petclinic.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.URL;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Anuncio extends BaseEntity {

    @NotBlank
    private String titulo;

    @Min(0)
    @Digits(fraction=2,integer=6,message="Debe contener 6 dígitos y 2 decimales")
    @NotNull(message="no puede estar vacío")
    private Double precio;

    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;

    @NotBlank
    private String descripcion;

    @URL
    private String photo;

    @ManyToOne
    @JoinColumn(name="beaver_id")
    @JsonIgnore
    private Beaver beaver;

    @OneToMany(mappedBy = "anuncio")
    @Cascade(CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Collection<Solicitud> solicitud;

    private Boolean destacado;

}
