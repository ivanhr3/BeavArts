package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Encargo;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.BeaverService;
import org.springframework.samples.petclinic.service.EncargoService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/beavers/{beaverId}")
public class EncargoController {

    @Autowired
    private EncargoService encargoService;

    @Autowired
    private UserService userService;

    @Autowired
    private BeaverService beaverService;


    @GetMapping(value = "/encargos/new")
    public String crearEncargo(@PathVariable("beaverId") int beaverId, ModelMap model) {

        //Beaver beaver = new Beaver();
        //String beaverIdString = String.valueOf(beaverId);
        Beaver beaver = this.beaverService.findBeaverByIntId(beaverId);
        Encargo encargo = new Encargo();
        model.addAttribute("encargo", encargo);
        model.addAttribute("beaver", beaver);
        return "encargos/nuevo";

    }

    @PostMapping(value = "/encargos/new")
    public String guardarEncargo(@PathVariable("beaverId") int beaverId, @Valid Encargo encargo, BindingResult result,
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
            return "redirect:/beavers/beaver{id}";
        }

    }



    //lIST ENCARGOS

    @GetMapping("/list")
    public String listarEncargos(@PathVariable("beaverId") int beaverId, ModelMap model) {

        Iterable<Encargo> encargos = this.encargoService.findEncargoByBeaverId(beaverId);
        model.addAttribute("encargos", encargos);
        return "encargos/listEncargos";

    }

    // SHOW ENCARGOS

    @GetMapping("/encargoInfo/{encargoId}")
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
