package org.springframework.samples.petclinic.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Anuncio;
import org.springframework.stereotype.Repository;

@Repository
public interface AnuncioRepository extends CrudRepository<Anuncio, Integer> {

    @Query("select a from Anuncio a where a.beaver.id = ?1")
    Iterable<Anuncio> findAnuncioByBeaverId(Integer id);

    @Query("select a from Anuncio a where a.id = ?1")
    Anuncio findAnuncioByIntId(int id);
}
