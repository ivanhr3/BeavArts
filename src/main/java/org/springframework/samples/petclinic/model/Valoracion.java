
package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "valoracion")
public class Valoracion extends BaseEntity {

	@Min(1)
	@Max(5)
	@NotBlank
	private double	puntuacion;

	@NotBlank
	@Size(min = 10, max = 300)
	private String	comentario;

	//faltaría relación al encargo en cuestión
}
