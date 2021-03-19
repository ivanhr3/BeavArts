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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SolicitudService {

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private SolicitudRepository solicitudRepository;
    @Value("${spring.mail.username")
    private String from;

    public SolicitudService(){

    }

    public SolicitudService(SolicitudRepository solicitudRepository, JavaMailSender emailSender){
        this.solicitudRepository = solicitudRepository;
        this.emailSender = emailSender;
    }

    @Transactional
    public void aceptarSolicitud(Solicitud solicitud, Beaver beaver) throws MessagingException, DataAccessException{
        solicitud.setEstado(Estado.ACEPTADA);
        solicitudRepository.save(solicitud);

        //Email de Notificacion
        String subject = "Tu solicitud ha sido aceptada";
        String text = "Texto de Prueba";
        sendMessageWithAttacthment(beaver.getEmail(), subject, text, "vacio");
    }

    public void sendMessageWithAttacthment(String to, String subject, String text, String pathToAttachment) throws MessagingException{

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        //FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
        //helper.addAttachment("html", file);

        emailSender.send(message);
    }
    
}
