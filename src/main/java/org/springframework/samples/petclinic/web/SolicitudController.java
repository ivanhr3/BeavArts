
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Encargo;
import org.springframework.samples.petclinic.model.Estados;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.service.BeaverService;
import org.springframework.samples.petclinic.service.EncargoService;
import org.springframework.samples.petclinic.service.SolicitudService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudController {

	@Autowired
	private SolicitudService	solicitudService;

	@Autowired
	private UserService			userService;

	@Autowired
	private BeaverService		beaverService;

	@Autowired
	private EmailSender			emailSender;

	@Autowired
	private EncargoService		encargoService;

	private static final String	VISTA_DE_ERROR		= "ErrorPermisos";
	private static final String	SOLICITUD_DETAILS	= "testview";


	@GetMapping("{engId}/create")
	public String crearSolicitudInit(@PathVariable("engId") final int encargoId, final ModelMap model) {
		Encargo encargo = this.encargoService.findEncargoById(encargoId);
		Beaver beaver = this.beaverService.getCurrentBeaver();

		if (encargo.getBeaver() == beaver) { //No se puede solicitar un encargo a si mismo
			return "accesoNoAutorizado"; //FRONT: Acceso no autorizado, un usuario NO puede solicitarse un encargo a si mismo. 
		} else if (!encargo.isDisponibilidad()) {
			return "accesoNoAutorizado";
		} else if (beaver == null) {
			return "accesoNoAutorizado";
		} else {
			final Solicitud sol = new Solicitud();
			model.addAttribute("encargo", encargo);
			model.addAttribute("solicitud", sol);
			return "solicitudes/creationForm"; //TODO: FRONT: Formulario con los campos: Fotos (Varias URL, averiguad como pasarlas para que se almacenen) y Descripción
		}

	}

	@PostMapping("{engId}/create")
	public String crearSolicitud(@PathVariable("engId") final int encargoId, final Solicitud solicitud, final BindingResult result, final ModelMap model) {
		Encargo encargo = this.encargoService.findEncargoById(encargoId);

		Beaver beaver = this.beaverService.getCurrentBeaver();

		if (solicitud.getDescripcion().isBlank() || !solicitudService.isCollectionAllURL(solicitud)) {
			model.addAttribute("solicitud", solicitud);
			return "solicitudes/creationForm";
		} else {

			if (encargo.getBeaver() == beaver) { //No se puede solicitar un encargo a si mismo
				return "accesoNoAutorizado"; //TODO: Cuando puse estos comentarios para FRONT, era para que controlaseis esto usando campos read-only y escondiendo botones
				//TODO: NO HACIENDO VISTAS PARA CADA ERROR.

			} else if (this.solicitudService.existSolicitudByBeaver(beaver, encargo)) { //Excepcion: Un usuario que tiene abierta una solicitud PENDIENTE o ACEPTADA para dicho encargo NO puede hacer otra solicitud
				model.addAttribute("solicitud", solicitud);
				result.rejectValue("descripcion", "Ya existe una solicitud PENDIENTE para este Encargo");
				return "solicitudes/CreationForm"; //FRONT: Ya existe una solicitud para este encargo por parte de este usuario 
			} else if (beaver == null) {
				return "accesoNoAutorizado"; //FRONT: No se puede solicitar un encargo si el usuario no está registrado
			}

			else {
				this.solicitudService.crearSolicitud(solicitud, encargo, beaver);
				return "solicitudes/solicitudSuccess"; //FRONT: Este es el caso de éxito en el que se crea la solicitud asociada al encargo
			}
		}

		//Notas: Las distintas excepciones no deberían ser capaces de darse, debéis hacer que el front controle los casos que se exponen.
		//Si necesitais algún objeto de modelo para controlar estas cosas decidmelo. 
		//Como puse aquí, estas excepciones no deberían permitirse desde Front, no que creaseis vistas para cada error.
	}

	@GetMapping("/list")
	public String listarSolicitudes(final ModelMap modelMap) {
		Beaver beaver = this.beaverService.getCurrentBeaver();
		Collection<Encargo> encargos = beaver.getEncargos();
		Collection<Solicitud> solicitudesEnviadas = beaver.getSolicitud();

		List<Solicitud> solicitudesRecibidas = new ArrayList<>();
		Boolean hayEncargos = false;
		Boolean haySolicitudes = false;

		if (encargos.size() == 0) {
			hayEncargos = false;
		} else {
			hayEncargos = true;
			for (Encargo e : encargos) {
				Collection<Solicitud> solicitudes = e.getSolicitud();
				if (solicitudes != null) {
					for (Solicitud s : solicitudes) {
						solicitudesRecibidas.add(s);
					}
				}
			}
		}

		if (solicitudesEnviadas.size() == 0 || solicitudesEnviadas == null) {
			haySolicitudes = false;
		} else {
			haySolicitudes = true;
		}

		String vista = "solicitudes/listadoSolicitudes";
		Iterable<Solicitud> solicitudes = this.solicitudService.findAll();
		modelMap.addAttribute("hayEncargos", hayEncargos); //TODO: Front: Boolean para controlar si hay solicitudes recibidas que mostrar
		modelMap.addAttribute("haySolicitudes", haySolicitudes); //TODO: Front: Boolean para controlar si hay solicitudes enviadas que mostrar
		modelMap.addAttribute("listaSolicitudesRecibidas", solicitudesRecibidas); //TODO: Front: Ahora hay dos listados en esta vista: Lista de Solicitudes Recibidas y Lista de Solicitudes Enviadas
		modelMap.addAttribute("listaSolicitudesEnviadas", solicitudesEnviadas);
		return vista;
	}

	@GetMapping("/solicitudInfo/{solicitudId}")
	public ModelAndView mostrarSolicitud(@PathVariable("solicitudId") final int solicitudId) {
		ModelAndView vista = new ModelAndView("solicitudes/solicitudesDetails");
		Solicitud solicitud = new Solicitud();
		Beaver beaver = this.beaverService.getCurrentBeaver();

		Optional<Solicitud> s = this.solicitudService.findSolicitudById(solicitudId);
		if (s.isPresent()) {
			solicitud = s.get();
		}
		vista.addObject("encargo", solicitud.getEncargo()); //TODO: FRONT: En los detalles de una solicitud deben aparecer algunos detalles del encargo para saber a cual se refiere.
		vista.addObject("solicitud", solicitud);
		vista.getModelMap().addAttribute("isEncargoCreator", beaver == solicitud.getEncargo().getBeaver()); //TODO: Front: Esto es para mostrar o no los botones de aceptar o rechazar

		return vista;
		//FRONT: Los datos de contacto del beaver creador del encargo solo deben aparecer cuando la solicitud
		//a ese encargo esté ACEPTADA
	}

	//Acepta Solicitudes cuando se pulsa en el botón en los detalles de una solicitud
	@PostMapping(value = "/accept/{solId}")
	public String acceptSolicitud(final Map<String, Object> model, @PathVariable("solId") final int solId) {
		Solicitud sol = this.solicitudService.findById(solId);
		Beaver beaver = this.beaverService.getCurrentBeaver();
		int beaverId = beaver.getId();
		Encargo encargo = this.encargoService.findEncargoById(sol.getEncargo().getId());

		if (beaverId != encargo.getBeaver().getId()) {
			return "solicitudes/errorAceptar"; //TODO: Front: Poned las redirecciones
		} else {
			//solicitudService.aceptarSolicitud(sol, beaver);
			sol.setEstado(Estados.ACEPTADO);
			this.solicitudService.saveSolicitud(sol);
			//Email de Notification
			String subject = "Tu Solicitud para el Encargo" + encargo.getTitulo() + " ha sido aceptada.";
			//emailSender.sendEmail(beaver.getEmail(), subject); Email de momento quitado
			return "solicitudes/listadoSolicitudes"; //TODO: Front: Poned las redirecciones
		}

	}

	//Rechaza Solicitudes cuando se pulsa en el botón en los detalles de una solicitud
	@PostMapping(value = "/decline/{solId}")
	public String declineSolicitud(final Map<String, Object> model, @PathVariable("solId") final int solId) {
		Solicitud sol = this.solicitudService.findById(solId);
		Beaver beaver = this.beaverService.getCurrentBeaver();
		int beaverId = beaver.getId();
		Encargo encargo = this.encargoService.findEncargoById(sol.getEncargo().getId());

		if (beaverId != encargo.getBeaver().getId()) {
			return "solicitudes/errorRechazar"; //TODO: Front: Poned las redirecciones
		} else {
			//solicitudService.rechazarSolicitud(sol, beaver);
			sol.setEstado(Estados.RECHAZADO);
			this.solicitudService.saveSolicitud(sol);
			//Email de Notificacion
			String subject = "Tu Solicitud para el Encargo" + encargo.getTitulo() + " ha sido rechazada";
			//emailSender.sendEmail(beaver.getEmail(), subject); Email de momento quitado
			return "solicitudes/listadoSolicitudes"; //TODO: Front: Poned las redirecciones
		}
	}

}
