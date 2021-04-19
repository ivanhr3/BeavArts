
package org.springframework.samples.petclinic.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Factura;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaRepository extends CrudRepository<Factura, Integer> {

	@Query("select f from Factura f where f.id = ?1")
	Factura findFacturaByIntId(int id);

	@Query("select f from Factura f where f.solicitud.id = ?1")
	Factura findFacturayBySolicitud(int id);
}
