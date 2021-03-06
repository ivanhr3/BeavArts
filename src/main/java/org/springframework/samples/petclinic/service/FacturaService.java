
package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Factura;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.repository.FacturaRepository;
import org.springframework.stereotype.Service;

@Service
public class FacturaService {

	private final FacturaRepository facturaRepo;

	@Autowired
	private SolicitudService solicitudService;

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
	public void saveFactura(final Factura factura){
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

	@Transactional
	public Factura findFacturaBySolicitud(Solicitud sol){
		return this.facturaRepo.findFacturayBySolicitud(sol.getId());
	}

	@Transactional
	public void unbindFacturas(Beaver beaver){
		Collection<Solicitud> sols = this.solicitudService.findAllSolicitudesFromBeaverEncargosAndAnuncios(beaver);
		Factura factura;
		Factura newFactura;
		if(!sols.isEmpty()){
			for(Solicitud s: sols){
				factura = this.facturaRepo.findFacturayBySolicitud(s.getId());
				if(factura != null){
				newFactura = new Factura();
				newFactura.setEmailBeaver(factura.getEmailBeaver());
				newFactura.setEmailPayer(factura.getEmailPayer());
				newFactura.setEstado(factura.getEstado());
				newFactura.setPaymentDate(factura.getPaymentDate());
				newFactura.setPrecio(factura.getPrecio());
				newFactura.setRecibido(factura.getRecibido());
				this.facturaRepo.save(newFactura);
				}
			}
		}
	}

}
