package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.services.CorreoMensajeService;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * Author: alexxus
 * Created on: 8/10/24
 */

@Service
public class CorreoMensajeServiceImpl implements CorreoMensajeService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public String envioCorreo() {
        String valor = "s";
        return  valor;
    }

    @Override
    public ResponseBackend generarCorreo(String destinatario, String subject, String cuerpo) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true); // true para habilitar el HTML

            helper.setTo(destinatario);
            helper.setSubject(subject);
            helper.setText(cuerpo, true); // true indica que el cuerpo es HTML
            helper.setFrom(this.from);

            mailSender.send(message);

            return new ResponseBackend(true, HttpStatus.OK, "Correo enviado exitosamente");

        }catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            return new ResponseBackend(false, HttpStatus.INTERNAL_SERVER_ERROR, "Error al enviar correo");
        }
    }
}
