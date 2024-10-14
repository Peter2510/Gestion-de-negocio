package com.gestion.empresa.backend.gestion_empresa.services;

import com.gestion.empresa.backend.gestion_empresa.models.CategoriaServicio;

import java.util.List;

public interface CategoriaServicioService {

    List<CategoriaServicio> obtenerTodo();
    CategoriaServicio ingresarCategoria(CategoriaServicio categoria);
}
