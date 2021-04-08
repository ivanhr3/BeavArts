package org.springframework.samples.petclinic.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Valoracion;
import org.springframework.samples.petclinic.repository.ValoracionRepository;
import org.springframework.stereotype.Service;

@Service
public class ValoracionService {

    private final ValoracionRepository valoracionRepository;

    @Autowired
    public ValoracionService(final ValoracionRepository valoracionRepository){
        this.valoracionRepository = valoracionRepository;
    }

    @Transactional
    public Iterable<Valoracion> findValoracionesByBeaverId(final int id){
        return this.valoracionRepository.findValoracionesByBeaverId(id);
    }


    
}
