package org.springframework.samples.petclinic.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends  CrudRepository<User, String>{

    @Query("select u from User u where u.username = ?1")
    User findByUsername(String username);

    @Modifying
    @Query("delete from User u where u.username =:username")
    void deleteByUsername(@Param("username") String username);

}


