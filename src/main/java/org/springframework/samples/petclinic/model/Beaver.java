package org.springframework.samples.petclinic.model;

import java.util.Collection;

import javax.persistence.CascadeType;
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
    @Column(name="especialidad")
    private Collection<Especialidad> especialidades;

    @NotBlank
    @Pattern(regexp="^[0-9]{8}[aA-zZ]{1}",message="introduce un DNI correcto")
	private String dni;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
	private User user;

    private Double valoracion;

    private String urlFotoPerfil;

    @OneToMany(mappedBy = "beaver")
    private Collection<Encargo> encargos;

    @OneToMany(mappedBy = "beaver")
    private Collection<Solicitud> solicitud;

    @OneToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @OneToMany(mappedBy = "beaver")
    private Collection<Anuncio> anuncios;

    @OneToMany(mappedBy = "beaver")
    private Collection<Valoracion> valoraciones;

    @OneToMany(mappedBy = "valAuthor")
    private Collection<Valoracion> valoracionesCreadas;
}
