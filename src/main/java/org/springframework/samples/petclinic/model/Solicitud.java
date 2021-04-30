package org.springframework.samples.petclinic.model;

import java.util.Collection;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Solicitud extends BaseEntity{

    @NotNull
    @Enumerated(EnumType.STRING)
    private Estados estado;

    @Min(0)
    @NotNull(message = "no puede estar vacío")
    @Digits(fraction = 2,integer = 6, message = "Debe contener 6 dígitos y 2 decimales")
    private Double precio;

    @ElementCollection(targetClass = String.class)
    @CollectionTable(name = "solicitud_fotos", joinColumns = {@JoinColumn(name="solicitud_id")})
    @JoinColumn(name="solicitud_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Collection<String> fotos;

    @NotBlank
    private String descripcion;

    @ManyToOne
    @JoinColumn(name="beaver_id")
    @JsonIgnore
    private Beaver beaver;

    @ManyToOne
    @JoinColumn(name="encargo_id")
    @JsonIgnore
    private Encargo encargo;

    @ManyToOne
    @JoinColumn(name="anuncio_id")
    @JsonIgnore
    private Anuncio anuncio;

}
