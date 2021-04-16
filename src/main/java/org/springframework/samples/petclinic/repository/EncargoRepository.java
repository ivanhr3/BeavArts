package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Encargo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EncargoRepository extends CrudRepository<Encargo, Integer> {

    @Query("select e from Encargo e where e.beaver.id = ?1")
    Iterable<Encargo> findEncargoByBeaverId(Integer id);

    @Query("select e from Encargo e where e.id = ?1")
    Encargo findEncargoByIntId(int id);

    @Query("select e from Encargo e where e.beaver.id = ?1 AND e.disponibilidad = true")
    Iterable<Encargo> findEncargoByAnotherBeaverId(Integer id);

}
