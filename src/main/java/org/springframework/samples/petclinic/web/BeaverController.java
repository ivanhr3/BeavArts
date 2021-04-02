
package org.springframework.samples.petclinic.web;

import java.util.HashSet;

import javax.validation.Valid;

import org.apache.commons.validator.UrlValidator;
import org.springframework.beans.BeanUtils;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/beavers")
public class BeaverController {

	@Autowired
	private BeaverService		beaverService;

	@Autowired
	private UserService			userService;

	@Autowired
	private PortfolioService	portfolioService;


	@RequestMapping("/beaverInfo/{beaverId}")
	public ModelAndView mostrarPerfilUsuario(@PathVariable("beaverId") final int beaverId) {

		final Beaver beaver = this.beaverService.findBeaverByIntId(beaverId);

		//Si el atributo portfolio del beaver es nulo, crearemos uno vacío
		if (beaver.getPortfolio() == null) {
			Portfolio port = new Portfolio();
			port.setSobreMi("");
			port.setPhotos(new HashSet<>());
			beaver.setPortfolio(port);
		}

		Portfolio portfolio = beaver.getPortfolio();

		ModelAndView vista = new ModelAndView("users/perfilBeaver");
		vista.addObject("beaver", beaver);
		vista.addObject("portfolio", portfolio);

		return vista;
	}

	@GetMapping("/beaverInfo/{beaverId}/portfolio/edit")
	public String initActualizarPerfil(@PathVariable("beaverId") final int beaverId, final ModelMap model) {
		Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication1.getName();
		User user = this.userService.findUserByUsername(currentPrincipalName);

		//Si el usuario no esta logueado tb salta el no autorizado
	    if (user == null) {
			return "accesoNoAutorizado";
		}

		Beaver beaver = this.beaverService.findBeaverByIntId(beaverId);

		Portfolio portfolio = beaver.getPortfolio();

		//Necesario tambien en el get
        if (beaver.getPortfolio() == null) {
			Portfolio port = new Portfolio();
			port.setSobreMi("");
			port.setPhotos(new HashSet<>());
			beaver.setPortfolio(port);
			portfolio = port;
		}


		String vista;

		//compruebo si el usuario logeado es el mismo que quiere editar su perfil
		if (user.getUsername().equals(beaver.getUser().getUsername())) {

			model.addAttribute("portfolio", portfolio);
			vista = "users/editarPortfolio";
			return vista;

		} else {

			vista = "accesoNoAutorizado"; //si el usuario logeado no es el mismo que el usuario del perfil a editar
		}

		return vista;
	}

	@PostMapping("/beaverInfo/{beaverId}/portfolio/edit")
	public String processActualizarPerfil(@PathVariable("beaverId") final int beaverId, @Valid final Portfolio portfolio, final BindingResult result, final ModelMap model) {
		Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication1.getName();
		User user = this.userService.findUserByUsername(currentPrincipalName);

		Beaver beaver = this.beaverService.findBeaverByIntId(beaverId);
		String vista;

		//compruebo si el usuario logeado es el mismo que el usuario del perfil a editar
		if (user.getUsername().equals(beaver.getUser().getUsername())) {

            UrlValidator validar = new UrlValidator();

            Boolean compruebaUrl = portfolio.getPhotos().stream().allMatch(url -> validar.isValid(url));

			if (result.hasErrors() || !compruebaUrl) {
                model.put("errorUrl", "*Las fotos añadidas al portfolio deben ser Urls");
				model.put("portfolio", portfolio);
				vista = "users/editarPortfolio"; //si hay algún error de campos se redirige a la misma vista

			} else {

				//Si el atributo portfolio del beaver es nulo, crearemos uno vacío
				if (beaver.getPortfolio() == null) {
					Portfolio port = new Portfolio();
					port.setSobreMi("");
					port.setPhotos(new HashSet<>());
					beaver.setPortfolio(port);
				}

				final Portfolio portfolio1 = beaver.getPortfolio();

				BeanUtils.copyProperties(portfolio, portfolio1, "id", "beaver");
				beaver.setPortfolio(portfolio1);

				//Necesario para que se actualice el portfolio
			    this.portfolioService.savePortfolio(portfolio1);

				model.put("portfolio", portfolio1);
				return "redirect:/beavers/beaverInfo/" + beaver.getId(); //si no hay ningún error de campos se redirige al perfil ya actualizado
			}

		} else {
			vista = "accesoNoAutorizado"; //si el usuario logeado no es el mismo que el usuario del perfil a editar
		}

		return vista;

	}

	@GetMapping("/list")
    public String listBeavers(final ModelMap modelMap) {
        String vista = "users/listBeavers";
        Iterable<Beaver> beavers = beaverService.findAllBeavers();
        modelMap.addAttribute("beavers", beavers);
        return vista;

    }

}
