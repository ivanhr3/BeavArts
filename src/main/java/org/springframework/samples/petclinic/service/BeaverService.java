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
    private BeaverRepository beaverRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthoritiesService authoritiesService;

    @Autowired
    public BeaverService(BeaverRepository beaverRepository){
        this.beaverRepository = beaverRepository;
    }

    @Transactional
    public void saveBeaver(Beaver beaver) throws DataAccessException{
        beaverRepository.save(beaver);
        userService.saveUser(beaver.getUser());
        authoritiesService.saveAuthorities(beaver.getUser().getUsername(), "admin");
    }

    public Beaver getCurrentBeaver() throws DataAccessException {
		final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;

		if(principal instanceof UserDetails){
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
}
