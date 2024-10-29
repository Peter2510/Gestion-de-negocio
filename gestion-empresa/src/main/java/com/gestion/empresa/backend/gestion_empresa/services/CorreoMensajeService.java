package com.gestion.empresa.backend.gestion_empresa.services;


import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;

/**
 * Author: gordillox
 * Created on: 29/10/24
 */

public interface CorreoMensajeService {
    ResponseBackend generarCorreo(String destinatario, String subject, String cuerpo);
}
