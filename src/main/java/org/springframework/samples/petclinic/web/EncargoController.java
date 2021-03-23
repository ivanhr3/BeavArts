
package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Encargo;

import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.BeaverRepository;
import org.springframework.samples.petclinic.service.AuthoritiesService;

import org.springframework.samples.petclinic.service.BeaverService;
import org.springframework.samples.petclinic.service.EncargoService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@RequestMapping("/beavers/{beaverId}")
public class EncargoController {

    private EncargoService encargoService;
    private UserService userService;
    private BeaverService beaverService;


    @Autowired
	public EncargoController(final EncargoService encargoService, final UserService userService,
			final BeaverService beaverService, final AuthoritiesService authoritiesService) throws ClassNotFoundException{
		this.encargoService = encargoService;
		this.userService = userService;
		this.beaverService = beaverService;
	}

    @GetMapping(value = "/encargos/new")
    public String initCreationForm(ModelMap model) {

        Beaver beaver = new Beaver();
        Encargo encargo = new Encargo();
        encargo.setBeaver(beaver);
        model.addAttribute("encargo", encargo);
        model.addAttribute("beaver", beaver);
        return "encargos/nuevo";

    }

    @PostMapping(value = "/encargos/new")
    public String processCreationForm(@PathVariable("beaverId") int beaverId, @Valid Encargo encargo, BindingResult result,
                                  ModelMap model) {

        Beaver beaver = this.beaverService.findBeaverByIntId(beaverId);

        if (result.hasErrors()) {

            model.addAttribute("beaver", beaver);
            model.addAttribute("encargo", encargo);
            return "encargos/nuevo";
        }
        else {
            encargo.setBeaver(beaver);
            this.encargoService.saveEncargo(encargo);
            beaver.getEncargos().add(encargo);
            this.beaverService.saveBeaver(beaver);

            return "redirect:/beavers/" + beaverId;
        }

    }



    //lIST ENCARGOS

    @GetMapping("/beavers/{beaverId}/encargos/list")
    public String listarEncargos(@PathVariable("beaverId") int beaverId, ModelMap model) {

        Iterable<Encargo> encargos = this.encargoService.findEncargoByBeaverId(beaverId);
        model.addAttribute("encargos", encargos);
        return "encargos/listEncargos";

    }

    // SHOW ENCARGOS

    @GetMapping("/encargos/{encargoId}")
    public ModelAndView mostrarEncargo(@PathVariable("encargoId") int encargoId){

        ModelAndView vista = new ModelAndView("encargos/encargosDetails");
        Encargo encargo = new Encargo();
        Optional<Encargo> enC = this.encargoService.findEncargoById(encargoId);
        if(enC.isPresent()){
            encargo = enC.get();
        }

        vista.addObject("encargo", encargo);

        return vista;
    }

    // CREATE DE OTRA FORMA

    /*
    @GetMapping("/encargos/nuevo")
    public String initCreationForm(Beaver beaver, ModelMap model) {

        Encargo encargo = new Encargo();
        model.addAttribute("encargo", encargo);
        return "encargos/nuevo";
    }

    @PostMapping("encargos/nuevo")
    public String processCreationForm(Beaver beaver, @Valid Encargo encargo, BindingResult result,
                                      ModelMap model) {

        if (result.hasErrors()) {
            model.addAttribute("encargo", encargo);
            return "encargos/nuevo";
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


            encargo.setBeaver(beaver);
            //beaver.getEncargos().add(encargo);
            beaver.getEncargos();
            this.encargoService.saveEncargo(encargo);

            return "redirect:/beavers/{beaverId}";
        }



    }

    */






    //UPDATE DE ENCARGO

    /*

    @GetMapping(value = "/encargos/{encargoId}/edit")
    public String initUpdateForm(@PathVariable("encargoId") int encargoId, ModelMap model) {

        Encargo encargo = new Encargo();
        Optional<Encargo> enC = this.encargoService.findEncargoById(encargoId);
        if (enC.isPresent()) {
            encargo = enC.get();
        }
        model.addAttribute("encargo", encargo);
        return "vista form";
    }


    @PostMapping(value = "/encargos/{encargoId}/edit")
    public String processUpdateForm(@Valid Encargo encargo, BindingResult result, Beaver beaver,@PathVariable("encargoId") int encargoId, ModelMap model) {
        if (result.hasErrors()) {
            model.addAttribute("encargo", encargo);
            return "vista form";
        }
        else {

            Encargo encargo1 = this.encargoService.saveEncargo(encargo);
            model.addAttribute("encargo", encargo);


            return "redirect:/beavers/{beaverId}";
        }
    }


     */

    //Delete Encargo

    @RequestMapping(value = "/encargos/delete")
    public String deleteEncargo(@RequestParam("encargoId") int encargoId) {

        Optional<Encargo> p = this.encargoService.findEncargoById(encargoId);
        if (p.isPresent()) {
                Encargo encargo = p.get();
                Beaver b = encargo.getBeaver();
                b.getEncargos().removeIf(x -> encargo.getId() == encargoId);
                this.beaverService.saveBeaver(b);
                this.encargoService.deleteEncargoById(encargoId);
                return "encargos/todoOk";

        }
        else {
            return "exception";
        }

    }

}
