
package org.springframework.samples.petclinic.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Encargo;
import org.springframework.samples.petclinic.repository.EncargoRepository;
import org.springframework.stereotype.Service;

@Service
public class EncargoService {

	private EncargoRepository encargoRepository;


	@Autowired
	public EncargoService(final EncargoRepository encargoRepository) {
		this.encargoRepository = encargoRepository;
	}

	@Transactional
	public void saveEncargo(final Encargo encargo) throws DataAccessException {
		this.encargoRepository.save(encargo);
	}

	public Iterable<Encargo> findEncargoByBeaverId(final int id) {
		return this.encargoRepository.findEncargoByBeaverId(id);
	}

	@Transactional
	public Encargo findEncargoById(final int id) {
		return this.encargoRepository.findByIntId(id);
	}

	@Transactional
	public void deleteEncargoById(final Integer id) {
		this.encargoRepository.deleteById(id);
	}
}
