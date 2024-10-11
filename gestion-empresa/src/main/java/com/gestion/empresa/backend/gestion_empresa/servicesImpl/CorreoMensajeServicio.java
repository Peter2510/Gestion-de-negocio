package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Author: alexxus
 * Created on: 8/10/24
 */

@Service
public class CorreoMensajeServicio {
    @Autowired
    private JavaMailSender mailSender;


    public String envioCorreo() {
        String valor = "s";
        return  valor;
    }

    public void sendSimpleEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("oleoalexis@gmail.com");  // Tu dirección de correo

        mailSender.send(message);
    }
}
