
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
	public EncargoService(final EncargoRepository encargoRepository) {
		this.encargoRepository = encargoRepository;
	}

	@Transactional
	public Iterable<Encargo> findEncargoByBeaverId(final int id) {
		return this.encargoRepository.findEncargoByBeaverId(id);
	}

	@Transactional
	public Encargo findEncargoById(final int id) {
		return this.encargoRepository.findEncargoByIntId(id);
	}

    @Transactional
    public void saveEncargo(Encargo encargo) throws DataAccessException{
        encargoRepository.save(encargo);
    }
    
    public Iterable<Encargo> findEncargoByBeaverId(int id){
        return this.encargoRepository.findEncargoByBeaverId(id);
    }


	@Transactional
	public Encargo saveEncargo(final Encargo encargo) {
		return this.encargoRepository.save(encargo);
	}

	@Transactional
	public void deleteEncargoById(final Integer id) {
		this.encargoRepository.deleteById(id);
	}
  
}
