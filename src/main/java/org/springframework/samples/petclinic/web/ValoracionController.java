package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Valoracion;
import org.springframework.samples.petclinic.service.BeaverService;
import org.springframework.samples.petclinic.service.ValoracionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/beavers/{beaverId}/valoraciones")
public class ValoracionController {
    
    private final ValoracionService valoracionService;
    private final BeaverService beaverService;

    @Autowired
    public ValoracionController(final ValoracionService valoracionService, final BeaverService beaverService) {
        this.valoracionService = valoracionService;
        this.beaverService = beaverService;
    }

    //LIST
    @GetMapping("/list")
    public String listarValoraciones(@PathVariable("beaverId") final int beaverId, final ModelMap modelMap){

        String vista = ""; //REDIRECCIONAR A VISTA CORRECTA
        Iterable<Valoracion> valoraciones = this.valoracionService.findValoracionesByBeaverId(beaverId);
        modelMap.addAttribute("valoraciones", valoraciones);
        return vista;
    }
}
