package com.gestion.empresa.backend.gestion_empresa.services;


import com.gestion.empresa.backend.gestion_empresa.models.Permiso;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Author: gordillox
 * Created on: 13/10/24
 */

@Service
public interface PermisoService {

    Permiso crearPermiso(Permiso permiso);
    Optional<Permiso> buscarPorId(Long id);
    List<Permiso> obtenerPermisosRegistrados();
    Permiso actualizarPermiso(Permiso permiso);
    Optional<Permiso> buscarPermisoPorNombre(String nombre);
}
