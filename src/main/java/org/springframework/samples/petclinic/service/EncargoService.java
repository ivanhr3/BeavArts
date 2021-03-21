package org.springframework.samples.petclinic.service;

import javax.transaction.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Encargo;
import org.springframework.samples.petclinic.repository.EncargoRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.repository.BeaverRepository;
import java.util.List;
import java.util.Optional;


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

    public Encargo findEncargoById(int id) throws DataAccessException{
        return encargoRepository.findById(id).orElse(null);
    }
    
    public Iterable<Encargo> findEncargoByBeaverId(int id){
        return this.encargoRepository.findEncargoByBeaverId(id);
    }

    @Transactional
    public Optional<Encargo> findEncargoById(int id) {
        return this.encargoRepository.findById(id);
    }

    @Transactional
    public Encargo saveEncargo(Encargo encargo) {
        return encargoRepository.save(encargo);
    }

    @Transactional
    public void deleteEncargoById(Integer id) {
        this.encargoRepository.deleteById(id);
    }
}
