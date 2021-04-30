package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Valoracion extends BaseEntity{
    
    @Min(0)
    @Max(5)
    @NotNull
    private double puntuacion;

    @Size(min = 10, max = 300)
    private String comentario;

    @ManyToOne
    @JoinColumn(name="beaver_id")
    @JsonIgnore
    private Beaver beaver;

    @ManyToOne
    @JoinColumn(name="author_id")
    @JsonIgnore
    private Beaver valAuthor;

}
