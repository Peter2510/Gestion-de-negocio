package com.gestion.empresa.backend.gestion_empresa.services;

import org.springframework.stereotype.Service;

import com.gestion.empresa.backend.gestion_empresa.models.Rol;

import java.util.List;
import java.util.Optional;

@Service
public interface RolService {

    Optional<Rol> buscarPorNombre(String nombre);
    Optional<Rol> buscarPorId(Long id);
    Rol crearRol(Rol rol);
    List<Rol> obtenerRolesRegistrados();
}
