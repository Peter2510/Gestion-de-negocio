package com.gestion.empresa.backend.gestion_empresa.repositories;


import com.gestion.empresa.backend.gestion_empresa.models.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author: gordillox
 * Created on: 12/10/24
 */

public interface PermisoRepository extends JpaRepository<Permiso, Long> {
}
