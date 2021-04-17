
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.*;
import org.springframework.samples.petclinic.service.*;
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

	@Autowired
	private FacturaService		facturaService;
	@Autowired
	private AnuncioService anuncioService;

	private static final String	VISTA_DE_ERROR		= "ErrorPermisos";
	private static final String	SOLICITUD_DETAILS	= "testview";


	@GetMapping("{engId}/create")
	public String crearSolicitudInit(@PathVariable("engId") final int encargoId, final ModelMap model) {
		Encargo encargo = this.encargoService.findEncargoById(encargoId);
		Beaver beaver = this.beaverService.getCurrentBeaver();

		if (beaver != null) {
			model.put("myBeaverId", beaver.getId());
		}
		//De esta forma si al crear la solicitud falla no se desaparecen los datos del encargo, haciendo
		//que haya que recargar la pagina para verlos
		//model.addAttribute("encargo", encargo);

		if (encargo.getBeaver() == beaver) { //No se puede solicitar un encargo a si mismo
			return "accesoNoAutorizado"; //FRONT: Acceso no autorizado, un usuario NO puede solicitarse un encargo a si mismo.
		} else if (!encargo.isDisponibilidad()) {
			return "accesoNoAutorizado";
		} else if (beaver == null) {
			return "accesoNoAutorizado";
		} else {
			final Solicitud sol = new Solicitud();
			sol.setBeaver(beaver);
			sol.setEncargo(encargo);
			sol.setEstado(Estados.PENDIENTE);
			sol.setPrecio(50.0);
			model.put("esDeEncargo", true);
			model.addAttribute("encargo", encargo);
			model.addAttribute("solicitud", sol);
			return "solicitudes/creationForm"; //TODO: FRONT: Formulario con los campos: Fotos (Varias URL, averiguad como pasarlas para que se almacenen) y Descripción
		}

	}

	@PostMapping("{engId}/create")
	public String crearSolicitud(@PathVariable("engId") final int encargoId, final Solicitud solicitud, final BindingResult result, final ModelMap model) {
		Encargo encargo = this.encargoService.findEncargoById(encargoId);

		Beaver beaver = this.beaverService.getCurrentBeaver();
		model.put("esDeEncargo", true);
		model.addAttribute("encargo", encargo);
		if (beaver != null) {
			model.put("myBeaverId", beaver.getId());
		}

		if (solicitud.getDescripcion().isEmpty() || !this.solicitudService.isCollectionAllURL(solicitud)) {
			model.addAttribute("solicitud", solicitud);
			model.put("vacia", true);
			model.put("descripcion", "La descripción no puede estar vacía");
			if (!this.solicitudService.isCollectionAllURL(solicitud)) { //Mensaje SÓLO cuando la url esta mal escrita
				model.put("url", true);
				model.put("errorUrl", "Las fotos añadidas a la solicitud deben ser Urls");
			}

			return "solicitudes/creationForm";
		} else {

			if (encargo.getBeaver() == beaver) { //No se puede solicitar un encargo a si mismo
				return "accesoNoAutorizado"; //TODO: Cuando puse estos comentarios para FRONT, era para que controlaseis esto usando campos read-only y escondiendo botones
				//TODO: NO HACIENDO VISTAS PARA CADA ERROR.

			} else if (this.solicitudService.existSolicitudByBeaver(beaver, encargo)) {//Excepcion: Un usuario que tiene abierta una solicitud PENDIENTE o ACEPTADA para dicho encargo NO puede hacer otra solicitud
				model.addAttribute("solicitud", solicitud);
				model.put("pendiente", true);
				model.put("error", "Tu solicitud se encuentra pendiente de aceptación");
				//result.rejectValue("descripcion", "Ya existe una solicitud PENDIENTE para este Encargo");
				//El result.rejectValue me genera un error500 que no hemos sido capaces de controlar. Comentandolo y mandando el siguiente return se controla que lo de la solicitud pendiente
				//return "solicitudes/solicitudPendiente"

				return "solicitudes/creationForm"; //FRONT: Ya existe una solicitud para este encargo por parte de este usuario
			} else if (beaver == null) {
				return "accesoNoAutorizado"; //FRONT: No se puede solicitar un encargo si el usuario no está registrado
			}

			else {
				Factura factura = new Factura();
				factura.setEmailBeaver(encargo.getBeaver().getEmail());
				factura.setEmailPayer(beaver.getEmail());
				
				this.solicitudService.crearSolicitud(solicitud, encargo, beaver);
				factura.setSolicitud(solicitud);
				this.facturaService.crearFactura(factura);
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

		modelMap.put("myBeaverId", beaver.getId());

		Collection<Encargo> encargos = new ArrayList<>();
		Collection<Anuncio> anuncios = new ArrayList<>();

		if (!(beaver.getEncargos() == null)) {
			encargos = beaver.getEncargos();
		}

		if(beaver.getAnuncios() != null) {
		    anuncios = beaver.getAnuncios();
        }

		Collection<Solicitud> solicitudesEnviadas = new ArrayList<>();
		if (!(beaver.getSolicitud() == null)) {
			solicitudesEnviadas = beaver.getSolicitud();
		}

		List<Solicitud> solicitudesRecibidas = new ArrayList<>();
		List<Solicitud> solicitudesRecibidasAnuncios = new ArrayList<>();
		Boolean hayEncargos = false;
        Boolean hayAnuncios = false;
		Boolean haySolicitudes = false;

		if (encargos.isEmpty()) {
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

		if (anuncios.isEmpty()) {
		    hayAnuncios = false;
        } else {
		    hayAnuncios = true;
		    for(Anuncio a: anuncios) {
		        Collection<Solicitud> solicitudes2 = a.getSolicitud();
		        if(solicitudes2 != null) {
		            for(Solicitud s2: solicitudes2) {
		                solicitudesRecibidasAnuncios.add(s2);
                    }
                }
            }
        }

		if (solicitudesEnviadas.isEmpty()) {
			haySolicitudes = false;
		} else {
			haySolicitudes = true;
		}

		String vista = "solicitudes/listadoSolicitudes";
		Iterable<Solicitud> solicitudes = this.solicitudService.findAll();
		modelMap.addAttribute("hayEncargos", hayEncargos); //TODO: Front: Boolean para controlar si hay solicitudes recibidas que mostrar
		modelMap.addAttribute("hayAnuncios", hayAnuncios);
		modelMap.addAttribute("haySolicitudes", haySolicitudes); //TODO: Front: Boolean para controlar si hay solicitudes enviadas que mostrar
		modelMap.addAttribute("listaSolicitudesRecibidas", solicitudesRecibidas); //TODO: Front: Ahora hay dos listados en esta vista: Lista de Solicitudes Recibidas y Lista de Solicitudes Enviadas
		modelMap.addAttribute("listaSolicitudesRecibidasAnuncios", solicitudesRecibidasAnuncios);
        modelMap.addAttribute("listaSolicitudesEnviadas", solicitudesEnviadas);
		return vista;
	}

	@GetMapping("/solicitudInfo/{solicitudId}")
	public ModelAndView mostrarSolicitud(@PathVariable("solicitudId") final int solicitudId) {
		ModelAndView vista = new ModelAndView("solicitudes/solicitudesDetails");
		Solicitud solicitud = new Solicitud();
		Beaver beaver = this.beaverService.getCurrentBeaver();
		vista.getModel().put("myBeaverId", beaver.getId());

		Optional<Solicitud> s = this.solicitudService.findSolicitudById(solicitudId);
		if (s.isPresent()) {
			solicitud = s.get();
		}

		if(solicitud.getEncargo() != null) {
		    vista.getModel().put("esDeEncargo", true);
		    vista.addObject("encargo", solicitud.getEncargo()); //TODO: FRONT: En los detalles de una solicitud deben aparecer algunos detalles del encargo para saber a cual se refiere.
            vista.getModelMap().addAttribute("isEncargoCreator", beaver == solicitud.getEncargo().getBeaver()); //TODO: Front: Esto es para mostrar o no los botones de aceptar o rechazar
        }

		if(solicitud.getAnuncio() != null) {
		    vista.getModel().put("esDeEncargo", false);
		    vista.addObject("anuncio", solicitud.getAnuncio()); //TODO: FRONT: En los detalles de una solicitud deben aparecer algunos detalles del anuncio para saber a cual se refiere.
            vista.getModelMap().addAttribute("isAnuncioCreator", beaver == solicitud.getAnuncio().getBeaver());
        }

		vista.addObject("solicitud", solicitud);
        //Para que el jsp muestre los datos del contacto, ha sido necesario añadir la línea de abajo
		vista.getModelMap().addAttribute("solicitudAceptada", solicitud.getEstado() == Estados.ACEPTADO);
		vista.getModelMap().addAttribute("solicitudPendiente", solicitud.getEstado() == Estados.PENDIENTE);
		return vista;
		//FRONT: Los datos de contacto del beaver creador del encargo solo deben aparecer cuando la solicitud
		//a ese encargo esté ACEPTADA
	}

	//Acepta Solicitudes cuando se pulsa en el botón en los detalles de una solicitud
	@GetMapping(value = "/accept/{solId}")
	public String acceptSolicitud(final Map<String, Object> model, @PathVariable("solId") final int solId) {
		Solicitud sol = this.solicitudService.findById(solId);
		Beaver beaver = this.beaverService.getCurrentBeaver();
		int beaverId = beaver.getId();
        Encargo encargo = new Encargo();
        Anuncio anuncio = new Anuncio();
        String res = null;

		model.put("myBeaverId", beaverId);

		//Si la solicitud pertenece a encargo
        if(sol.getEncargo() != null) {
            encargo = this.encargoService.findEncargoById(sol.getEncargo().getId());
            if(beaverId != encargo.getBeaver().getId()) {
                res = "solicitudes/errorAceptar";
            } else {
                sol.setEstado(Estados.ACEPTADO);
                this.solicitudService.saveSolicitud(sol);
                //Email de Notification
                String subject = "Tu Solicitud para el Encargo" + encargo.getTitulo() + " ha sido aceptada.";
                //emailSender.sendEmail(beaver.getEmail(), subject); Email de momento quitado
                res = "solicitudes/aceptarSuccess"; //TODO: Front: Poned las redirecciones
            }

            //Si la solicitud pertenece a anuncio
        } else if (sol.getAnuncio() != null) {
            anuncio = this.anuncioService.findAnuncioById(sol.getAnuncio().getId());
            if (beaverId != anuncio.getBeaver().getId()) {
                res = "solicitudes/errorAceptar";
            } else {
                sol.setEstado(Estados.ACEPTADO);
                this.solicitudService.saveSolicitud(sol);
                //Email de Notification
                String subject = "Tu Solicitud para el Encargo" + encargo.getTitulo() + " ha sido aceptada.";
                //emailSender.sendEmail(beaver.getEmail(), subject); Email de momento quitado
                res = "solicitudes/aceptarSuccess"; //TODO: Front: Poned las redirecciones
            }
        }
        return res;
	}

	//Rechaza Solicitudes cuando se pulsa en el botón en los detalles de una solicitud
	@GetMapping(value = "/decline/{solId}")
	public String declineSolicitud(final Map<String, Object> model, @PathVariable("solId") final int solId) {
		Solicitud sol = this.solicitudService.findById(solId);
		Beaver beaver = this.beaverService.getCurrentBeaver();
		int beaverId = beaver.getId();
        Encargo encargo = new Encargo();
        Anuncio anuncio = new Anuncio();
        String res = null;

        model.put("myBeaverId", beaverId);

        //Si la solicitud pertenece a encargo
        if(sol.getEncargo() != null) {
            encargo = this.encargoService.findEncargoById(sol.getEncargo().getId());
            if(beaverId != encargo.getBeaver().getId()) {
                res = "solicitudes/errorRechazar";
            } else {
                sol.setEstado(Estados.RECHAZADO);
                this.solicitudService.saveSolicitud(sol);
                //Email de Notification
                String subject = "Tu Solicitud para el Encargo" + encargo.getTitulo() + " ha sido rechazada.";
                //emailSender.sendEmail(beaver.getEmail(), subject); Email de momento quitado
                res = "solicitudes/rechazarSuccess"; //TODO: Front: Poned las redirecciones
            }

            //Si la solicitud pertenece a anuncio
        } else if (sol.getAnuncio() != null) {
            anuncio = this.anuncioService.findAnuncioById(sol.getAnuncio().getId());
            if (beaverId != anuncio.getBeaver().getId()) {
                res = "solicitudes/errorRechazar";
            } else {
                sol.setEstado(Estados.RECHAZADO);
                this.solicitudService.saveSolicitud(sol);
                //Email de Notification
                String subject = "Tu Solicitud para el Encargo" + encargo.getTitulo() + " ha sido rechazada.";
                //emailSender.sendEmail(beaver.getEmail(), subject); Email de momento quitado
                res = "solicitudes/rechazarSuccess"; //TODO: Front: Poned las redirecciones
            }
        }
        return res;
	}

	//Acepta Solicitudes cuando se pulsa en el botón en los detalles de una solicitud
	@GetMapping(value = "/finish/{solId}")
	public String finishSolicitud(final Map<String, Object> model, @PathVariable("solId") final int solId) {
		Solicitud sol = this.solicitudService.findById(solId);
		Beaver beaver = this.beaverService.getCurrentBeaver();
		int beaverId = beaver.getId();
        Encargo encargo = new Encargo();
        Anuncio anuncio = new Anuncio();
        String res = null;

        model.put("myBeaverId", beaverId);

        //Si la solicitud pertenece a encargo
        if(sol.getEncargo() != null) {
            encargo = this.encargoService.findEncargoById(sol.getEncargo().getId());
            if(beaverId != encargo.getBeaver().getId()) {
                res = "solicitudes/errorAceptar";
            } else if(sol.getEstado() == Estados.ACEPTADO){
                sol.setEstado(Estados.FINALIZADO);
                this.solicitudService.saveSolicitud(sol);
                res = "solicitudes/aceptarSuccess"; //TODO: Front: Poned las redirecciones
            } else {
                res = "accesoNoAutorizado";
            }

            //Si la solicitud pertenece a anuncio
        } else if (sol.getAnuncio() != null) {
            anuncio = this.anuncioService.findAnuncioById(sol.getAnuncio().getId());
            if (beaverId != anuncio.getBeaver().getId()) {
                res = "solicitudes/errorAceptar";
            } else if(sol.getEstado() == Estados.ACEPTADO){
                sol.setEstado(Estados.FINALIZADO);
                this.solicitudService.saveSolicitud(sol);
                res = "solicitudes/aceptarSuccess"; //TODO: Front: Poned las redirecciones
            } else {
                res = "accesoNoAutorizado";
            }
        }
        return res;
	}



	@GetMapping("/{anuncioId}/new")
	public String initCrearSolicitudAnuncios(@PathVariable("anuncioId") int anuncioId, final ModelMap model) {
        Anuncio anuncio = this.anuncioService.findAnuncioById(anuncioId);
        Beaver beaver = this.beaverService.getCurrentBeaver();

        if (anuncio.getBeaver() == beaver) { //No se puede solicitar un encargo a si mismo
            return "accesoNoAutorizado"; //FRONT: Acceso no autorizado, un usuario NO puede solicitarse un encargo a si mismo.
        } else if (beaver == null) {
            return "accesoNoAutorizado";
        } else {
            Solicitud solicitud = new Solicitud();
            solicitud.setBeaver(beaver);
            solicitud.setAnuncio(anuncio);
            solicitud.setEstado(Estados.PENDIENTE);
            model.put("esDeEncargo", false);
            model.addAttribute("anuncio", anuncio);
            model.addAttribute("solicitud", solicitud);
            return "solicitudes/creationForm";
        }
    }
    @PostMapping("/{anuncioId}/new")
    public String processCrearSolicitudAnuncios(@PathVariable("anuncioId") int anuncioId, final Solicitud solicitud, final BindingResult result, final ModelMap model) {
	    Anuncio anuncio = this.anuncioService.findAnuncioById(anuncioId);
	    Beaver beaver = this.beaverService.getCurrentBeaver();
		model.put("esDeEncargo", false);
		model.put("anuncio", anuncio);

	    if(solicitud.getDescripcion().isEmpty() || !this.solicitudService.isCollectionAllURL(solicitud)) {
            model.addAttribute("solicitud", solicitud);
            model.put("vacia", true);
            model.put("descripcion", "La descripción no puede estar vacía");

            if (!this.solicitudService.isCollectionAllURL(solicitud)) { //Mensaje SÓLO cuando la url esta mal escrita
                model.put("url", true);
                model.put("errorUrl", "Las fotos añadidas a la solicitud deben ser Urls");
            }

            return "solicitudes/creationForm";

        } else {
	        if (anuncio.getBeaver() == beaver) { //No se puede solicitar un encargo a si mismo
	            return "accesoNoAutorizado";
            } else if (this.solicitudService.existSolicitudAnuncioByBeaver(beaver, anuncio)) { //Excepcion: Un usuario que tiene abierta una solicitud PENDIENTE o ACEPTADA para dicho encargo NO puede hacer otra solicitud
                model.addAttribute("solicitud", solicitud);
                model.put("pendiente", true);
                model.put("error", "Tu solicitud se encuentra pendiente de aceptación");

                return "solicitudes/creationForm"; //FRONT: Ya existe una solicitud para este encargo por parte de este usuario
            } else if (beaver == null) {
	            return "accesoNoAutorizado"; //FRONT: No se puede solicitar un encargo si el usuario no está registrado
            } else {
	            this.solicitudService.crearSolicitudAnuncio(solicitud, anuncio, beaver);
	            return "solicitudes/solicitudSuccess"; //FRONT: Este es el caso de éxito en el que se crea la solicitud asociada al encargo
            }

        }
    }

}
