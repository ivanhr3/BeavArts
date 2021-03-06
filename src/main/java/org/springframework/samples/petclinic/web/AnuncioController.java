
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Anuncio;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Encargo;
import org.springframework.samples.petclinic.model.Especialidad;
import org.springframework.samples.petclinic.model.Estados;
import org.springframework.samples.petclinic.model.Factura;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AnuncioService;
import org.springframework.samples.petclinic.service.BeaverService;
import org.springframework.samples.petclinic.service.EncargoService;
import org.springframework.samples.petclinic.service.FacturaService;
import org.springframework.samples.petclinic.service.SolicitudService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AnuncioController {

	private final EncargoService	encargoService;
	private final AnuncioService	anuncioService;
	private final BeaverService		beaverService;
	private final SolicitudService	solicitudService;
	private final FacturaService	facturaService;
	private static final String		VIEWS_ANUNCIO_CREATE_OR_UPDATE_FORM	= "anuncios/createAnunciosForm";


	@Autowired
	public AnuncioController(final EncargoService encargoService, final AnuncioService anuncioService, final BeaverService beaverService, final SolicitudService solicitudService, final FacturaService facturaService) throws ClassNotFoundException {
		this.encargoService = encargoService;
		this.anuncioService = anuncioService;
		this.beaverService = beaverService;
		this.solicitudService = solicitudService;
		this.facturaService = facturaService;
	}

	//A??adido para usarlo en el jsp
	@ModelAttribute("types")
	public Collection<Especialidad> populateEspecialidades() {

		Collection<Especialidad> especialidades = new ArrayList<>();

		especialidades.add(Especialidad.ACRILICO);
		especialidades.add(Especialidad.ESCULTURA);
		especialidades.add(Especialidad.FOTOGRAFIA);
		especialidades.add(Especialidad.ILUSTRACION);
		especialidades.add(Especialidad.JOYERIA);
		especialidades.add(Especialidad.RESINA);
		especialidades.add(Especialidad.TEXTIL);
		especialidades.add(Especialidad.OLEO);

		return especialidades;
	}

	// CREAR ANUNCIO
	@GetMapping(value = "/beavers/{beaverId}/anuncios/new")
	public String initCreationForm(final ModelMap model) {

		Beaver beaver = this.beaverService.getCurrentBeaver();
		model.put("myBeaverId", beaver.getId()); //A??adido para usar las url del header

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

		model.put("myBeaverId", beaver.getId()); //A??adido para usar las url del header

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

	//Aqu?? comienza la parte de editar Anuncio

	@GetMapping(value = "/beavers/{beaverId}/anuncios/{anuncioId}/edit")
	public String initUpdateForm(@PathVariable("anuncioId") final int anuncio, final ModelMap model) {
		Beaver beaver = this.beaverService.getCurrentBeaver();
		final Anuncio anunc = this.anuncioService.findAnuncioById(anuncio);

		model.put("myBeaverId", beaver.getId()); //A??adido para usar las url del header
		model.put("editando", true); //Para que los botones no cambien

		if (anunc.getBeaver() != beaver) {
			return "accesoNoAutorizado"; //Acceso no Autorizado
		} else {
			// Al darle al boton de editar anuncio, saldra un error si dicho anuncio tiene solicitudes aceptadas
			if (anunc.getSolicitud() != null && anunc.getSolicitud().stream().anyMatch(s -> s.getEstado() == Estados.ACEPTADO)) {
				model.addAttribute("anuncio", anunc);
				model.addAttribute("createdByUser", true); 
				model.addAttribute("promocionado", anunc.getDestacado());
				model.put("urlEdit", true);
				model.put("errorEditarSolicitudesAceptadas", "No se puede editar un anuncio con solicitudes aceptadas. Por favor, finalice dichas solicitudes antes de editar.");
				return "anuncios/anunciosDetails";
			} else {
				model.addAttribute("anuncio", anunc);
				return AnuncioController.VIEWS_ANUNCIO_CREATE_OR_UPDATE_FORM;
			}
		}
	}

	@PostMapping(value = "/beavers/{beaverId}/anuncios/{anuncioId}/edit")
	public String processUpdateForm(@Valid final Anuncio anuncio, final BindingResult result, @PathVariable("anuncioId") final int anuncioId, final ModelMap model) {

		Beaver beaver = this.beaverService.getCurrentBeaver();
		model.put("myBeaverId", beaver.getId()); //A??adido para usar las url del header
		model.put("editando", true); //Para que los botones no cambien

		final Anuncio anunc = this.anuncioService.findAnuncioById(anuncioId);
		//El atributo destacado no se edita aqu??
		if (result.hasErrors()) {
			model.addAttribute("anuncio", anuncio);
			return AnuncioController.VIEWS_ANUNCIO_CREATE_OR_UPDATE_FORM; //Si hay alg??n error de campo se redirige a la misma vista
		} else {
			BeanUtils.copyProperties(anuncio, anunc, "id", "beaver", "destacado");
			this.anuncioService.saveAnuncio(anunc);
			return "redirect:/beavers/" + beaver.getId() + "/anuncios/" + anuncioId;
		}
	}

	// METODO PARA BORRAR ANUNCIOS

	@RequestMapping(value = "/beavers/{beaverId}/anuncios/{anuncioId}/delete")
	public String deleteAnuncio(@PathVariable("beaverId") final int beaverId, @PathVariable("anuncioId") final int anuncioId, final ModelMap model) {

		Anuncio anuncio = this.anuncioService.findAnuncioById(anuncioId);
		
		Beaver beav = this.beaverService.getCurrentBeaver();
		model.put("myBeaverId", beav.getId());
		User user = beav.getUser();
		List<Authorities> auth = this.beaverService.findUserAuthorities(user);
		Boolean esAdmin = auth.get(0).getAuthority().equals("admin");

		if (this.beaverService.getCurrentBeaver() != this.beaverService.findBeaverByIntId(beaverId) && !esAdmin) {
			return "accesoNoAutorizado"; // Acceso no autorizado
		} else {
			// SOLO SE PUEDE BORRAR UN ANUNCIO SI NO TIENE SOLICITUDES ACEPTADAS
			if (anuncio.getSolicitud() != null && anuncio.getSolicitud().stream().anyMatch(s -> s.getEstado() == Estados.ACEPTADO) && !esAdmin) {
				
				model.addAttribute("anuncio", anuncio);
				model.addAttribute("createdByUser", true); 
				model.put("urlEliminar", true);
				model.addAttribute("promocionado", anuncio.getDestacado());
				model.put("errorEliminarSolicitudesAceptadas", "No se puede eliminar un anuncio con solicitudes aceptadas. Por favor, finalice dichas solicitudes antes de eliminar.");
				return "anuncios/anunciosDetails";

			} else {
				for (Solicitud s : anuncio.getSolicitud()) {
					Factura factura = this.facturaService.findFacturaBySolicitud(s);
					if(factura != null){
						factura.setSolicitud(null);
						this.facturaService.saveFactura(factura);
					}
					this.solicitudService.deleteSolicitud(s);
				}
				this.anuncioService.deleteAnuncio(anuncioId);
				if (esAdmin) {
					return "redirect:/anuncios/list";
				} else {
					return "redirect:/beavers/" + beav.getId() + "/misPublicaciones";
				}
			}
		}
	}

	@RequestMapping(value = "/beavers/{beaverId}/anuncios/{anuncioId}/promote", method = RequestMethod.POST)
	public String promoteAnuncio(@PathVariable("beaverId") final int beaverId, @PathVariable("anuncioId") final int anuncioId, final ModelMap model) {

		Beaver beaver = this.beaverService.getCurrentBeaver();
		model.put("myBeaverId", beaver.getId()); //A??adido para usar las url del header

		Anuncio anuncio = this.anuncioService.findAnuncioById(anuncioId);

		if (this.beaverService.getCurrentBeaver() != this.beaverService.findBeaverByIntId(beaverId)) {
			return "accesoNoAutorizado"; // FRONT: No debe aparecer el bot??n para los usuarios que no crearon el anuncio. 
										// Esta vista solo debe redirigirse si se intenta un acceso ilegal por url.
		} else {
			anuncio.setDestacado(true);
			this.anuncioService.saveAnuncio(anuncio);
			return "redirect:/anuncios/list";
		}
	}

	// LISTAR TODOS LOS ANUNCIOS EN EL MEN??
	@GetMapping("/anuncios/list")
	public String listAnuncios(final ModelMap modelMap) {

		Beaver me = this.beaverService.getCurrentBeaver();  //Obtenemos el beaver conectado

		if (me != null) {//a??adido el if para los tests
			modelMap.put("myBeaverId", me.getId()); //a??adimos el id a la vista
		}

		String vista = "anuncios/listAnuncios";
		List<Anuncio> anuncios = new ArrayList<>();
		List<Anuncio> destacados = this.anuncioService.findAnunciosDestacados();
		List<Anuncio> noDestacados = this.anuncioService.findAnunciosNoDestacados();
		anuncios.addAll(destacados);
		anuncios.addAll(noDestacados);

		modelMap.addAttribute("anuncios", anuncios);
		return vista;
	}

	// MOSTRAR LOS DETALLES DE UN ANUNCIO

	@GetMapping("/beavers/{beaverId}/anuncios/{anuncioId}")
	public ModelAndView mostrarAnuncio(@PathVariable("anuncioId") final int anuncioId, final ModelMap model) {

		final ModelAndView vista = new ModelAndView("anuncios/anunciosDetails");
		final Anuncio anuncio = this.anuncioService.findAnuncioById(anuncioId);
		model.addAttribute("anuncio", anuncio);

		Beaver beaver = this.beaverService.getCurrentBeaver(); //Necesario para ver el id y usar las url
		Boolean esAdmin = false;

		if(beaver != null){
		model.addAttribute("myBeaverId", beaver.getId());

		User user = beaver.getUser();
		List<Authorities> auth = this.beaverService.findUserAuthorities(user);
		esAdmin = auth.get(0).getAuthority().equals("admin");
		}
		if (esAdmin) {
			model.addAttribute("esAdmin", true); //Este par??metro es la condicion para ver el boton de delete sin ser el creador
		}

		model.addAttribute("promocionado", anuncio.getDestacado());

		if (this.beaverService.getCurrentBeaver() == anuncio.getBeaver()) {
			model.addAttribute("createdByUser", true); //TODO: Front: S??lo quien cre?? el anuncio puede actualizarlo. Mostrad el bot??n s??lo en este caso.
			model.addAttribute("authenticated", true);
		} else if(beaver == null){
			model.addAttribute("authenticated", false);
		} else {
			model.addAttribute("createdByUser", false);
			model.addAttribute("authenticated", true); //TODO: Front: Controla que el usuario que est?? viendo la vista es el mismo que cre?? el anuncio.
			//TODO: Front: El creador del anuncio NO puede crear solicitudes, mostrad el bot??n si este atributo es false.
		}
		return vista;
	}

	//MOSTRAR MIS ANUNCIOS Y MIS ENCARGOS

	@GetMapping("/beavers/{beaverId}/misPublicaciones")
	public String listarMisPublicaciones(@PathVariable("beaverId") final int beaverId, final ModelMap model) {

		if (this.beaverService.getCurrentBeaver() != null) {
			Beaver me = this.beaverService.getCurrentBeaver();
			model.put("myBeaverId", me.getId()); //A??adido para usar las url del header
		}

		Beaver beaver = this.beaverService.findBeaverByIntId(beaverId);
		model.addAttribute("beaverId", beaverId);
		model.addAttribute("beaver", beaver);

		if (this.beaverService.getCurrentBeaver() == null || !beaver.equals(this.beaverService.getCurrentBeaver())) {
			return "accesoNoAutorizado"; //Acceso no autorizado
		} else {
			model.addAttribute("noHayEspecialidades", beaver.getEspecialidades().isEmpty());
			model.put("errorEspecialidades", "Debe seleccionar alguna especialidad para poder crear encargos.");
			if (beaver.getEncargos().isEmpty() && beaver.getAnuncios().isEmpty()) {
				model.addAttribute("hayEncargos", false);
				model.addAttribute("hayAnuncios", false);

			} else if (!beaver.getEncargos().isEmpty() && beaver.getAnuncios().isEmpty()) {
				model.addAttribute("hayAnuncios", false);
				final Iterable<Encargo> encargos = this.encargoService.findEncargoByBeaverId(beaverId);
				model.addAttribute("encargos", encargos);

			} else if (beaver.getEncargos().isEmpty() && !beaver.getAnuncios().isEmpty()) {
				model.addAttribute("hayEncargos", false);
				final Iterable<Anuncio> anuncios = this.anuncioService.findAnuncioByBeaverId(beaverId);
				model.addAttribute("anuncios", anuncios);

			} else {
				final Iterable<Encargo> encargos = this.encargoService.findEncargoByBeaverId(beaverId);
				final Iterable<Anuncio> anuncios = this.anuncioService.findAnuncioByBeaverId(beaverId);
				model.addAttribute("encargos", encargos);
				model.addAttribute("anuncios", anuncios);
			}

			return "publicaciones/misPublicaciones";

		}

	}

}
