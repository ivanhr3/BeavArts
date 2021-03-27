package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Perfil;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.BeaverService;
import org.springframework.samples.petclinic.service.PerfilService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.BeanUtils;

import javax.validation.Valid;

@Controller
@RequestMapping("/beavers")
public class BeaverController {



    @Autowired
    private BeaverService beaverService;

    @Autowired
    private UserService userService;

    @Autowired
    private PerfilService perfilService;

    @RequestMapping("/beaverInfo/{beaverId}")
    public ModelAndView mostrarPerfilUsuario(@PathVariable("beaverId") final int beaverId) {

        final Beaver beaver = this.beaverService.findBeaverByIntId(beaverId);
        Perfil perfil = beaver.getPerfil();

        ModelAndView vista = new ModelAndView("users/perfilBeaver");
        vista.addObject("beaver", beaver);
        vista.addObject("perfil", perfil);

        return vista;
    }

    @GetMapping("/beaverInfo/{beaverId}/perfil/edit")
    public String initActualizarPerfil(@PathVariable("beaverId") final int beaverId, ModelMap model) {
        Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication1.getName();
        User user = this.userService.findUserByUsername(currentPrincipalName);

        Beaver beaver = this.beaverService.findBeaverByIntId(beaverId);

        Perfil perfil = beaver.getPerfil();
        String vista;

        //compruebo si el usuario logeado es el mismo que quiere editar su perfil
        if(user.getUsername().equals(beaver.getUser().getUsername())) {

            model.addAttribute("perfil", perfil);
            vista = "users/editarPerfil";
            return vista;

        } else {

            vista = "accesoNoAutorizado"; //si el usuario logeado no es el mismo que el usuario del perfil a editar
        }

        return vista;
    }

    @PostMapping("/beaverInfo/{beaverId}/perfil/edit")
    public String processActualizarPerfil(@PathVariable("beaverId") final int beaverId, @Valid Perfil perfil, BindingResult result, ModelMap model) {
        Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication1.getName();
        User user = this.userService.findUserByUsername(currentPrincipalName);

        Beaver beaver = this.beaverService.findBeaverByIntId(beaverId);
        String vista;

        //compruebo si el usuario logeado es el mismo que el usuario del perfil a editar
        if(user.getUsername().equals(beaver.getUser().getUsername())) {

            if (result.hasErrors()) {
                model.put("perfil", perfil);
                vista = "users/editarPerfil"; //si hay algún error de campos se redirige a la misma vista

            } else {

                final Perfil perfil1 = beaver.getPerfil();

                BeanUtils.copyProperties(perfil, perfil1, "id", "beaver");
                beaver.setPerfil(perfil1);
                model.put("perfil", perfil1);
                vista = "users/perfilBeaver"; //si no hay ningún error de campos se redirige al perfil ya actualizado
            }

        } else {
            vista = "accesoNoAutorizado"; //si el usuario logeado no es el mismo que el usuario del perfil a editar
        }

        return vista;

    }

}
