package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Portfolio;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.BeaverService;
import org.springframework.samples.petclinic.service.PortfolioService;
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
import java.util.HashSet;

@Controller
@RequestMapping("/beavers")
public class BeaverController {

    @Autowired
    private BeaverService beaverService;

    @Autowired
    private UserService userService;

    @Autowired
    private PortfolioService portfolioService;

    @RequestMapping("/beaverInfo/{beaverId}")
    public ModelAndView mostrarPerfilUsuario(@PathVariable("beaverId") final int beaverId) {

        final Beaver beaver = this.beaverService.findBeaverByIntId(beaverId);
        Portfolio portfolio = beaver.getPortfolio();

        ModelAndView vista = new ModelAndView("users/perfilBeaver");
        vista.addObject("beaver", beaver);
        vista.addObject("portfolio", portfolio);

        return vista;
    }

    @GetMapping("/beaverInfo/{beaverId}/portfolio/edit")
    public String initActualizarPerfil(@PathVariable("beaverId") final int beaverId, ModelMap model) {
        Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication1.getName();
        User user = this.userService.findUserByUsername(currentPrincipalName);

        Beaver beaver = this.beaverService.findBeaverByIntId(beaverId);

        Portfolio portfolio = beaver.getPortfolio();
        String vista;

        //compruebo si el usuario logeado es el mismo que quiere editar su perfil
        if(user.getUsername().equals(beaver.getUser().getUsername())) {

            model.addAttribute("portfolio", portfolio);
            vista = "users/editarPortfolio";
            return vista;

        } else {

            vista = "accesoNoAutorizado"; //si el usuario logeado no es el mismo que el usuario del perfil a editar
        }

        return vista;
    }

    @PostMapping("/beaverInfo/{beaverId}/portfolio/edit")
    public String processActualizarPerfil(@PathVariable("beaverId") final int beaverId, @Valid Portfolio portfolio, BindingResult result, ModelMap model) {
        Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication1.getName();
        User user = this.userService.findUserByUsername(currentPrincipalName);

        Beaver beaver = this.beaverService.findBeaverByIntId(beaverId);
        String vista;

        //compruebo si el usuario logeado es el mismo que el usuario del perfil a editar
        if(user.getUsername().equals(beaver.getUser().getUsername())) {

            if (result.hasErrors()) {
                model.put("portfolio", portfolio);
                vista = "users/editarPortfolio"; //si hay algún error de campos se redirige a la misma vista

            } else {

                //Si el atributo portfolio del beaver es nulo, crearemos uno vacío
                if(beaver.getPortfolio()==null){
                    Portfolio port = new Portfolio();
                    port.setSobreMi("");
                    port.setPhotos(new HashSet<>());
                    beaver.setPortfolio(port);
                }


                final Portfolio portfolio1 = beaver.getPortfolio();

                BeanUtils.copyProperties(portfolio, portfolio1, "id", "beaver");
                beaver.setPortfolio(portfolio1);
                model.put("portfolio", portfolio1);
                vista = "users/perfilBeaver"; //si no hay ningún error de campos se redirige al perfil ya actualizado
            }

        } else {
            vista = "accesoNoAutorizado"; //si el usuario logeado no es el mismo que el usuario del perfil a editar
        }

        return vista;

    }

}
