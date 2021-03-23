package org.springframework.samples.petclinic.service;

import java.io.File;
import java.util.List;
import java.util.Optional;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.dao.DataAccessException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Encargo;
import org.springframework.samples.petclinic.model.Estado;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.repository.SolicitudRepository;
import org.springframework.samples.petclinic.web.EmailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SolicitudService {



    private SolicitudRepository solicitudRepository;
    
    @Autowired
    public SolicitudService(SolicitudRepository solicitudRepository){
        this.solicitudRepository = solicitudRepository;
    }

    @Transactional
    public void saveSolicitud(Solicitud solicitud) throws DataAccessException{
        solicitudRepository.save(solicitud);
    }

    @Transactional
    public void crearSolicitud(Solicitud solicitud, Encargo encargo, Beaver beaver) throws DataAccessException{
      solicitud.setEstado(Estado.PENDIENTE);
      solicitud.setEncargo(encargo);
      solicitud.setBeaver(beaver);
      solicitudRepository.save(solicitud);
    }

    @Transactional
    public void aceptarSolicitud(Solicitud solicitud, Beaver beaver) throws DataAccessException{
        solicitud.setEstado(Estado.ACEPTADA);
        solicitudRepository.save(solicitud);
    }

    @Transactional
    public void rechazarSolicitud(Solicitud solicitud, Beaver beaver) throws DataAccessException{
        solicitud.setEstado(Estado.RECHAZADA);
        solicitudRepository.save(solicitud);
    }

    @Transactional
    public Solicitud findById(Integer id){
        return solicitudRepository.findById(id).get();
    }

    @Transactional
    public Boolean existsSol(Integer id){
        return solicitudRepository.existsById(id);
    }

    @Transactional
    public int solicitudCount() {
      return (int) this.solicitudRepository.count();
    }
  
    @Transactional
    public Iterable<Solicitud> findAll() {
      return this.solicitudRepository.findAll();
    }

    @Transactional
    public Optional<Solicitud> findSolicitudById(final int id) {
      return this.solicitudRepository.findById(id);
    }

    public List<Solicitud> findSolicitudByEncargoId(final int id) {
      return this.solicitudRepository.findSolicitudByEncargoId(id);
    }

    @Transactional
    public void deleteSolicitud(final Solicitud s) {
      this.solicitudRepository.delete(s);
    }

    @Transactional
    public void deleteSolicitudById(final int id) {
      this.solicitudRepository.deleteById(id);
    }

    @Transactional
    public Boolean existSolicitudByBeaver(Beaver beaver, Encargo encargo){
      Solicitud sol = this.solicitudRepository.findSolicitudByBeaver(beaver.getId(), encargo.getId());
      Boolean res;
      if(sol == null){
        res = false;
      } else {
        res = true;
      }
      return res;
    }
}
