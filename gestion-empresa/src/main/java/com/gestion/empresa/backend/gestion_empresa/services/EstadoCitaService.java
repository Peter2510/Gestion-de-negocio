package com.gestion.empresa.backend.gestion_empresa.services;

import com.gestion.empresa.backend.gestion_empresa.models.CategoriaServicio;
import com.gestion.empresa.backend.gestion_empresa.models.EstadoCita;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EstadoCitaService {


    List<EstadoCita> obtenerTodo();
    EstadoCita ingresarEstadoCita(EstadoCita estadoCita);
}
