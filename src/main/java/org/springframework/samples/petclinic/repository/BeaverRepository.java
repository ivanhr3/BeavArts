
package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Anuncio;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface BeaverRepository extends CrudRepository<Beaver, String> {

	@Query("SELECT b from Beaver b where b.user = :user")
	Beaver findBeaverByUsername(User user) throws DataAccessException;

	@Query("select b from Beaver b where b.id = ?1")
	Beaver findBeaverById(int id);

	@Query("SELECT b from Beaver b where b.user = :user")
	Beaver findBeaverByUser(User user) throws DataAccessException;

	@Query("select b from Beaver b where b.email = ?1")
	Beaver findBeaverByEmail(String email);

	@Query("SELECT a from Authorities a where a.user = ?1")
	List<Authorities> findUserAuthorities(User user);

    @Query("SELECT b from Beaver b")
    Page<Beaver> findAllBeavers(Pageable pageable) throws DataAccessException;

}
