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

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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

    @RequestMapping(value = "/beavers/{beaverId}/anuncios/{anuncioId}/promote")
    public String promoteAnuncio(@PathVariable("beaverId") final int beaverId, @PathVariable("anuncioId") final int anuncioId, final ModelMap model) {

        Beaver beaver = this.beaverService.getCurrentBeaver();
        model.put("myBeaverId", beaver.getId()); //Añadido para usar las url del header

        Anuncio anuncio = this.anuncioService.findAnuncioById(anuncioId);

        if (this.beaverService.getCurrentBeaver() != this.beaverService.findBeaverByIntId(beaverId)) {
            return "accesoNoAutorizado"; // FRONT: No debe aparecer el botón para los usuarios que no crearon el anuncio. 
                                         // Esta vista solo debe redirigirse si se intenta un acceso ilegal por url.
        } else {
            // EN ESTE ELSE SE REDIRIGIRÁ A LA VISTA DEL PAGO Y SE CONTROLARÁ SI EL PROCESO SE HA REALIZADO CORRECTAMENTE O NO. 
            return null;
        }
    }


}
