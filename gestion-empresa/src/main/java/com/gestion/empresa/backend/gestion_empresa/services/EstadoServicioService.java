package com.gestion.empresa.backend.gestion_empresa.services;


import com.gestion.empresa.backend.gestion_empresa.models.CategoriaServicio;
import com.gestion.empresa.backend.gestion_empresa.models.EstadoServicio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EstadoServicioService {

    List<EstadoServicio> obtenerTodo();
    EstadoServicio ingresarEstado(EstadoServicio estadoServicio);
}
