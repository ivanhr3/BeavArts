package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.samples.petclinic.model.Anuncio;
import org.springframework.samples.petclinic.model.Especialidad;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnuncioRepository extends CrudRepository<Anuncio, Integer> {

    @Query("select a from Anuncio a where a.beaver.id = ?1")
    Iterable<Anuncio> findAnuncioByBeaverId(Integer id);

    @Query("select a from Anuncio a where a.id = ?1")
    Anuncio findAnuncioByIntId(int id);

    @Query("select a from Anuncio a where a.especialidad = ?1")
    List<Anuncio> findAnunciosByEspecialidad(Especialidad especialidad);

    @Query("select a from Anuncio a where a.destacado = true")
    List<Anuncio> findAnunciosDestacados();

    @Query("select a from Anuncio a where a.destacado = false")
    List<Anuncio> findAnunciosNoDestacados();

    @Query("SELECT a from Anuncio a")
    Page<Anuncio> findAllAnuncios(Pageable pageable) throws DataAccessException;
}
