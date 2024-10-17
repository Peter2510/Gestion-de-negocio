package com.gestion.empresa.backend.gestion_empresa.services;


import com.gestion.empresa.backend.gestion_empresa.models.JornadaLaboral;
import com.gestion.empresa.backend.gestion_empresa.models.JornadaPorDia;
import org.springframework.stereotype.Service;

@Service
public interface JornadaPorDiaService {

    JornadaPorDia ingresarJornadaDia(JornadaPorDia jornadaPorDia);
}
