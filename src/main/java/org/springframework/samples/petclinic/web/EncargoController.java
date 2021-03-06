
package org.springframework.samples.petclinic.web;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Encargo;
import org.springframework.samples.petclinic.model.Factura;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.BeaverService;
import org.springframework.samples.petclinic.service.EncargoService;
import org.springframework.samples.petclinic.service.FacturaService;
import org.springframework.samples.petclinic.service.SolicitudService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/beavers/{beaverId}/encargos")
public class EncargoController {

	private final EncargoService	encargoService;
	private final BeaverService		beaverService;
	private final SolicitudService	solicitudService;
	private final FacturaService 	facturaService;
	private static final String		VIEWS_ENCARGOS_CREATE_OR_UPDATE_FORM	= "encargos/createEncargosForm";


	@Autowired
	public EncargoController(final EncargoService encargoService, final BeaverService beaverService, final SolicitudService solicitudService, final AuthoritiesService authoritiesService, final FacturaService facturaService) throws ClassNotFoundException {
		this.encargoService = encargoService;
		this.beaverService = beaverService;
		this.solicitudService = solicitudService;
		this.facturaService = facturaService;
	}

	//Create

	@GetMapping(value = "/new")
	public String initCreationForm(final ModelMap model) {

		Beaver beaver = this.beaverService.getCurrentBeaver();
		model.put("myBeaverId", beaver.getId()); //A??adido para usar las url del header

		if (beaver.getEspecialidades().isEmpty()) { //DEBE COMPROBARSE QUE ESTA VAC??O, NO SI ES NULO
			model.put("url", true);
			model.put("errorEspecialidades", "*No se puede crear un encargo sin tener especialidades asignadas.");
			model.addAttribute("hayEncargos", false); //Se ha a??adido para que no muestre los datos si hay error

			return "encargos/listEncargos";  //TODO: Front: Redirecci??n hacia atr??s *DONE*
		} else {
			final Encargo encargo = new Encargo();
			model.addAttribute("encargo", encargo);
			return EncargoController.VIEWS_ENCARGOS_CREATE_OR_UPDATE_FORM;
		}
	}

	@PostMapping(value = "/new")
	public String processCreationForm(@Valid final Encargo encargo, final BindingResult result, final ModelMap model) {
		Beaver beaver = this.beaverService.getCurrentBeaver();

		model.put("myBeaverId", beaver.getId()); //A??adido para usar las url del header

		if (result.hasErrors() /* encargo.getTitulo()==null encargo.getPrecio() == 0.0 || checkDesc(encargo.getDescripcion()) */) {
			model.addAttribute("encargo", encargo);
			return EncargoController.VIEWS_ENCARGOS_CREATE_OR_UPDATE_FORM;

		} else {
			if (beaver.getEncargos().isEmpty() || beaver.getEncargos() == null) {
				final Set<Encargo> res = new HashSet<>();
				res.add(encargo);
				encargo.setBeaver(beaver);
				beaver.setEncargos(res);

			} else {
				beaver.getEncargos().add(encargo);
				encargo.setBeaver(beaver);
			}
			this.encargoService.CrearEncargo(encargo, beaver);
			return "redirect:/beavers/" + beaver.getId() + "/encargos/" + encargo.getId();
		}

	}

	//List

	@GetMapping("/list")
	public String listarEncargos(@PathVariable("beaverId") final int beaverId, final ModelMap model) {

		if (this.beaverService.getCurrentBeaver() != null) {
			Beaver me = this.beaverService.getCurrentBeaver();
			model.put("myBeaverId", me.getId()); //A??adido para usar las url del header
		}

		Beaver beaver = this.beaverService.findBeaverByIntId(beaverId);
		model.addAttribute("beaverId", beaverId);
		model.addAttribute("beaver", beaver);
		if (this.beaverService.getCurrentBeaver() == null) {  // deben listar solo usuarios logueados, ??LLevar a otra vista?
			return "accesoNoAutorizado"; //Acceso no autorizado
		} else {
			if (beaver.getEncargos().isEmpty()) {
				model.addAttribute("hayEncargos", false); //TODO: Usar este boolean para controlar la falta de encargos *DONE*
			} else if (beaver.equals(this.beaverService.getCurrentBeaver())) {
				final Iterable<Encargo> encargos = this.encargoService.findEncargoByBeaverId(beaverId);
				model.addAttribute("encargos", encargos);
			} else {
				// para mostrar los encargos de un beaver a otro usuario que la disponibilidad debe ser True
				final Iterable<Encargo> encargos = this.encargoService.findEncargoByAnotherBeaverId(beaverId);
				model.addAttribute("encargos", encargos);
			}
			return "encargos/listEncargos";
		}
	}

	// Show

	@GetMapping("/{encargoId}")
	public ModelAndView mostrarEncargo(@PathVariable("encargoId") final int encargoId, final ModelMap model) {

		final ModelAndView vista = new ModelAndView("encargos/encargosDetails");
		final Encargo encargo = this.encargoService.findEncargoById(encargoId);
		model.addAttribute("encargo", encargo);

		Beaver beaver = this.beaverService.getCurrentBeaver();//Necesario para ver el id y usar las url
		model.addAttribute("myBeaverId", beaver.getId());

		User user = beaver.getUser();
		List<Authorities> auth = this.beaverService.findUserAuthorities(user);
		Boolean esAdmin = auth.get(0).getAuthority().equals("admin");

		if (esAdmin) {
			model.addAttribute("esAdmin", true); //Este par??metro es la condicion para ver el boton de delete sin ser el creador
		}

		if (this.beaverService.getCurrentBeaver() == encargo.getBeaver()) {
			model.addAttribute("createdByUser", true); //TODO: Front: S??lo quien cre?? el encargo puede actualizarlo. Mostrad el bot??n s??lo en este caso.
		} else {
			model.addAttribute("createdByUser", false); //TODO: Front: Controla que el usuario que est?? viendo la vista es el mismo que cre?? el encargo.
			//TODO: Front: El creador del encargo NO puede crear solicitudes, mostrad el bot??n si este atributo es false.
		}
		return vista;
	}

	//Update

	@GetMapping(value = "/{encargoId}/edit")
	public String initUpdateForm(@PathVariable("encargoId") final int encargoId, final ModelMap model) {
		Beaver beaver = this.beaverService.getCurrentBeaver();
		final Encargo enC = this.encargoService.findEncargoById(encargoId);

		model.put("myBeaverId", beaver.getId()); //A??adido para usar las url del header
		model.put("editando", true); //Para que los botones no cambien

		if (enC.getBeaver() != beaver) {
			return "accesoNoAutorizado"; //Acceso no Autorizado -Nombre cambiado para homogeneidad con las otras ramas-
		} else {
			model.addAttribute("encargo", enC);
			model.addAttribute("isDisponible", enC.isDisponibilidad()); //TODO: Poner los campos de edici??n a READ ONLY cuando isDisponible sea True
			return EncargoController.VIEWS_ENCARGOS_CREATE_OR_UPDATE_FORM;
		} //TODO: Falta implementar la regla de negocio 11
	}

	@PostMapping(value = "/{encargoId}/edit")
	public String processUpdateForm(@Valid final Encargo encargo, final BindingResult result, @PathVariable("encargoId") final int encargoId, final ModelMap model) {

		Beaver beaver = this.beaverService.getCurrentBeaver();
		model.put("myBeaverId", beaver.getId()); //A??adido para usar las url del header
		model.put("editando", true); //Para que los botones no cambien

		final Encargo enC = this.encargoService.findEncargoById(encargoId);
		model.addAttribute("isDisponible", enC.isDisponibilidad()); //TODO: Poner los campos de edici??n a READ ONLY cuando isDisponible sea True

		if (result.hasErrors()) {
			model.addAttribute("encargo", encargo);
			return EncargoController.VIEWS_ENCARGOS_CREATE_OR_UPDATE_FORM;
		} else if (enC.isDisponibilidad() == true && encargo.isDisponibilidad() == true) {
			model.addAttribute("encargo", encargo);
			result.rejectValue("disponibilidad", "No se puede editar un encargo que est?? disponible.");
			model.put("url", true);
			model.put("errorDisponibilidad", "No se puede editar un encargo que est?? 'Disponible'. Primero c??mbielo a 'No disponible' y luego actualice el encargo.");
			return EncargoController.VIEWS_ENCARGOS_CREATE_OR_UPDATE_FORM;
		} else {
			BeanUtils.copyProperties(encargo, enC, "id", "beaver");
			this.encargoService.saveEncargo(enC);
			return "redirect:/beavers/{beaverId}/encargos/" + enC.getId();
		}

	}

	//Delete

	@RequestMapping(value = "/{encargoId}/delete")
	public String deleteEncargo(@PathVariable("beaverId") final int beaverId, @PathVariable("encargoId") final int encargoId, final ModelMap model) {

		Beaver beav = this.beaverService.getCurrentBeaver();
		User user = beav.getUser();
		List<Authorities> auth = this.beaverService.findUserAuthorities(user);
		Boolean esAdmin = auth.get(0).getAuthority().equals("admin");

		if (this.beaverService.getCurrentBeaver() != this.beaverService.findBeaverByIntId(beaverId) && !esAdmin) {
			return "accesoNoAutorizado"; // Acceso no autorizado
		} else if (esAdmin) {
			for (Solicitud s : this.encargoService.findEncargoById(encargoId).getSolicitud()) {
				Factura factura = this.facturaService.findFacturaBySolicitud(s);
				if(factura != null){
					factura.setSolicitud(null);
					this.facturaService.saveFactura(factura);
				}
				this.solicitudService.deleteSolicitud(s);
			}
			this.encargoService.deleteEncargoById(encargoId);
			return "redirect:/beavers/" + beaverId + "/encargos/list";
		} else {
			this.encargoService.deleteEncargoById(encargoId);
			return "redirect:/beavers/" + beaverId + "/misPublicaciones";
		}
	} //TODO: Falta la regla de Negocio 11

}
