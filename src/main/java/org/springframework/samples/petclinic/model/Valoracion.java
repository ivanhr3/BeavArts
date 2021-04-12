package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Valoracion extends BaseEntity{
    
    @Min(1)
    @Max(5)
    @NotNull
    private double puntuacion;

    @Size(min = 10, max = 300)
    private String comentario;

    @ManyToOne
    @JoinColumn(name="beaver_id")
    private Beaver beaver;

    @ManyToOne
    @JoinColumn(name="author_id")
    private Beaver valAuthor;
}
