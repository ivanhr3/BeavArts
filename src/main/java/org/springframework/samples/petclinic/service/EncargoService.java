
package org.springframework.samples.petclinic.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Encargo;
import org.springframework.samples.petclinic.repository.EncargoRepository;
import org.springframework.stereotype.Service;

@Service
public class EncargoService {

    private EncargoRepository encargoRepository;

    @Autowired
    public EncargoService(EncargoRepository encargoRepository){
        this.encargoRepository = encargoRepository;
    }

    @Transactional
    public void saveEncargo(Encargo encargo) throws DataAccessException{
        encargoRepository.save(encargo);
    }
    
    public Iterable<Encargo> findEncargoByBeaverId(int id){
        return this.encargoRepository.findEncargoByBeaverId(id);
    }

    @Transactional
    public Optional<Encargo> findEncargoById(int id) {
        return this.encargoRepository.findById(id);
    }

    @Transactional
    public void deleteEncargoById(Integer id) {
        this.encargoRepository.deleteById(id);
    }
}

