package org.springframework.samples.petclinic.web;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;

@Service
public class EmailSender {

    final String from = "beavartsispp@gmail.com";
    final String password = "myupxiwntfuxtjyi";

    public void sendEmail(String to, String subject){

        Address addressTo;
        Properties properties = new Properties();

        properties.put("spring.mail.host", "smtp.gmail.com");
        properties.put("spring.mail.port", "587");
        properties.put("spring.mail.username", from);
        properties.put("spring.mail.password", password);
        properties.put("spring.mail.properties.mail.smtp.auth", "true");
        properties.put("spring.mail.properties.mail.smtp.connectiontimeout", "5000");
        properties.put("spring.mail.properties.mail.smtp.timeout", "5000");
        properties.put("spring.mail.properties.mail.smtp.writetimeout", "5000");
        properties.put("spring.mail.properties.mail.smtp.startttls.enable", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication(){
            return new PasswordAuthentication(from, password);
        }});


        try{
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));

        addressTo = new InternetAddress(to);
        message.setRecipient(Message.RecipientType.BCC, addressTo);
        message.setSubject(subject);
        message.setContent("insertaHTML", "text/html");
        Transport.send(message);
        } catch (MessagingException e){
            throw new RuntimeException(e);
        }
    }
    
}
