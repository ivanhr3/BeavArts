package org.springframework.samples.petclinic.service;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.hibernate.validator.internal.constraintvalidators.hv.URLValidator;
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

    private static final String URL_REGEX =
    "^((((https?|ftps?|gopher|telnet|nntp)://)|(mailto:|news:))" +
            "(%{2}|[-()_.!~*';/?:@&=+$, A-Za-z0-9])+)" + "([).!';/?:, ][[:blank:]])?$";
    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);
    
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
      Collection<Solicitud> sols = this.solicitudRepository.findSolicitudByBeaver(beaver.getId(), encargo.getId());
      Boolean res = null;
      if(sols.isEmpty()){
        res = false; //Si no hay solicitud previa no existe
      } else {
        for(Solicitud sol: sols){
        if(sol.getEstado() == Estados.RECHAZADO || sol.getEstado() == Estados.FINALIZADO){ //Si la solicitud esta RECHAZADA o FINALIZADA falso
          res = false;
        } else {
        res = true;
        break; //Existe solicitud PENDIENTE
        }
      }
    }
      return res;
    }

    public Boolean isCollectionAllURL(Solicitud solicitud){
      Boolean res = false;
      if(solicitud.getFotos().isEmpty() || solicitud.getFotos() == null){ //No hay fotos adjuntas
        res = true;
      }
      for(String s: solicitud.getFotos()){
       if(urlValidator(s)){
         res = true;
       }
      }
      return res;
    }


    public static boolean urlValidator(String url)
    {
        if (url == null) {
            return false;
        }
        Matcher matcher = URL_PATTERN.matcher(url);
        return matcher.matches();
    }

  }