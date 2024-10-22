package com.gestion.empresa.backend.gestion_empresa.services;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JornadaServicioService {
    List<Object[]> obtenerTodasJornadas(Long idServicio);

}
