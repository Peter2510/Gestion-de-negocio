package com.gestion.empresa.backend.gestion_empresa.services;

import com.gestion.empresa.backend.gestion_empresa.entities.Rol;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RolService {

    Rol buscarPorNombre(String nombre);
    Rol crearRol(Rol rol);
    List<Rol> findAll();
}


