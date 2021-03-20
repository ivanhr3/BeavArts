
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Encargo;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.SolicitudService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudController {

	@Autowired
	private SolicitudService	solicitudService;

	@Autowired
	private UserService			userService;


	@GetMapping("/list")
	public String listarSolicitudes(final ModelMap modelMap) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		User user = this.userService.findUserByUsername(currentPrincipalName);
		Collection<Encargo> encargos = user.getBeaver().getEncargos();

		List<Solicitud> listaSolicitudes = new ArrayList<>();

		if (encargos.size() == 0) {
			//AÑADIR MENSAJE DE "NO HAY SOLICITUDES DISPONIBLES
		} else {

			for (Encargo e : encargos) {
				Collection<Solicitud> solicitudes = e.getSolicitudes();
				for (Solicitud s : solicitudes) {
					listaSolicitudes.add(s);
				}
			}
		}

		String vista = "solicitudes/listadoSolicitudes";
		Iterable<Solicitud> solicitudes = this.solicitudService.findAll();
		modelMap.addAttribute("listaSolicitudes", listaSolicitudes);
		return vista;
	}

	@GetMapping("/solicitudInfo/{solicitudId}")
	public ModelAndView mostrarSolicitud(@PathVariable("solicitudId") final int solicitudId) {
		ModelAndView vista = new ModelAndView("solicitudes/solicitudesDetails");
		Solicitud solicitud = new Solicitud();
		Optional<Solicitud> s = this.solicitudService.findSolicitudById(solicitudId);
		if (s.isPresent()) {
			solicitud = s.get();
		}

		vista.addObject("solicitud", solicitud);

		return vista;
	}

}
