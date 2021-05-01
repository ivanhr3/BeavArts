
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Factura extends BaseEntity {

	@Email
	private String		emailPayer;

	@Email
	private String		emailBeaver;

	private LocalDate	paymentDate;

	@OneToOne
	@Cascade(CascadeType.REMOVE)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Solicitud	solicitud;

	private Double		precio;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Estados		estado;

}
