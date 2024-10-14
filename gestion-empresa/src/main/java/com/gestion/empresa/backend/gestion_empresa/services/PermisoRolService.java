package com.gestion.empresa.backend.gestion_empresa.services;


import com.gestion.empresa.backend.gestion_empresa.models.PermisoRol;
import com.gestion.empresa.backend.gestion_empresa.projections.PermisoRolProjection;
import com.gestion.empresa.backend.gestion_empresa.repositories.PermisoRolRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Author: gordillox
 * Created on: 14/10/24
 */

@Service
public interface PermisoRolService {

    PermisoRol crearPermisoRol(PermisoRol permisoRol);
    List<PermisoRol> obtenerRegistros();
    Optional<PermisoRol> buscarPorId(Long id);
    PermisoRol actualizarPermisoRol(PermisoRol permisoRol);
    List<PermisoRolProjection> obtenerPermisosPorRol(Long idRol);
}
