package org.springframework.samples.petclinic.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeaverRepository extends CrudRepository<Beaver, String> {

    @Query("select b from Beaver b where b.id = ?1")
    Beaver findBeaverById(int id);
}
