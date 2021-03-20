
package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.repository.SolicitudRepository;
import org.springframework.stereotype.Service;

@Service
public class SolicitudService {

	@Autowired
	private SolicitudRepository solicitudRepository;


	@Transactional
	public int solicitudCount() {
		return (int) this.solicitudRepository.count();
	}
	@Transactional
	public Solicitud saveSolicitud(final Solicitud s) {
		return this.solicitudRepository.save(s);
	}

	@Transactional
	public Iterable<Solicitud> findAll() {
		return this.solicitudRepository.findAll();
	}

	@Transactional
	public Optional<Solicitud> findSolicitudById(final int id) {
		return this.solicitudRepository.findById(id);
	}

	public List<Solicitud> findSolicitudByEncargoId(final int id) {
		return this.solicitudRepository.findSolicitudByEncargoId(id);
	}

	@Transactional
	public void deleteSolicitud(final Solicitud s) {
		this.solicitudRepository.delete(s);
	}

	@Transactional
	public void deleteSolicitudById(final int id) {
		this.solicitudRepository.deleteById(id);
	}

}
