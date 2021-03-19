package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Solicitud;

public interface SolicitudRepository extends CrudRepository<Solicitud, String> {
    
}
