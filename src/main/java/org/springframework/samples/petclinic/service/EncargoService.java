package org.springframework.samples.petclinic.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Encargo;
import org.springframework.samples.petclinic.repository.EncargoRepository;
import org.springframework.stereotype.Service;

@Service
public class EncargoService {

	private final EncargoRepository encargoRepository;

	@Autowired
	private BeaverService beaverService;


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
	public Encargo saveEncargo(final Encargo encargo) {
		return this.encargoRepository.save(encargo);
	}

	@Transactional
	public void deleteEncargoById(final Integer id) {
		this.encargoRepository.deleteById(id);
	}

	@Transactional
	public Encargo findEncargoByIntId(final int id) {
		return this.encargoRepository.findEncargoByIntId(id);
	}

	@Transactional
    public int encargosCount(){
	   return (int) this.encargoRepository.count();
    }

	@Transactional
	public void CrearEncargo(Encargo encargo, Beaver beaver){
		
		this.beaverService.saveBeaver(beaver);
		encargoRepository.save(encargo);
		
	}

}
