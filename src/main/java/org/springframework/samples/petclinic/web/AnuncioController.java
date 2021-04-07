package org.springframework.samples.petclinic.web;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Anuncio;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Estados;
import org.springframework.samples.petclinic.service.AnuncioService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.BeaverService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
//@RequestMapping("/anuncios")
public class AnuncioController {


    private final AnuncioService anuncioService;
    private final BeaverService beaverService;
    private static final String		VIEWS_ANUNCIO_CREATE_OR_UPDATE_FORM	= "anuncios/createAnunciosForm";

    @Autowired
    public AnuncioController(final AnuncioService anuncioService, final BeaverService beaverService, final AuthoritiesService authoritiesService) throws ClassNotFoundException {
        this.anuncioService = anuncioService;
        this.beaverService = beaverService;
    }


    // CREAR ANUNCIO
    @GetMapping(value = "/beavers/{beaverId}/anuncios/new")
    public String initCreationForm(final ModelMap model) {

        Beaver beaver = this.beaverService.getCurrentBeaver();
        model.put("myBeaverId", beaver.getId()); //Añadido para usar las url del header

        if (beaver == null) {

            return "accesoNoAutorizado"; // SOLO UN USUARIO LOGUEADO PUEDE CREAR ANUNCIO
        } else {
            final Anuncio anuncio = new Anuncio();
            model.addAttribute("anuncio", anuncio);
            return AnuncioController.VIEWS_ANUNCIO_CREATE_OR_UPDATE_FORM;
        }
    }



    @PostMapping(value = "/beavers/{beaverId}/anuncios/new")
    public String processCreationForm(@Valid final Anuncio anuncio, final BindingResult result, final ModelMap model) {
        Beaver beaver = this.beaverService.getCurrentBeaver();

        model.put("myBeaverId", beaver.getId()); //Añadido para usar las url del header

        if (result.hasErrors() /* anuncio.getTitulo()==null anuncio.getPrecio() == 0.0 || anuncio.getDescricpcion==null */) {
            model.addAttribute("anuncio", anuncio);
            return AnuncioController.VIEWS_ANUNCIO_CREATE_OR_UPDATE_FORM;

        } else {
            if (beaver.getAnuncios().isEmpty() || beaver.getAnuncios() == null) {
                final Set<Anuncio> res = new HashSet<>();
                res.add(anuncio);
                anuncio.setBeaver(beaver);
                beaver.setAnuncios(res);

            } else {
                beaver.getAnuncios().add(anuncio);
                anuncio.setBeaver(beaver);
            }
            this.anuncioService.crearEncargo(anuncio, beaver);
            return "redirect:/beavers/" + beaver.getId() + "/anuncios/" + anuncio.getId();
        }

    }


    @GetMapping(value = "/beavers/{beaverId}/anuncios/{anuncioId}/edit")
    public String initUpdateForm(@PathVariable("anuncioId") final int anuncio, final ModelMap model) {
        Beaver beaver = this.beaverService.getCurrentBeaver();
        final Anuncio anunc = this.anuncioService.findAnuncioById(anuncio);

        if (anunc.getBeaver() != beaver) {
            return "accesoNoAutorizado"; //Acceso no Autorizado
        } else {

            //PONER RESTRICCION DE SOLICITUDES ACEPTADAS

            model.addAttribute("anuncio", anunc);

            return AnuncioController.VIEWS_ANUNCIO_CREATE_OR_UPDATE_FORM;

        }

    }


    @PostMapping(value = "/beavers/{beaverId}/anuncios/{anuncioId}/edit")
    public String processUpdateForm(@Valid final Anuncio anuncio, final BindingResult result, @PathVariable("anuncioId") final int anuncioId, final ModelMap model) {

        Beaver beaver = this.beaverService.getCurrentBeaver();
        model.put("myBeaverId", beaver.getId()); //Añadido para usar las url del header
        model.put("editando", true); //Para que los botones no cambien

        final Anuncio anunc = this.anuncioService.findAnuncioById(anuncioId);

        if (result.hasErrors()) {
            model.addAttribute("anuncio", anuncio);
            return AnuncioController.VIEWS_ANUNCIO_CREATE_OR_UPDATE_FORM;
        } else {

            // SOLO SE PUEDE EDITAR UN ANUNCIO SI NO TIENE SOLICITUDES ACEPTADAS

            if(anuncio.getSolicitud().stream().anyMatch(s -> s.getEstado()== Estados.ACEPTADO)) {
                model.put("errorSolicitudesAceptadas", "No se puede editar un anuncio con solicitudes aceptadas.");
                return AnuncioController.VIEWS_ANUNCIO_CREATE_OR_UPDATE_FORM;

            } else {

                BeanUtils.copyProperties(anuncio, anunc, "id", "beaver");
                this.anuncioService.saveAnuncio(anunc);
                return "redirect:/beavers/" +beaver.getId() + "/anuncios/" + anuncioId;
            }



        }



    }

    // LISTAR TODOS LOS ANUNCIOS EN EL MENÚ
    @GetMapping("anuncios/list")
    public String listAnuncios(final ModelMap modelMap) {

        Beaver me = this.beaverService.getCurrentBeaver();  //Obtenemos el beaver conectado
        /*
        if (me != null) {//añadido el if para los tests
            modelMap.put("myBeaverId", me.getId()); //añadimos el id a la vista
        }

         */

        String vista = "anuncios/listAnuncios";
        Iterable<Anuncio> anuncios = this.anuncioService.findAllAnuncios();
        modelMap.addAttribute("anuncios", anuncios);
        return vista;

    }


    // MOSTRAR LOS DETALLES DE UN ANUNCIO

    @GetMapping("/beavers/{beaverId}/anuncios/{anuncioId}")
    public ModelAndView mostrarEncargo(@PathVariable("anuncioId") final int anuncioId, final ModelMap model) {

        final ModelAndView vista = new ModelAndView("anuncios/anunciosDetails");
        final Anuncio anuncio = this.anuncioService.findAnuncioById(anuncioId);
        model.addAttribute("anuncio", anuncio);

        Beaver beaver = this.beaverService.getCurrentBeaver(); //Necesario para ver el id y usar las url
        model.addAttribute("myBeaverId", beaver.getId());

        if (this.beaverService.getCurrentBeaver() == anuncio.getBeaver()) {
            model.addAttribute("createdByUser", true); //TODO: Front: Sólo quien creó el anuncio puede actualizarlo. Mostrad el botón sólo en este caso.
        } else {
            model.addAttribute("createdByUser", false); //TODO: Front: Controla que el usuario que está viendo la vista es el mismo que creó el anuncio.
            //TODO: Front: El creador del anuncio NO puede crear solicitudes, mostrad el botón si este atributo es false.
        }
        return vista;
    }

    @RequestMapping(value = "/beavers/{beaverId}/anuncios/{anuncioId}/delete")
    public String deleteAnuncio(@PathVariable("beaverId") final int beaverId, @PathVariable("anuncioId") final int anuncioId, final ModelMap model) {

        Anuncio anuncio = this.anuncioService.findAnuncioById(anuncioId);

        if (this.beaverService.getCurrentBeaver() != this.beaverService.findBeaverByIntId(beaverId)) {
            return "accesoNoAutorizado"; // Acceso no autorizado
        } else {
            // SOLO SE PUEDE BORRAR UN ANUNCIO SI NO TIENE SOLICITUDES ACEPTADAS
            if(anuncio.getSolicitud().stream().anyMatch(s -> s.getEstado()== Estados.ACEPTADO)) {
                model.put("errorSolicitudesAceptadas", "No se puede eliminar un anuncio con solicitudes aceptadas.");
                return "anuncios/anunciosDetails";

            } else {
                this.anuncioService.deleteAnuncio(anuncioId);
                return "redirect:/beavers/" + beaverId + "/encargos/list";
            }

        }
    }
}
