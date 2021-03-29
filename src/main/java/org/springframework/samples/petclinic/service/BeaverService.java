
package org.springframework.samples.petclinic.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.repository.BeaverRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class BeaverService {

	private BeaverRepository	beaverRepository;
	@Autowired
	private UserService			userService;
	@Autowired
	private AuthoritiesService	authoritiesService;


	@Autowired
	public BeaverService(final BeaverRepository beaverRepository) {
		this.beaverRepository = beaverRepository;
	}

	@Transactional
	public void saveBeaver(final Beaver beaver) throws DataAccessException {
		this.beaverRepository.save(beaver);
		this.userService.saveUser(beaver.getUser());
		this.authoritiesService.saveAuthorities(beaver.getUser().getUsername(), "admin");
	}

	@Transactional
	public Beaver findBeaverByUsername(final String username) {
		return this.beaverRepository.findBeaverByUser(this.userService.findUserByUsername(username));
	}

	public Beaver getCurrentBeaver() throws DataAccessException {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;

		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}

		Beaver beaver = this.findBeaverByUsername(username);
		return beaver;
	}

	@Transactional
	public Optional<Beaver> findBeaverById(final String id) {
		return this.beaverRepository.findById(id);
	}

	@Transactional
	public Beaver findBeaverByIntId(final int id) {
		return this.beaverRepository.findBeaverById(id);
	}
}
