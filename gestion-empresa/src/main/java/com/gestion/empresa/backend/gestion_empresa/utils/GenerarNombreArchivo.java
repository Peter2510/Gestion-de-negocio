package com.gestion.empresa.backend.gestion_empresa.utils;


import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * Author: gordillox
 * Created on: 5/10/24
 */

public class GenerarNombreArchivo {

    public String generarNombreUnico(MultipartFile file) {
        String nombreArchivo = file.getOriginalFilename();

        if (nombreArchivo == null || !nombreArchivo.contains(".")) {
            return null;
        }
        String extensionArchivo = nombreArchivo.substring(nombreArchivo.lastIndexOf('.') + 1);

        String uuidAsString = UUID.randomUUID().toString();
        return uuidAsString + "." + extensionArchivo;
    }


}
