package com.gestion.empresa.backend.gestion_empresa.services;


import com.gestion.empresa.backend.gestion_empresa.models.Servicios;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DuracionServicioPrestadoService {

    List<Object[]> obtenerTodosServicios(Long idServicio);

}
