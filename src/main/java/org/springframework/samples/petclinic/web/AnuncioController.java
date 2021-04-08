package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Anuncio;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Especialidad;
import org.springframework.samples.petclinic.service.AnuncioService;
import org.springframework.samples.petclinic.service.BeaverService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

        especialidades.add(Especialidad.ACRÍLICO);
        especialidades.add(Especialidad.ESCULTURA);
        especialidades.add(Especialidad.FOTOGRAFÍA);
        especialidades.add(Especialidad.ILUSTRACIÓN);
        especialidades.add(Especialidad.JOYERÍA);
        especialidades.add(Especialidad.RESINA);
        especialidades.add(Especialidad.TEXTIL);
        especialidades.add(Especialidad.ÓLEO);

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


}
