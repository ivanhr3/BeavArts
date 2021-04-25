package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;

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
public class Factura extends BaseEntity{
    
    @Email
    private String emailPayer;

    @Email
    private String emailBeaver;

    private LocalDate paymentDate;

    @OneToOne
    @JsonIgnore
    private Solicitud solicitud;

    private Double precio;
    
}
