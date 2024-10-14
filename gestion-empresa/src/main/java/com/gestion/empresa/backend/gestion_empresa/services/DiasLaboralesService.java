package com.gestion.empresa.backend.gestion_empresa.services;


import com.gestion.empresa.backend.gestion_empresa.models.DiasLaborales;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DiasLaboralesService {

    List<DiasLaborales> obtenerTodosDias();
    DiasLaborales ingresarDias(DiasLaborales dias);
}
