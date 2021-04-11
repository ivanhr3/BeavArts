package org.springframework.samples.petclinic.web;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Anuncio;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Especialidad;
import org.springframework.samples.petclinic.model.Estados;
import org.springframework.samples.petclinic.service.AnuncioService;
import org.springframework.samples.petclinic.service.BeaverService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.*;

@Controller
public class AnuncioController {


    private final AnuncioService anuncioService;
    private final BeaverService beaverService;
    private static final String		VIEWS_ANUNCIO_CREATE_OR_UPDATE_FORM	= "anuncios/createAnunciosForm";

    @Autowired
    public AnuncioController(final AnuncioService anuncioService, final BeaverService beaverService) throws ClassNotFoundException {
        this.anuncioService = anuncioService;
        this.beaverService = beaverService;
    }

    //Añadido para usarlo en el jsp
    @ModelAttribute("types")
    public Collection<Especialidad> populateEspecialidades() {

        Collection<Especialidad> especialidades = new ArrayList<>();

        especialidades.add(Especialidad.ACRILICO);
        especialidades.add(Especialidad.ESCULTURA);
        especialidades.add(Especialidad.FOTOGRAFIA);
        especialidades.add(Especialidad.ILUSTRACION);
        especialidades.add(Especialidad.JOYERIA);
        especialidades.add(Especialidad.RESINA);
        especialidades.add(Especialidad.TEXTIL);
        especialidades.add(Especialidad.OLEO);

        return especialidades;
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
            if (beaver.getAnuncios() == null) {
                final Set<Anuncio> res = new HashSet<>();
                res.add(anuncio);
                anuncio.setBeaver(beaver);
                beaver.setAnuncios(res);

            } else {
                beaver.getAnuncios().add(anuncio);
                anuncio.setBeaver(beaver);
            }
            anuncio.setDestacado(false); // El atributo destacado al crear un anuncio por defecto es FALSE
            this.anuncioService.crearAnuncio(anuncio, beaver);
            return "redirect:/beavers/" + beaver.getId() + "/anuncios/" + anuncio.getId();
        }

    }

    //Aquí comienza la parte de editar Anuncio

    @GetMapping(value = "/beavers/{beaverId}/anuncios/{anuncioId}/edit")
    public String initUpdateForm(@PathVariable("anuncioId") final int anuncio, final ModelMap model) {
        Beaver beaver = this.beaverService.getCurrentBeaver();
        final Anuncio anunc = this.anuncioService.findAnuncioById(anuncio);

        model.put("myBeaverId", beaver.getId()); //Añadido para usar las url del header
        model.put("editando", true); //Para que los botones no cambien

        if (anunc.getBeaver() != beaver) {
            return "accesoNoAutorizado"; //Acceso no Autorizado
        } else {
            // Al darle al boton de editar anuncio, saldra un error si dicho anuncio tiene solicitudes aceptadas
            if(anunc.getSolicitud() != null && anunc.getSolicitud().stream().anyMatch(s -> s.getEstado()== Estados.ACEPTADO)) {
                model.put("errorSolicitudesAceptadas", "No se puede editar un anuncio con solicitudes aceptadas.");
                return "anuncios/anunciosDetails";

            } else {

                model.addAttribute("anuncio", anunc);

                return AnuncioController.VIEWS_ANUNCIO_CREATE_OR_UPDATE_FORM;
            }
        }
    }

    @PostMapping(value = "/beavers/{beaverId}/anuncios/{anuncioId}/edit")
    public String processUpdateForm(@Valid final Anuncio anuncio, final BindingResult result, @PathVariable("anuncioId") final int anuncioId, final ModelMap model) {

        Beaver beaver = this.beaverService.getCurrentBeaver();
        model.put("myBeaverId", beaver.getId()); //Añadido para usar las url del header
        model.put("editando", true); //Para que los botones no cambien

        final Anuncio anunc = this.anuncioService.findAnuncioById(anuncioId);
        //El atributo destacado no se edita aquí
        if (result.hasErrors()) {
            model.addAttribute("anuncio", anuncio);
            return AnuncioController.VIEWS_ANUNCIO_CREATE_OR_UPDATE_FORM; //Si hay algún error de campo se redirige a la misma vista
        } else {
            BeanUtils.copyProperties(anuncio, anunc, "id", "beaver");
            this.anuncioService.saveAnuncio(anunc);
            return "redirect:/beavers/" +beaver.getId() + "/anuncios/" + anuncioId;
        }
    }

    // METODO PARA BORRAR ANUNCIOS

    @RequestMapping(value = "/beavers/{beaverId}/anuncios/{anuncioId}/delete")
    public String deleteAnuncio(@PathVariable("beaverId") final int beaverId, @PathVariable("anuncioId") final int anuncioId, final ModelMap model) {

        Anuncio anuncio = this.anuncioService.findAnuncioById(anuncioId);

        if (this.beaverService.getCurrentBeaver() != this.beaverService.findBeaverByIntId(beaverId)) {
            return "accesoNoAutorizado"; // Acceso no autorizado
        } else {
            // SOLO SE PUEDE BORRAR UN ANUNCIO SI NO TIENE SOLICITUDES ACEPTADAS
            if(anuncio.getSolicitud() != null && anuncio.getSolicitud().stream().anyMatch(s -> s.getEstado()== Estados.ACEPTADO)) {
                model.put("errorSolicitudesAceptadas", "No se puede eliminar un anuncio con solicitudes aceptadas.");
                return "anuncios/anunciosDetails";

            } else {
                this.anuncioService.deleteAnuncio(anuncioId);
                return "redirect:/beavers/" + beaverId + "/encargos/list";
            }

        }
    }

    // LISTAR TODOS LOS ANUNCIOS EN EL MENÚ
    @GetMapping("/anuncios/list")
    public String listAnuncios(final ModelMap modelMap) {

        Beaver me = this.beaverService.getCurrentBeaver();  //Obtenemos el beaver conectado

        if (me != null) {//añadido el if para los tests
            modelMap.put("myBeaverId", me.getId()); //añadimos el id a la vista
        }

        String vista = "anuncios/listAnuncios";
        List<Anuncio> anuncios = new ArrayList<>();
        List<Anuncio> destacados = this.anuncioService.findAnunciosDestacados();
        List<Anuncio> noDestacados = this.anuncioService.findAnunciosNoDestacados();
        anuncios.addAll(destacados);
        anuncios.addAll(noDestacados);

        modelMap.addAttribute("anuncios", anuncios);
        return vista;
    }

    @GetMapping("/anuncios/listEspecialidad/{especialidades}")
    public String listAnunciosPorEspecialidad(final ModelMap modelMap, @PathVariable List<Especialidad> especialidades) {

        List<Anuncio> anunciosPorEspecialidad = new ArrayList<>();

        for(Especialidad e: especialidades){
            List<Anuncio> aux = this.anuncioService.findAnunciosByEspecialidad(e);
            anunciosPorEspecialidad.addAll(aux); // se meten en una lista los anuncios ordenados por las especialidades seleccionadas
        }
        List<Anuncio> destacados = new ArrayList<>();
        List<Anuncio> noDestacados = new ArrayList<>();
        List<Anuncio> anuncios = new ArrayList<>();

        for(Anuncio a: anunciosPorEspecialidad){
            if(a.getDestacado() == true){
                destacados.add(a); // se ordenan los anuncios dependiendo si son destacados o no
            } else {
                noDestacados.add(a);
            }
        }
        anuncios.addAll(destacados);
        anuncios.addAll(noDestacados);
        String vista = "anuncios/listAnuncios";
        modelMap.addAttribute("anuncios", anuncios);
        return vista;
    }


    // MOSTRAR LOS DETALLES DE UN ANUNCIO

    @GetMapping("/beavers/{beaverId}/anuncios/{anuncioId}")
    public ModelAndView mostrarAnuncio(@PathVariable("anuncioId") final int anuncioId, final ModelMap model) {

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


}
