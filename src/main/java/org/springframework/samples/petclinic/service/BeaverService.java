
package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.User;
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
	private ValoracionService	valoracionService;


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
	public void guardarUsuario(final Beaver beaver) throws DataAccessException {
		this.beaverRepository.save(beaver);
		this.userService.save(beaver.getUser());
	}

	public Beaver getCurrentBeaver() throws DataAccessException {
		final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;

		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		final Beaver beaver = this.findBeaverByUsername(username);
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

	@Transactional
	public Beaver findBeaverByUsername(final String username) {
		return this.beaverRepository.findBeaverByUser(this.userService.findUserByUsername(username));
	}

	@Transactional
	public Double calculatePuntuacion(final Beaver beaver) {
		return this.valoracionService.calcularValoracion(beaver.getId());
	}

	public Iterable<Beaver> findAllBeavers() {
		return this.beaverRepository.findAll();
	}

	@Transactional
	public Integer getNumValoraciones(final Beaver beaver) {
		return this.valoracionService.getNumValoracionesUsuario(beaver.getId());
	}

	@Transactional
	public List<Authorities> findUserAuthorities(final User user) {
		return this.beaverRepository.findUserAuthorities(user);
	}

}
