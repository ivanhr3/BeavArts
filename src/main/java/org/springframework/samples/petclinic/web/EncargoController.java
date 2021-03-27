package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

	private final EncargoService		encargoService;
	private final BeaverService			beaverService;
	private static final String	VIEWS_ENCARGOS_CREATE_OR_UPDATE_FORM	= "encargos/createEncargosForm";



	@Autowired
	public EncargoController(final EncargoService encargoService, final BeaverService beaverService, final AuthoritiesService authoritiesService) throws ClassNotFoundException {
		this.encargoService = encargoService;
		this.beaverService = beaverService;
	}

	//Create

	@GetMapping(value = "/new")
	public String initCreationForm(final ModelMap model) {
		Beaver beaver = beaverService.getCurrentBeaver();
		if(beaver.getEspecialidades().isEmpty()){
			model.put("errorEspecialidades", "No se puede crear un encargo sin tener especialidades asignadas.");
			return "/"; //TODO: Front: Redirección hacia atrás
		} else {
		final Encargo encargo = new Encargo();
		model.addAttribute("encargo", encargo);
		return EncargoController.VIEWS_ENCARGOS_CREATE_OR_UPDATE_FORM;
		}
	}

	@PostMapping(value = "/new")
	public String processCreationForm(@Valid final Encargo encargo, final BindingResult result, final ModelMap model) {
		Beaver beaver = beaverService.getCurrentBeaver();

		if (result.hasErrors() /* encargo.getTitulo()==null encargo.getPrecio() == 0.0 || checkDesc(encargo.getDescripcion()) */) {
			model.addAttribute("encargo", encargo);
			return EncargoController.VIEWS_ENCARGOS_CREATE_OR_UPDATE_FORM;

		} else {
			if (beaver.getEncargos() == null) {
				final Set<Encargo> res = new HashSet<>();
				res.add(encargo);
				encargo.setBeaver(beaver);
				beaver.setEncargos(res);
				
			} else {
			beaver.getEncargos().add(encargo);
			encargo.setBeaver(beaver);
			}
			encargoService.CrearEncargo(encargo, beaver);
			return "redirect:/beavers/" + beaver.getId() + "/encargos/" + encargo.getId();
		}

	}

	//List

	@GetMapping("/list")
	public String listarEncargos(@PathVariable("beaverId") final int beaverId, final ModelMap model) {

		Beaver beaver = this.beaverService.findBeaverByIntId(beaverId);
		model.addAttribute("beaverId", beaverId);
		if(beaver.getEncargos().isEmpty()){
			model.addAttribute("hayEncargos", false); //TODO: Usar este boolean para controlar la falta de encargos
		} else {
		final Iterable<Encargo> encargos = this.encargoService.findEncargoByBeaverId(beaverId);
		model.addAttribute("encargos", encargos);
		}
		return "encargos/listEncargos";
	}

	// Show

	@GetMapping("/{encargoId}")
	public ModelAndView mostrarEncargo(@PathVariable("encargoId") final int encargoId, final ModelMap model) {

		final ModelAndView vista = new ModelAndView("encargos/encargosDetails");
		final Encargo encargo = this.encargoService.findEncargoById(encargoId);
		model.addAttribute("encargo", encargo);

		if(beaverService.getCurrentBeaver() == encargo.getBeaver()){
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
		Beaver beaver = beaverService.getCurrentBeaver();
		final Encargo enC = this.encargoService.findEncargoById(encargoId);
		if(enC.getBeaver() == beaver){
			return "accessNotAuthorized"; //Acceso no Autorizado
		} else {
		model.addAttribute("encargo", enC);
		return EncargoController.VIEWS_ENCARGOS_CREATE_OR_UPDATE_FORM;
		} //TODO: Falta implementar la regla de negocio 11
	}

	@PostMapping(value = "/{encargoId}/edit")
	public String processUpdateForm(@Valid final Encargo encargo, final BindingResult result, @PathVariable("encargoId") final int encargoId, final ModelMap model) {

		if (result.hasErrors() || encargo.isDisponibilidad() == true) {
			model.addAttribute("encargo", encargo);
			return EncargoController.VIEWS_ENCARGOS_CREATE_OR_UPDATE_FORM;

		} else {

			final Encargo enC = this.encargoService.findEncargoById(encargoId);
			BeanUtils.copyProperties(encargo, enC, "id", "beaver");
			this.encargoService.saveEncargo(enC);
			return "redirect:/beavers/{beaverId}/encargos/" + enC.getId();
		}

	}

	//Delete

	@RequestMapping(value = "/{encargoId}/delete")
	public String deleteEncargo(@PathVariable("beaverId") final int beaverId,@PathVariable("encargoId") final int encargoId, final ModelMap model) {
		this.encargoService.deleteEncargoById(encargoId);
		return "redirect:/beavers/"+beaverId+"/encargos/list";
	} //TODO: Falta la regla de Negocio 11

}
