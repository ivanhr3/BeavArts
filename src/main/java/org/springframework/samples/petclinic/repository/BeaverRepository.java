package org.springframework.samples.petclinic.repository;

import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface BeaverRepository extends CrudRepository<Beaver, String> {
    
    @Query("SELECT b from Beaver b where b.user = :user")
    Beaver findBeaverByUsername(User user) throws DataAccessException;
}
