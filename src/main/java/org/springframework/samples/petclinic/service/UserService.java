/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.service;


import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.ConfirmationToken;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.UserRepository;
import org.springframework.samples.petclinic.web.EmailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class UserService {

	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder PasswordEncoder;

	@Autowired
	private ConfirmationTokenService confirmationTokenService;

	@Autowired
	private EmailSenderService emailSenderService;

	@Autowired
	private AuthoritiesService authoritiesService;

	@Autowired
	private EntityManagerFactory entityManager;

	@Autowired
	private BeaverService beaverService;

	@Autowired
	private FacturaService facturaService;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	//Método para registrar usuarios
	@Transactional
	public void registrarUser(User user, Beaver beaver) throws DataAccessException {
		user.setEnabled(false);
		user.setPassword(PasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		authoritiesService.saveAuthorities(beaver.getUser().getUsername(), "user");
		
		final ConfirmationToken confirmationToken = new ConfirmationToken(beaver.getUser());
		confirmationTokenService.saveConfirmationToken(confirmationToken);
		sendConfirmationEmail(beaver.getEmail(), confirmationToken.getConfirmationToken());
	}

	//MUY IMPORTANTE: SÓLO SE VA A USAR EN TESTS, ES IGUAL QUE EL REGISTRO PERO SIN CONFIRMACION DE EMAIL
	@Transactional
	public void saveUser(User user, Beaver beaver) throws DataAccessException {
		user.setEnabled(true);
		user.setPassword(PasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		authoritiesService.saveAuthorities(beaver.getUser().getUsername(), "user");
		
	}

	@Transactional
	public void confirmUser(ConfirmationToken confirmationToken){
		final User user = confirmationToken.getUser();
		user.setEnabled(true);
		userRepository.save(user);
		confirmationTokenService.deleteConfirmationToken(confirmationToken.getId());

	}

	public void sendConfirmationEmail(String userMail, String token){
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(userMail);
		mailMessage.setSubject("Email de Confirmación para Beavarts");
		mailMessage.setFrom("beavartsispp@gmail.com");
		mailMessage.setText("Gracias por registrarse en Beavarts, por favor acceda la siguiente URL para activar su cuenta." + "https://beavarts-2.herokuapp.com/confirmar?token=" + token);
		emailSenderService.sendEmail(mailMessage);
	}

	@Transactional
	public void save(User user) throws DataAccessException{
		userRepository.save(user);
	}

	public Optional<User> findUser(String username) {
		return userRepository.findById(username);
	}

	@Transactional
    public User findUserByUsername(String username){
        return this.userRepository.findByUsername(username);
    }

	@Transactional
	public void deleteAllUser(User user, Beaver beaver){

		this.facturaService.unbindFacturas(beaver);

		EntityManager em = this.entityManager.createEntityManager();
		em.find(User.class, user.getUsername());
		em.getTransaction().begin();
		em.remove(em.contains(user) ? user : em.merge(user));
		em.getTransaction().commit();
	}

	@Transactional
	public String getUserEntitiesJson(User user) throws JsonProcessingException{
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		Beaver beaver = this.beaverService.findBeaverByUsername(user.getUsername());
		String json2 = ow.writeValueAsString(beaver);
		return json2;
	}
}
