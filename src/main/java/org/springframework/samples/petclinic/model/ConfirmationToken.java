package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmationToken extends BaseEntity {
    
    private String confirmationToken;

    private LocalDate creationDate;

    @OneToOne(targetEntity= User.class,fetch = FetchType.EAGER)
    private User user;

    public ConfirmationToken(User user){
        this.user = user;
        this.creationDate = LocalDate.now();
        this.confirmationToken = UUID.randomUUID().toString();
    }
}
