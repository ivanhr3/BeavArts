package org.springframework.samples.petclinic.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Portfolio;
import org.springframework.stereotype.Repository;

@Repository
public interface PortFolioRepository extends CrudRepository<Portfolio, Integer>{

    @Query("select p from Portfolio p where p.beaver.id = ?1")
    Portfolio findPortfolioByByBeaverId(Integer id);
}

