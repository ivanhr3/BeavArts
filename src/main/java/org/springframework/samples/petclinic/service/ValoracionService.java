package org.springframework.samples.petclinic.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.repository.ValoracionRepository;
import org.springframework.stereotype.Service;

@Service
public class ValoracionService {
    
    private final ValoracionRepository valoracionRepository;

    @Autowired
    private BeaverService beaverService;

    @Autowired
    public ValoracionService(final ValoracionRepository valoracionRepository){
        this.valoracionRepository = valoracionRepository;
    }

    @Transactional
    public Double calcularValoracion(Integer beaverId){
        return this.valoracionRepository.calcularPuntuacion(beaverId);   
    }

}
