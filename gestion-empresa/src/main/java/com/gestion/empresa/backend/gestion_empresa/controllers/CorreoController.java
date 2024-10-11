package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.models.CorreoConfirmacion;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.CorreoMensajeServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Author: alexxus
 * Created on: 8/10/24
 */

@RestController
@Validated
@RequestMapping("/correo")
public class CorreoController {
    @Autowired
    private CorreoMensajeServicio emailService;


    //ruta para enviar un correo
    @PostMapping("/send-email")
    public String sendEmail(@RequestBody CorreoConfirmacion request) {
        emailService.sendSimpleEmail(request.getCorreo(), "Asunto", "Cuerpo del correo: " + request.getCodigo());
        return "Correo enviado!";
    }

}
