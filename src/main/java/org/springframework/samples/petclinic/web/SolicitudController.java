package org.springframework.samples.petclinic.web;

import java.util.Map;

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

@Controller
@RequestMapping("/solicitud")
public class SolicitudController {

    @Autowired
    private SolicitudService solicitudService;

    @Autowired
    private BeaverService beaverService;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private EncargoService encargoService;

    private static final String VISTA_DE_ERROR = "ErrorPermisos";
    private static final String SOLICITUD_DETAILS = "testview";

    
    //Acepta Solicitudes cuando se pulsa en el botón en los detalles de una solicitud
    @GetMapping(value="/accept/{solId}")
    public String acceptSolicitud(Map<String, Object> model, @PathVariable("solId") final int solId){
        Solicitud sol = this.solicitudService.findById(solId);
        Beaver beaver = this.beaverService.getCurrentBeaver(); 
        int beaverId = beaver.getId();
        Encargo encargo = encargoService.findEncargoById(sol.getEncargo().getId());
        if(beaverId != encargo.getBeaver().getId()){ //Hay que comparar con el Beaver del encargo NO el de la solicitud
            return VISTA_DE_ERROR; //Front: Poned las redirecciones
        } else {
            solicitudService.aceptarSolicitud(sol, beaver);
            //Email de Notification
            String subject = "Tu Solicitud para el Encargo" + encargo.getTitulo() + " ha sido aceptada.";
            emailSender.sendEmail(beaver.getEmail(), subject);
            return SOLICITUD_DETAILS; //Front: Poned las redirecciones
        }

    }

    //Rechaza Solicitudes cuando se pulsa en el botón en los detalles de una solicitud
    @GetMapping(value="/decline/{solId}")
    public String declineSolicitud(Map<String, Object> model, @PathVariable("solId") final int solId){
        Solicitud sol = this.solicitudService.findById(solId);
        Beaver beaver = this.beaverService.getCurrentBeaver();
        int beaverId = beaver.getId();
        Encargo encargo = encargoService.findEncargoById(sol.getEncargo().getId());
        if(beaverId != encargo.getBeaver().getId()){
            return VISTA_DE_ERROR; //Front: Poned las redirecciones
        } else {
            solicitudService.rechazarSolicitud(sol, beaver);
            //Email de Notificacion
            String subject = "Tu Solicitud para el Encargo" + encargo.getTitulo() + " ha sido rechazada";
            emailSender.sendEmail(beaver.getEmail(), subject);
            return SOLICITUD_DETAILS; //Front: Poned las redirecciones
        }

    }

    
}
