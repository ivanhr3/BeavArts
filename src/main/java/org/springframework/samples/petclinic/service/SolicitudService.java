package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.repository.SolicitudRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class SolicitudService {

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Transactional
    public int solicitudCount(){
        return (int) this.solicitudRepository.count();
    }
    @Transactional
    public Solicitud saveSolicitud(Solicitud s){
        return this.solicitudRepository.save(s);
    }

    @Transactional
    public Iterable<Solicitud> findAll(){
        return this.solicitudRepository.findAll();
    }

    @Transactional
    public Optional<Solicitud> findSolicitudById(int id){
        return this.solicitudRepository.findById(id);
    }

    public Solicitud findSolicitudByEncargoId(int id){
        return this.solicitudRepository.findSolicitudByEncargoId(id);
    }

    @Transactional
    public void deleteSolicitud(Solicitud s){
        this.solicitudRepository.delete(s);
    }

    @Transactional
    public void deleteSolicitudById(int id){
        this.solicitudRepository.deleteById(id);
    }

}
