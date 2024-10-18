package com.gestion.empresa.backend.gestion_empresa.services;

import com.gestion.empresa.backend.gestion_empresa.models.CategoriaServicio;

import java.util.List;
import java.util.Optional;

public interface CategoriaServicioService {

    List<CategoriaServicio> obtenerTodo();
    CategoriaServicio ingresarCategoria(CategoriaServicio categoria);
    Optional<CategoriaServicio> buscarPorTipo(String tipo);
}
