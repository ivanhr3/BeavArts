
package org.springframework.samples.petclinic.service;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Factura;
import org.springframework.samples.petclinic.repository.FacturaRepository;
import org.springframework.stereotype.Service;

@Service
public class FacturaService {

	private final FacturaRepository facturaRepo;


	@Autowired
	public FacturaService(final FacturaRepository facturaRepo) {
		this.facturaRepo = facturaRepo;
	}

	@Transactional
	public void crearFactura(final Factura factura) {
		factura.setPaymentDate(LocalDate.now());
		this.facturaRepo.save(factura);
	}

	@Transactional
	public Iterable<Factura> findAllFacturas() {
		return this.facturaRepo.findAll();
	}

	@Transactional
	public Factura findFacturaById(final int id) {
		return this.facturaRepo.findFacturaByIntId(id);
	}

}
