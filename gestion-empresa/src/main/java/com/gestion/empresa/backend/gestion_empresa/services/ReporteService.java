package com.gestion.empresa.backend.gestion_empresa.services;


import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

/**
 * Author: gordillox
 * Created on: 29/10/24
 */
@Service
public interface ReporteService {
    ResponseBackend usuariosPorRol();
}
