
package org.springframework.samples.petclinic.model;


import java.util.Collection;
import javax.persistence.OneToMany;
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

import org.springframework.samples.petclinic.model.Enum.EncargoStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "encargos")
public class Encargo extends BaseEntity {

	@NotBlank
	private String			titulo;

	@Min(0)
	@NotNull
	@Digits(fraction = 2, integer = 6)
	private double			precio;

	@NotNull
	private EncargoStatus	disponibilidad;

	@NotBlank
	@Size(min = 30, max = 3000)
	private String			descripcion;

	private String			photo;

	@OneToMany
	private Collection<Solicitud>	solicitudes;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "beaver", referencedColumnName = "id")
    private Beaver beaver;
