package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Perfil;
import org.springframework.samples.petclinic.repository.PerfilRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PerfilService {

    private PerfilRepository perfilRepository;

    @Autowired
    public PerfilService(PerfilRepository perfilRepository) {
        this.perfilRepository = perfilRepository;
    }

    @Transactional
    public void savePerfil(Perfil perfil) throws DataAccessException {
        this.perfilRepository.save(perfil);
    }

    @Transactional
    public Perfil findPerfilByBeaverId(int id) {
        return this.perfilRepository.findPerfilByBeaverId(id);

    }





}