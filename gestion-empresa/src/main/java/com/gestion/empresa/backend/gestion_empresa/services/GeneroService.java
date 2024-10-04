package com.gestion.empresa.backend.gestion_empresa.services;

import com.gestion.empresa.backend.gestion_empresa.models.Genero;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public interface GeneroService {

    //obtener todo
    List<Genero> findAll();
}
