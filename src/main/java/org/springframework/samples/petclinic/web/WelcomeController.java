
package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.service.BeaverService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

	//Se ha añadido para pasar el id del beaver conectado a la página de inicio

	private final BeaverService beaverService;


	@Autowired
	public WelcomeController(final BeaverService beavartsService) {
		this.beaverService = beavartsService;
	}

	@GetMapping({
		"/", "/welcome"
	})

	public String welcome(final Map<String, Object> model) {

		Beaver beaver = this.beaverService.getCurrentBeaver();  //Obtenemos el beaver conectado

		if (beaver != null) {						//Si no se esta logueado no se hace nada
			model.put("beaverId", beaver.getId());  //Si se esta logueado se pasa el id a la vista
		}

		return "welcome";
	}
}
