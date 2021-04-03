
package org.springframework.samples.petclinic.web;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Encargo;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.BeaverService;
import org.springframework.samples.petclinic.service.EncargoService;
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
	private static final String		VIEWS_ENCARGOS_CREATE_OR_UPDATE_FORM	= "encargos/createEncargosForm";


	@Autowired
	public EncargoController(final EncargoService encargoService, final BeaverService beaverService, final AuthoritiesService authoritiesService) throws ClassNotFoundException {
		this.encargoService = encargoService;
		this.beaverService = beaverService;
	}

	//Create

	@GetMapping(value = "/new")
	public String initCreationForm(final ModelMap model) {

		Beaver beaver = this.beaverService.getCurrentBeaver();
		model.put("myBeaverId", beaver.getId()); //Añadido para usar las url del header

		if (beaver.getEspecialidades().isEmpty()) { //DEBE COMPROBARSE QUE ESTA VACÍO, NO SI ES NULO

			model.put("errorEspecialidades", "*No se puede crear un encargo sin tener especialidades asignadas.");
			model.addAttribute("hayEncargos", false); //Se ha añadido para que no muestre los datos si hay error

			return "encargos/listEncargos";  //TODO: Front: Redirección hacia atrás *DONE*
		} else {
			final Encargo encargo = new Encargo();
			model.addAttribute("encargo", encargo);
			return EncargoController.VIEWS_ENCARGOS_CREATE_OR_UPDATE_FORM;
		}
	}

	@PostMapping(value = "/new")
	public String processCreationForm(@Valid final Encargo encargo, final BindingResult result, final ModelMap model) {
		Beaver beaver = this.beaverService.getCurrentBeaver();

		model.put("myBeaverId", beaver.getId()); //Añadido para usar las url del header

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

		Beaver me = this.beaverService.getCurrentBeaver();
		model.put("myBeaverId", me.getId()); //Añadido para usar las url del header

		Beaver beaver = this.beaverService.findBeaverByIntId(beaverId);
		model.addAttribute("beaverId", beaverId);
		if (this.beaverService.getCurrentBeaver() == null) {  // deben listar solo usuarios logueados, ¿LLevar a otra vista?
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

		if (this.beaverService.getCurrentBeaver() == encargo.getBeaver()) {
			model.addAttribute("createdByUser", true); //TODO: Front: Sólo quien creó el encargo puede actualizarlo. Mostrad el botón sólo en este caso.
		} else {
			model.addAttribute("createdByUser", false); //TODO: Front: Controla que el usuario que está viendo la vista es el mismo que creó el encargo.
			//TODO: Front: El creador del encargo NO puede crear solicitudes, mostrad el botón si este atributo es false.
		}
		return vista;
	}

	//Update

	@GetMapping(value = "/{encargoId}/edit")
	public String initUpdateForm(@PathVariable("encargoId") final int encargoId, final ModelMap model) {
		Beaver beaver = this.beaverService.getCurrentBeaver();
		final Encargo enC = this.encargoService.findEncargoById(encargoId);

		model.put("myBeaverId", beaver.getId()); //Añadido para usar las url del header
		model.put("editando", true); //Para que los botones no cambien

		if (enC.getBeaver() != beaver) {
			return "accesoNoAutorizado"; //Acceso no Autorizado -Nombre cambiado para homogeneidad con las otras ramas-
		} else {
			model.addAttribute("encargo", enC);
			model.addAttribute("isDisponible", enC.isDisponibilidad()); //TODO: Poner los campos de edición a READ ONLY cuando isDisponible sea True
			return EncargoController.VIEWS_ENCARGOS_CREATE_OR_UPDATE_FORM;
		} //TODO: Falta implementar la regla de negocio 11
	}

	@PostMapping(value = "/{encargoId}/edit")
	public String processUpdateForm(@Valid final Encargo encargo, final BindingResult result, @PathVariable("encargoId") final int encargoId, final ModelMap model) {

		Beaver beaver = this.beaverService.getCurrentBeaver();
		model.put("myBeaverId", beaver.getId()); //Añadido para usar las url del header
		model.put("editando", true); //Para que los botones no cambien

		final Encargo enC = this.encargoService.findEncargoById(encargoId);
		model.addAttribute("isDisponible", enC.isDisponibilidad()); //TODO: Poner los campos de edición a READ ONLY cuando isDisponible sea True

		if (result.hasErrors()) {
			model.addAttribute("encargo", encargo);
			return EncargoController.VIEWS_ENCARGOS_CREATE_OR_UPDATE_FORM;
		} else if (enC.isDisponibilidad() == true && encargo.isDisponibilidad() == true) {
			model.addAttribute("encargo", encargo);
			result.rejectValue("disponibilidad", "No se puede editar un encargo que esté disponible.");
			model.put("errorDisponibilidad", "No se puede editar un encargo que esté disponible.");
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
		if (this.beaverService.getCurrentBeaver() != this.beaverService.findBeaverByIntId(beaverId)) {
			return "accesoNoAutorizado"; // Acceso no autorizado
		} else {
			this.encargoService.deleteEncargoById(encargoId);
			return "redirect:/beavers/" + beaverId + "/encargos/list";
		}
	} //TODO: Falta la regla de Negocio 11

}
