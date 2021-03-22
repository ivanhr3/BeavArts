
package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Encargo;
import org.springframework.samples.petclinic.model.Enum.EncargoStatus;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.BeaverService;
import org.springframework.samples.petclinic.service.EncargoService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/beavers/{beaverId}/encargos")
public class EncargoController {

	private EncargoService		encargoService;
	private UserService			userService;
	private BeaverService		beaverService;
	private static final String	VIEWS_ENCARGOS_CREATE_OR_UPDATE_FORM	= "encargos/createEncargosForm";


	@Autowired
	public EncargoController(final EncargoService encargoService, final UserService userService, final BeaverService beaverService, final AuthoritiesService authoritiesService) throws ClassNotFoundException {
		this.encargoService = encargoService;
		this.userService = userService;
		this.beaverService = beaverService;
	}

	@ModelAttribute("status")
	public List<EncargoStatus> populateStatus() {
		List<EncargoStatus> status = new ArrayList<>();
		status.add(EncargoStatus.Si);
		status.add(EncargoStatus.No);
		return status;
	}

	@GetMapping(value = "/new")
	public String initCreationForm(final ModelMap model) {

		Encargo encargo = new Encargo();
		model.addAttribute("encargo", encargo);
		return EncargoController.VIEWS_ENCARGOS_CREATE_OR_UPDATE_FORM;

	}

	@PostMapping(value = "/new")
	public String processCreationForm(@Valid final Encargo encargo, final BindingResult result, final ModelMap model, @RequestParam("file") final MultipartFile imagen) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		Beaver beaver = this.beaverService.findBeaverByUsername(currentPrincipalName);

		if (result.hasErrors() /* encargo.getTitulo()==null encargo.getPrecio() == 0.0 || checkDesc(encargo.getDescripcion()) */) {
			model.addAttribute("encargo", encargo);
			return EncargoController.VIEWS_ENCARGOS_CREATE_OR_UPDATE_FORM;

		} else {
			if (!imagen.isEmpty()) {
				Path directorioImagen = Paths.get("src/main/resources/static/resources/images/imagenes");
				String rutaAbsoluta = directorioImagen.toFile().getAbsolutePath();
				try {
					byte[] bytesImg = imagen.getBytes();
					Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
					Files.write(rutaCompleta, bytesImg);
					encargo.setPhoto(imagen.getOriginalFilename());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (beaver.getEncargos() == null) {
				Set<Encargo> res = new HashSet<>();
				beaver.setEncargos(res);
			}

			encargo.setBeaver(beaver);
			beaver.addEncargo(encargo);
			this.encargoService.saveEncargo(encargo);
			this.beaverService.saveBeaver(beaver);

			return "redirect:/beavers/" + beaver.getId() + "/encargos/" + encargo.getId();
		}

	}

	//lIST ***MIS*** ENCARGOS

	@GetMapping("/list")
	public String listarEncargos(@PathVariable("beaverId") final int beaverId, final ModelMap model) {

		Iterable<Encargo> encargos = this.encargoService.findEncargoByBeaverId(beaverId);
		model.addAttribute("encargos", encargos);
		model.addAttribute("beaverId", beaverId);
		return "encargos/listEncargos";

	}

	// SHOW ENCARGOS

	@GetMapping("/{encargoId}")
	public ModelAndView mostrarEncargo(@PathVariable("encargoId") final int encargoId, final ModelMap model) {

		ModelAndView vista = new ModelAndView("encargos/encargosDetails");
		Encargo encargo = this.encargoService.findEncargoById(encargoId);
		model.addAttribute("encargo", encargo);

		return vista;
	}

	//Update Encargos

	@GetMapping(value = "/{encargoId}/edit")
	public String initUpdateForm(@PathVariable("encargoId") final int encargoId, final ModelMap model) {

		Encargo enC = this.encargoService.findEncargoById(encargoId);

		model.addAttribute("encargo", enC);

		return EncargoController.VIEWS_ENCARGOS_CREATE_OR_UPDATE_FORM;

	}

	@PostMapping(value = "/{encargoId}/edit")
	public String processUpdateForm(@Valid final Encargo encargo, final BindingResult result, @PathVariable("encargoId") final int encargoId, final ModelMap model, @RequestParam("file") final MultipartFile imagen) {

		if (result.hasErrors()) {
			model.addAttribute("encargo", encargo);
			return EncargoController.VIEWS_ENCARGOS_CREATE_OR_UPDATE_FORM;

		} else {

			if (!imagen.isEmpty()) {
				Path directorioImagen = Paths.get("src/main/resources/static/resources/images/imagenes");
				String rutaAbsoluta = directorioImagen.toFile().getAbsolutePath();
				try {
					byte[] bytesImg = imagen.getBytes();
					Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
					Files.write(rutaCompleta, bytesImg);
					encargo.setPhoto(imagen.getOriginalFilename());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			Encargo enC = this.encargoService.findEncargoById(encargoId);

			BeanUtils.copyProperties(encargo, enC, "id", "beaver");

			this.encargoService.saveEncargo(enC);

			return "redirect:/beavers/{beaverId}/encargos/" + enC.getId();
		}

	}

	//Delete Encargo

	@RequestMapping(value = "/{encargoId}/delete")
	public String deleteEncargo(@PathVariable("encargoId") final int encargoId, final ModelMap model) {

		Encargo encargo = this.encargoService.findEncargoByIntId(encargoId);
		Beaver b = encargo.getBeaver();
		b.getEncargos().remove(encargo);
		this.beaverService.saveBeaver(b);
		this.encargoService.deleteEncargoById(encargoId);

		model.addAttribute("encargo", encargo);

		return "encargos/todoOk";

	}

}
