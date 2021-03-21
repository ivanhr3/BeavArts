package org.springframework.samples.petclinic.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.repository.BeaverRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Transactional
    public Optional<Beaver> findBeaverById(String id) {
        return beaverRepository.findById(id);
    }

    @Transactional
    public Beaver findBeaverByIntId(int id) {
        return beaverRepository.findBeaverById(id);
    }



}
