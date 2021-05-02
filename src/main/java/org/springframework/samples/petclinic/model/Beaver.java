package org.springframework.samples.petclinic.model;

import java.util.Collection;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "beavers")
@Getter
@Setter
public class Beaver extends Person {

    @Email
	private String email;

    @ElementCollection(targetClass = Especialidad.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="beaver_especialidades", joinColumns = @JoinColumn(name="beaver_id"))
    @JoinColumn(name="beaver_id")
    @Column(name="especialidad")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Collection<Especialidad> especialidades;

    @NotBlank
    @Pattern(regexp="^[0-9]{8}[aA-zZ]{1}",message="Introduce un DNI correcto.")
	private String dni;

    @OneToOne
    @Cascade(CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "username", referencedColumnName = "username")
	private User user;

    private Double valoracion;

    private String urlFotoPerfil;

    @OneToMany(mappedBy = "beaver")
    @Cascade(CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Collection<Encargo> encargos;

    @OneToMany(mappedBy = "beaver")
    @Cascade(CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Collection<Solicitud> solicitud;

    @OneToOne(orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

	@OneToMany(mappedBy = "beaver")
    @Cascade(CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Collection<Anuncio> anuncios;

    @OneToMany(mappedBy = "beaver")
    @Cascade(CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Collection<Valoracion> valoraciones;

    @OneToMany(mappedBy = "valAuthor")
    @Cascade(CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Collection<Valoracion> valoracionesCreadas;


}
