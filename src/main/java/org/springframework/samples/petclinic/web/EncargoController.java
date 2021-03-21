
package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Encargo;

import org.springframework.samples.petclinic.model.User;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import antlr.collections.List;

@Controller
@RequestMapping("/beavers/{beaverId}/encargos")
public class EncargoController {
	
	private EncargoService	encargoService;
	private UserService		userService;
	private BeaverService	beaverService;
 	private static final String	VIEWS_ENCARGOS_CREATE_OR_UPDATE_FORM	= "encargos/createEncargosForm";

	@Autowired
	public EncargoController(final EncargoService encargoService, final UserService userService, final BeaverService beaverService, final AuthoritiesService authoritiesService) throws ClassNotFoundException {
		this.encargoService = encargoService;
		this.userService = userService;
		this.beaverService = beaverService;
	}


	@GetMapping(value = "/new")
	public String initCreationForm(final ModelMap model) {

		ArrayList<Boolean> booleanList = new ArrayList<Boolean>();
        booleanList.add(true);
        booleanList.add(false);

		Encargo encargo = new Encargo();
		model.addAttribute("encargo", encargo);
		model.addAttribute("booleanList", booleanList);
		return EncargoController.VIEWS_ENCARGOS_CREATE_OR_UPDATE_FORM;

	}

	@PostMapping(value = "/new")
	public String processCreationForm(@Valid Encargo encargo, BindingResult result, final ModelMap model/*, @RequestParam("urlImagen") MultipartFile imagen*/) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Beaver beaver = beaverService.findBeaverByUsername(currentPrincipalName);
		

		if (result.hasErrors() /*|| encargo.getTitulo()==null || encargo.getPrecio() == 0.0 || checkDesc(encargo.getDescripcion())*/) {
			model.addAttribute("encargo", encargo);
			return EncargoController.VIEWS_ENCARGOS_CREATE_OR_UPDATE_FORM;

		} else {
/*
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
*/

            if(beaver.getEncargos()==null){
                Set<Encargo> res = new HashSet<>();
                beaver.setEncargos(res);
            }

			encargo.setBeaver(beaver);
			beaver.addEncargo(encargo);
            encargoService.saveEncargo(encargo);
			beaverService.saveBeaver(beaver);

			return "redirect:/beavers/" + beaver.getId();
		}

	}
  
/* private Boolean checkDesc(String desc){
		Boolean res = false;

		if(desc.length()< 30 || desc.length() > 3000) res = true;

		return res;
	} */
	
	//lIST ENCARGOS

	//@GetMapping("beavers/{beaverId}/encargos/list")
	@GetMapping("/list")
	public String listarEncargos(@PathVariable("beaverId") final int beaverId, final ModelMap model) {

		Iterable<Encargo> encargos = this.encargoService.findEncargoByBeaverId(beaverId);
		model.addAttribute("encargos", encargos);
		model.addAttribute("beaverId", beaverId);
		return "encargos/listEncargos";

	}

	// SHOW ENCARGOS


	@GetMapping("/{encargoId}")
	public ModelAndView mostrarEncargo(@PathVariable("encargoId") final int encargoId) {

		ModelAndView vista = new ModelAndView("encargos/encargosDetails");
		Encargo encargo = new Encargo();
		Optional<Encargo> enC = this.encargoService.findEncargoById(encargoId);
		if (enC.isPresent()) {
			encargo = enC.get();
		}

		vista.addObject("encargo", encargo);

		return vista;
	}

	//Update Encargos

    @GetMapping(value = "/{encargoId}/edit")
    public String initUpdateForm(@PathVariable("encargoId") int encargoId, ModelMap model) {
        Encargo encargo = new Encargo();
        Optional<Encargo> enC = this.encargoService.findEncargoById(encargoId);

        if (enC.isPresent()) {
            encargo = enC.get();
        }

        model.addAttribute("encargo", encargo);

        return EncargoController.VIEWS_ENCARGOS_CREATE_OR_UPDATE_FORM;

    }


    @PostMapping(value = "/{encargoId}/edit")
    public String processUpdateForm(@Valid Encargo encargo, BindingResult result,
                                    @PathVariable("encargoId") int encargoId, ModelMap model
                                    /*@RequestParam("urlImagen") MultipartFile imagen*/) {


	    if (result.hasErrors()) {
	        model.addAttribute("encargo", encargo);
	        return EncargoController.VIEWS_ENCARGOS_CREATE_OR_UPDATE_FORM;

	    } else {
/*
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

 */


            encargoService.saveEncargo(encargo);

	        return "redirect:/beavers/{beaverId}";
        }

    }

	//Delete Encargo

	@RequestMapping(value = "/{encargoId}/delete")
	public String deleteEncargo(@PathVariable("encargoId") int encargoId) {

		Encargo encargo = this.encargoService.findEncargoByIntId(encargoId);
        Beaver b = encargo.getBeaver();
        b.getEncargos().remove(encargo);
        beaverService.saveBeaver(b);
        this.encargoService.deleteEncargoById(encargoId);
        return "encargos/todoOk";

	}

}
