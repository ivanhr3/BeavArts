package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudRepository extends CrudRepository<Solicitud, Integer> {

	@Query("select s from Solicitud s where s.encargo.id = ?1")
	List<Solicitud> findSolicitudByEncargoId(Integer id);

	@Query("select s from Solicitud s where s.beaver.id = ?1 and s.encargo.id = ?2")
	Collection<Solicitud> findSolicitudByBeaver(Integer id, Integer endId);


}