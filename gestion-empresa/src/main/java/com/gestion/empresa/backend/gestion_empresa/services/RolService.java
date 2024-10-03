package com.gestion.empresa.backend.gestion_empresa.services;

import org.springframework.stereotype.Service;

import com.gestion.empresa.backend.gestion_empresa.models.Rol;

import java.util.List;

@Service
public interface RolService {

    Rol buscarPorNombre(String nombre);
    Rol crearRol(Rol rol);
    List<Rol> findAll();
}
