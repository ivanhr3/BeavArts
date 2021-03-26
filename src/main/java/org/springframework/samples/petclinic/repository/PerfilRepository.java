package org.springframework.samples.petclinic.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Perfil;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository  extends CrudRepository<Perfil, Integer>{
    
    @Query("select p from Perfil p where p.beaver.id = ?1")
    Perfil findPerfilByBeaverId(Integer id);
}
