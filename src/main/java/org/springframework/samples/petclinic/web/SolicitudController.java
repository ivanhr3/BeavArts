package org.springframework.samples.petclinic.web;

import java.util.Map;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Encargo;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.service.BeaverService;
import org.springframework.samples.petclinic.service.EncargoService;
import org.springframework.samples.petclinic.service.SolicitudService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.samples.petclinic.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudController {

    @Autowired
    private SolicitudService solicitudService;
  
    @Autowired
	  private UserService userService;

    @Autowired
    private BeaverService beaverService;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private EncargoService encargoService;

    private static final String VISTA_DE_ERROR = "ErrorPermisos";
    private static final String SOLICITUD_DETAILS = "testview";

  
    @GetMapping("/list")
	public String listarSolicitudes(final ModelMap modelMap) {
		Beaver beaver = beaverService.getCurrentBeaver();
		Collection<Encargo> encargos = beaver.getEncargos();

		List<Solicitud> listaSolicitudes = new ArrayList<>();

		if (encargos.size() == 0) {
			//AÑADIR MENSAJE DE "NO HAY SOLICITUDES DISPONIBLES
			return "solicitudes/solicitudesNotFound";
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

    
    //Acepta Solicitudes cuando se pulsa en el botón en los detalles de una solicitud
    @GetMapping(value="/accept/{solId}")
    public String acceptSolicitud(Map<String, Object> model, @PathVariable("solId") final int solId){
        Solicitud sol = this.solicitudService.findById(solId);
        Beaver beaver = this.beaverService.getCurrentBeaver(); 
        int beaverId = beaver.getId();
        Optional<Encargo> p = encargoService.findEncargoById(sol.getEncargo().getId());

        if(!p.isPresent()){
            return "exception";
        } else {
        Encargo encargo = p.get();
        if(beaverId != encargo.getBeaver().getId()){ 
            return VISTA_DE_ERROR; //TODO: Front: Poned las redirecciones
        } else {
            solicitudService.aceptarSolicitud(sol, beaver);
            //Email de Notification
            String subject = "Tu Solicitud para el Encargo" + encargo.getTitulo() + " ha sido aceptada.";
            emailSender.sendEmail(beaver.getEmail(), subject);
            return SOLICITUD_DETAILS; //TODO: Front: Poned las redirecciones
        }
        }

    }

    //Rechaza Solicitudes cuando se pulsa en el botón en los detalles de una solicitud
    @GetMapping(value="/decline/{solId}")
    public String declineSolicitud(Map<String, Object> model, @PathVariable("solId") final int solId){
        Solicitud sol = this.solicitudService.findById(solId);
        Beaver beaver = this.beaverService.getCurrentBeaver();
        int beaverId = beaver.getId();
        Optional<Encargo> p = encargoService.findEncargoById(sol.getEncargo().getId());

        if(!p.isPresent()){
            return "exception";
        } else {

        Encargo encargo = p.get();
        
        if(beaverId != encargo.getBeaver().getId()){
            return VISTA_DE_ERROR; //TODO: Front: Poned las redirecciones
        } else {
            solicitudService.rechazarSolicitud(sol, beaver);
            //Email de Notificacion
            String subject = "Tu Solicitud para el Encargo" + encargo.getTitulo() + " ha sido rechazada";
            emailSender.sendEmail(beaver.getEmail(), subject);
            return SOLICITUD_DETAILS; //TODO: Front: Poned las redirecciones

        }
    }

    }
}
