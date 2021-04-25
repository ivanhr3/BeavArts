package org.springframework.samples.petclinic.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Valoracion;
import org.springframework.samples.petclinic.service.BeaverService;
import org.springframework.samples.petclinic.service.ValoracionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView listarValoraciones(@PageableDefault(value = 5, page= 0) Pageable pageable, @PathVariable("beaverId") final int beaverId){

        ModelAndView vista = new ModelAndView("valoracion/lista"); //REDIRECCIONAR A VISTA CORRECTA
        Beaver beaver = this.beaverService.findBeaverByIntId(beaverId);
        vista.getModel().put("beaverId", beaverId);
        vista.getModel().put("beaver", beaver);

		//PARA QUE FUNCIONE EL BOTÓN MI PERFIL
		Beaver current = this.beaverService.getCurrentBeaver();
		if(current != null){
            vista.getModel().put("myBeaverId", current.getId());
	        }

        if (beaver.getValoraciones().isEmpty()) {
            vista.getModel().put("hayValoraciones", false); //Control de falta de valoraciones
        }
        Page<Valoracion> valoraciones = this.valoracionService.findValoracionesByBeaverId(beaverId, pageable);
        vista.getModel().put("valoraciones", valoraciones);
        return vista;
    }

    //Creacion de Valoraciones

    @GetMapping(value = "/create")
    public String initCreationForm(@PathVariable("beaverId") final int beaverId, ModelMap model){
        Beaver current = beaverService.getCurrentBeaver();
        Beaver reciever = beaverService.findBeaverByIntId(beaverId);

        if(current  == null|| current == reciever){
            return "accesoNoAutorizado";
        } else {
            model.addAttribute("myBeaverId", current.getId());
            final Valoracion valoracion = new Valoracion();
            model.addAttribute("valoracion", valoracion);
            return "valoracion/createValoracion"; //TODO FRONT: Añadir vista de creación aquí
        }
    }

    @PostMapping(value = "/create")
    public String processCreationForm(@PathVariable("beaverId") final int beaverId, @Valid Valoracion valoracion, final BindingResult result, final ModelMap model){
        Beaver current = this.beaverService.getCurrentBeaver();
        Beaver reciever = this.beaverService.findBeaverByIntId(beaverId);
        if(current != null){
        model.put("myBeaverId", current.getId());
        }
        if(result.hasErrors()){
            model.addAttribute("valoracion", valoracion);
            return "valoracion/createValoracion"; //TODO FRONT: Añadir vista de creación aquí
        } else if(current  == null|| current == reciever){
            return "accesoNoAutorizado";
        } else {
            valoracionService.crearValoracion(valoracion, reciever, current);
            return "redirect:/beavers/beaverInfo/"+reciever.getId(); //TODO: FRONT: Añadir vista de lista de valoraciones o el Perfil del usuario valorado
        }
    }
}
