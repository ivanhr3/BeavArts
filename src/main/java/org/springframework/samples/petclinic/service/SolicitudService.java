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
import org.springframework.samples.petclinic.model.Estados;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.repository.SolicitudRepository;
import org.springframework.samples.petclinic.web.EmailSender;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SolicitudService {



    private SolicitudRepository solicitudRepository;
    
    @Autowired
    public SolicitudService(SolicitudRepository solicitudRepository){
        this.solicitudRepository = solicitudRepository;
    }

    //Sólo para propositos de Debugging y actualización.
    @Transactional
    public void saveSolicitud(Solicitud solicitud) throws DataAccessException{
        solicitudRepository.save(solicitud);
    }

    @Transactional
    public void crearSolicitud(Solicitud solicitud, Encargo encargo, Beaver beaver) throws DataAccessException{      
      solicitud.setEstado(Estados.PENDIENTE);
      solicitud.setPrecio(50.0);
      solicitud.setDescripcion("descripcion");
      solicitud.setEncargo(encargo);
      solicitud.setBeaver(beaver);
      solicitudRepository.save(solicitud);
    }

    @Transactional
    public void aceptarSolicitud(Solicitud solicitud, Beaver beaver) throws DataAccessException{
        solicitud.setEstado(Estados.ACEPTADO);
        solicitudRepository.save(solicitud);
    }

    @Transactional
    public void rechazarSolicitud(Solicitud solicitud, Beaver beaver) throws DataAccessException{
        solicitud.setEstado(Estados.RECHAZADO);
        solicitudRepository.save(solicitud);
    }

    @Transactional
    public void finalizarSolicitud(Solicitud solicitud, Beaver beaver) throws DataAccessException{
        solicitud.setEstado(Estados.FINALIZADO);
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


    //Comprueba si un Beaver ha realizado una solicitud para un encargo y si dicha solicitud sigue PENDIENTE o ACEPTADA
    @Transactional
    public Boolean existSolicitudByBeaver(Beaver beaver, Encargo encargo){
      Solicitud sol = this.solicitudRepository.findSolicitudByBeaver(beaver.getId(), encargo.getId());
      Boolean res;
      if(sol == null){
        res = false; //Si no hay solicitud previa no existe
      } else {
        if(sol.getEstado() == Estados.RECHAZADO || sol.getEstado() == Estados.FINALIZADO){ //Si la solicitud esta RECHAZADA o FINALIZADA falso
          res = false;
        } else {
        res = true; //Existe solicitud PENDIENTE
        }
      }
      return res;
    }
}