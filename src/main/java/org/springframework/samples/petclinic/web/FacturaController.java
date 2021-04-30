
package org.springframework.samples.petclinic.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Estados;
import org.springframework.samples.petclinic.model.Factura;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.BeaverService;
import org.springframework.samples.petclinic.service.FacturaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FacturaController {

	private final FacturaService	facturaService;
	private final BeaverService		beaverService;


	@Autowired
	public FacturaController(final FacturaService facturaService, final BeaverService beaverService) throws ClassNotFoundException {
		this.facturaService = facturaService;
		this.beaverService = beaverService;
	}

	@GetMapping("/facturas/list")
	public String listFacturas(final ModelMap modelMap) {

		Beaver beav = this.beaverService.getCurrentBeaver();
		User user = beav.getUser();
		List<Authorities> auth = this.beaverService.findUserAuthorities(user);
		Boolean esAdmin = auth.get(0).getAuthority().equals("admin");

		Beaver me = this.beaverService.getCurrentBeaver();  //Obtenemos el beaver conectado
		if (me != null) {//añadido el if para los tests
			modelMap.put("myBeaverId", me.getId()); //añadimos el id a la vista
		}

		if (!esAdmin) {
			return "accesoNoAutorizado";

		} else {

			String vista = "facturas/listFacturas";
			List<Factura> facturas = (List<Factura>) this.facturaService.findAllFacturas();
			modelMap.addAttribute("facturas", facturas);

			return vista;
		}
	}

	@GetMapping("/facturas/{facturaId}")
	public ModelAndView mostrarFactura(@PathVariable("facturaId") final int facturaId, final ModelMap model) {

		final ModelAndView vista = new ModelAndView("facturas/facturasDetails");
		final Factura factura = this.facturaService.findFacturaById(facturaId);
		model.addAttribute("factura", factura);

		Beaver beav = this.beaverService.getCurrentBeaver();
		User user = beav.getUser();
		List<Authorities> auth = this.beaverService.findUserAuthorities(user);
		Boolean esAdmin = auth.get(0).getAuthority().equals("admin");

		Beaver me = this.beaverService.getCurrentBeaver();  //Obtenemos el beaver conectado
		if (me != null) {//añadido el if para los tests
			model.put("myBeaverId", me.getId()); //añadimos el id a la vista
		}

		if (!esAdmin) {
			return new ModelAndView("accesoNoAutorizado");
		} else { //Estos dos atributos deben aparecer en la vista:
			Double precioConComision = factura.getPrecio() * 0.95;
			Double comision = factura.getPrecio() * 0.05;
			model.put("precioConComision", precioConComision);
			model.put("comision", comision);
			return vista;
		}
	}

	@PostMapping("/facturas/{facturaId}")
	public String finalizarFactura(@PathVariable("facturaId") final int facturaId, final ModelMap model) {

		String vista = "";
		Beaver me = this.beaverService.getCurrentBeaver();
		User user = me.getUser();
		List<Authorities> auth = this.beaverService.findUserAuthorities(user);
		Boolean esAdmin = auth.get(0).getAuthority().equals("admin");
		Factura factura = this.facturaService.findFacturaById(facturaId);

		if (!esAdmin || factura.getEstado().equals(Estados.FINALIZADO)) {
			vista = "accesoNoAutorizado"; //El boton de finalizar no debe aparecer si está ya finalizada
		} else {
			factura.setEstado(Estados.FINALIZADO);
			this.facturaService.saveFactura(factura);
			vista = "redirect:/facturas/list";
		}

		return vista;

	}
}
