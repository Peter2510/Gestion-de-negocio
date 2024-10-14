package com.gestion.empresa.backend.gestion_empresa.repositories;

import com.gestion.empresa.backend.gestion_empresa.models.PermisoRol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Author: gordillox
 * Created on: 14/10/24
 */

public interface PermisoRolRepository extends JpaRepository<PermisoRol, Long> {
}
