package com.gestion.empresa.backend.gestion_empresa.repositories;

import com.gestion.empresa.backend.gestion_empresa.models.PermisoRol;
import com.gestion.empresa.backend.gestion_empresa.projections.PermisoRolProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Author: gordillox
 * Created on: 14/10/24
 */

public interface PermisoRolRepository extends JpaRepository<PermisoRol, Long> {
    @Query("SELECT pr.rol.id AS rolId, pr.permiso.id AS permisoId , pr.permiso.nombre AS permisoNombre FROM PermisoRol pr WHERE pr.rol.id = :rolId")
    List<PermisoRolProjection> findByRolId(@Param("rolId") Long rolId);
}
