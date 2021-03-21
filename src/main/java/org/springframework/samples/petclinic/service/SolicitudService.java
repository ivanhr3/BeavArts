package org.springframework.samples.petclinic.service;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.dao.DataAccessException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.samples.petclinic.model.Beaver;
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
    
}
