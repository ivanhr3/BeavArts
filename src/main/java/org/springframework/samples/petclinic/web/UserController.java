/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.ConfirmationToken;
import org.springframework.samples.petclinic.model.Especialidad;
import org.springframework.samples.petclinic.service.BeaverService;
import org.springframework.samples.petclinic.service.ConfirmationTokenService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class UserController {

	private static final String	VIEWS_BEAVER_CREATE_FORM	= "users/createBeaverForm";

	private final BeaverService	beaverService;

	private final ConfirmationTokenService confirmationTokenService;

	private final UserService userService;


	@Autowired
	public UserController(final BeaverService beavartsService, final ConfirmationTokenService confirmationTokenService, final UserService userService) {
		this.beaverService = beavartsService;
		this.confirmationTokenService = confirmationTokenService;
		this.userService = userService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	//Añadido para usarlo en el jsp
	@ModelAttribute("types")
	public Collection<Especialidad> populateStatus() {

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

	@GetMapping(value = "/users/new")
	public String initCreationForm(final Map<String, Object> model) {
		Beaver beaver = new Beaver();
		model.put("beaver", beaver);

		return UserController.VIEWS_BEAVER_CREATE_FORM;
	}

	@PostMapping(value = "/users/new")
	public String processCreationForm(@Valid final Beaver beaver, final BindingResult result) {
		if (result.hasErrors()) {
			return UserController.VIEWS_BEAVER_CREATE_FORM;
		} else {
			//creating owner, user, and authority
			this.beaverService.saveBeaver(beaver);
			return "redirect:/";
		}
	}

	@GetMapping("/confirmar")
	String confirmarEmail(@RequestParam("token") String token){
		Optional<ConfirmationToken> optToken = confirmationTokenService.findConfirmationTokenByToken(token);
		optToken.ifPresent(userService::confirmUser);
		return "redirect:/login";
	}

}
