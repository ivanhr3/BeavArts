package org.springframework.samples.petclinic.model;

import lombok.*;

import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order extends BaseEntity {

    private double price;
    private String currency;
    private String method;
    private String intent;
    private String description;

}
