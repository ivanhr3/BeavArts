
package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.model.*;
import org.springframework.samples.petclinic.repository.BeaverRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BeaverService {

    private BeaverRepository beaverRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthoritiesService authoritiesService;
    @Autowired
    private PortfolioService portfolioService;
    @Autowired
    private ValoracionService valoracionService;

    @Autowired
    public BeaverService(BeaverRepository beaverRepository){
        this.beaverRepository = beaverRepository;
    }

    @Transactional
    public void registrarBeaver(Beaver beaver) throws DataAccessException{
        beaverRepository.save(beaver);

        Portfolio port = new Portfolio();
        port.setBeaver(beaver);

        beaver.setPortfolio(port);
        portfolioService.savePortfolio(port);

        userService.registrarUser(beaver.getUser(), beaver);
    }
    //MUY IMPORTANTE: Esto es una copia del Registro pero sólo para Tests, es para evitar que se envíen emails.
    @Transactional
    public void saveBeaver(Beaver beaver) throws DataAccessException{
        beaverRepository.save(beaver);

        Portfolio port = new Portfolio();
        port.setBeaver(beaver);

        beaver.setPortfolio(port);
        portfolioService.savePortfolio(port);

        userService.saveUser(beaver.getUser(), beaver);
    }

    @Transactional
    public void guardarUsuario(Beaver beaver) throws DataAccessException{
        beaverRepository.save(beaver);
        userService.save(beaver.getUser());
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
    public Double calculatePuntuacion(final Beaver beaver){
        return this.valoracionService.calcularValoracion(beaver.getId());
    }

    public Iterable<Beaver> findAllBeavers(){
        return this.beaverRepository.findAll();
    }

    @Transactional
    public Beaver findBeaverByEmail(final String email) {
        return this.beaverRepository.findBeaverByEmail(email);
    }
        @Transactional
        public Integer getNumValoraciones(Beaver beaver){
        return this.valoracionService.getNumValoracionesUsuario(beaver.getId());
    }

    @Transactional
	public List<Authorities> findUserAuthorities(final User user) {
		return this.beaverRepository.findUserAuthorities(user);
	}

    @Transactional(readOnly = true)
    public Page<Beaver> findAllBeavers (Pageable pageable){
        Page<Beaver> page = beaverRepository.findAllBeavers(pageable);
        return page;
    }

}
