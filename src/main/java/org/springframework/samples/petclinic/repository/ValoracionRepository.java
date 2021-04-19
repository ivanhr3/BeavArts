package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Valoracion;
import org.springframework.stereotype.Repository;

@Repository
public interface ValoracionRepository extends CrudRepository<Valoracion, String>{
    
    @Query("select v from Valoracion v where v.beaver.id = ?1")
    Collection<Valoracion> findValoracionesByBeaverId(int id);

    @Query("select avg(v.puntuacion) from Valoracion v where v.beaver.id = ?1")
	Double calcularPuntuacion(int id);

    @Query("select count(v) from Valoracion v where v.beaver.id = ?1")
    Integer getNumValoracionesUsuario(int id);

}
