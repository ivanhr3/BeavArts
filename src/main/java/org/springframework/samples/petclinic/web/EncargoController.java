
package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@RequestMapping("/beavers/{beaverId}")
public class EncargoController {

	private EncargoService	encargoService;
	private UserService		userService;
	private BeaverService	beaverService;


	@Autowired
	public EncargoController(final EncargoService encargoService, final UserService userService, final BeaverService beaverService, final AuthoritiesService authoritiesService) throws ClassNotFoundException {
		this.encargoService = encargoService;
		this.userService = userService;
		this.beaverService = beaverService;
	}


	@GetMapping(value = "/encargos/new")
	public String initCreationForm(final ModelMap model) {

		Encargo encargo = new Encargo();
		model.addAttribute("encargo", encargo);
		return "encargos/nuevo";

	}

	@PostMapping(value = "/encargos/new")
	public String processCreationForm(@Valid final Encargo encargo, final BindingResult result, final ModelMap model/*, @RequestParam("urlImagen") MultipartFile imagen*/) {

		//Beaver beaver = this.beaverService.findBeaverByIntId(beaverId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userService.findUserByUsername(currentPrincipalName);
        Beaver beaver = beaverService.findBeaverByUsername(currentPrincipalName);

		if (result.hasErrors()) {

			//model.addAttribute("beaver", beaver);
			model.addAttribute("encargo", encargo);
			return "encargos/nuevo";

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

	//lIST ENCARGOS

	@GetMapping("beavers/{beaverId}/encargos/list")
	public String listarEncargos(@PathVariable("beaverId") final int beaverId, final ModelMap model) {

		Iterable<Encargo> encargos = this.encargoService.findEncargoByBeaverId(beaverId);
		model.addAttribute("encargos", encargos);
		return "encargos/listEncargos";

	}

	// SHOW ENCARGOS

	@GetMapping("/encargos/{encargoId}")
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

    @GetMapping(value = "/encargos/{encargoId}/edit")
    public String initUpdateForm(@PathVariable("encargoId") int encargoId, ModelMap model) {
        Encargo encargo = new Encargo();
        Optional<Encargo> enC = this.encargoService.findEncargoById(encargoId);

        if (enC.isPresent()) {
            encargo = enC.get();
        }

        model.addAttribute("encargo", encargo);

        return "encargos/editar";

    }


    @PostMapping(value = "/encargos/{encargoId}/edit")
    public String processUpdateForm(@Valid Encargo encargo, BindingResult result,
                                    @PathVariable("encargoId") int encargoId, ModelMap model, @RequestParam("urlImagen") MultipartFile imagen) {


	    if (result.hasErrors()) {
	        model.addAttribute("encargo", encargo);
	        return "encargos/editar";

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


            encargoService.saveEncargo(encargo);

	        return "redirect:/beavers/{beaverId}";
        }

    }

	//Delete Encargo

	@RequestMapping(value = "/encargos/{encargoId}/delete")
	public String deleteEncargo(@PathVariable("encargoId") int encargoId) {

		Encargo encargo = this.encargoService.findEncargoByIntId(encargoId);
        Beaver b = encargo.getBeaver();
        b.getEncargos().remove(encargo);
        beaverService.saveBeaver(b);
        this.encargoService.deleteEncargoById(encargoId);
        return "encargos/todoOk";

	}

}
