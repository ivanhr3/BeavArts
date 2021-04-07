package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Anuncio;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Encargo;
import org.springframework.samples.petclinic.repository.AnuncioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnuncioService {

    private final AnuncioRepository anuncioRepository;

    @Autowired
    private BeaverService beaverService;

    @Autowired
    public AnuncioService(final AnuncioRepository anuncioRepository) {
        this.anuncioRepository = anuncioRepository;
    }

    @Transactional
    public Iterable<Anuncio> findAllAnuncios(){
        return this.anuncioRepository.findAll();
    }

    @Transactional
    public Iterable<Anuncio> findAnuncioByBeaverId(final int id) {
        return this.anuncioRepository.findAnuncioByBeaverId(id);
    }

    @Transactional
    public Anuncio findAnuncioById(final int id){
        return this.anuncioRepository.findAnuncioByIntId(id);
    }

    @Transactional
    public Anuncio saveAnuncio(final Anuncio anuncio) {
        return this.anuncioRepository.save(anuncio);
    }

    @Transactional
    public void crearEncargo(Anuncio anuncio, Beaver beaver){

        this.beaverService.guardarUsuario(beaver);
        anuncioRepository.save(anuncio);

    }

    @Transactional
    public void deleteAnuncio(final Integer id) {
         this.anuncioRepository.deleteById(id);
    }

    @Transactional
    public int anunciosCount(){
        return (int) this.anuncioRepository.count();
    }
}
