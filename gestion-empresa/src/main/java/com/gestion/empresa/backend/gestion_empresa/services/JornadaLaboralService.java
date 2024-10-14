package com.gestion.empresa.backend.gestion_empresa.services;


import com.gestion.empresa.backend.gestion_empresa.models.DiasLaborales;
import com.gestion.empresa.backend.gestion_empresa.models.JornadaLaboral;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JornadaLaboralService {

    List<JornadaLaboral> obtenerTodasJornadas();
    JornadaLaboral ingresarJornada(JornadaLaboral jornadaLaboral);
}
