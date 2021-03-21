package org.springframework.samples.petclinic.service;

import javax.transaction.Transactional;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Encargo;
import org.springframework.samples.petclinic.repository.EncargoRepository;
import org.springframework.stereotype.Service;

@Service
public class EncargoService {

    private EncargoRepository encargoRepository;

    public EncargoService(EncargoRepository encargoRepository){
        this.encargoRepository = encargoRepository;
    }

    @Transactional
    public void saveEncargo(Encargo encargo) throws DataAccessException{
        encargoRepository.save(encargo);
    }

    public Encargo findEncargoById(int id) throws DataAccessException{
        return encargoRepository.findById(id).orElse(null);
    }
    
}
