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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional
	public void saveUser(User user, Beaver beaver) throws DataAccessException {
		user.setEnabled(false);
		user.setPassword(PasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		authoritiesService.saveAuthorities(beaver.getUser().getUsername(), "user");
		
		final ConfirmationToken confirmationToken = new ConfirmationToken(beaver.getUser());
		confirmationTokenService.saveConfirmationToken(confirmationToken);
		sendConfirmationEmail(beaver.getEmail(), confirmationToken.getConfirmationToken());
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
		mailMessage.setText("Gracias por registrarse en Beavarts, por favor acceda la siguiente URL para activar su cuenta." + "http://localhost:8080/confirmar?token=" + token);
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
}
