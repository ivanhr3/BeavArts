package org.springframework.samples.petclinic.repository;

import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.data.repository.CrudRepository;

public interface BeaverRepository extends CrudRepository<Beaver, String> {
    
}
