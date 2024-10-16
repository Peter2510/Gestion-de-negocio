package com.gestion.empresa.backend.gestion_empresa.repositories;

import com.gestion.empresa.backend.gestion_empresa.models.PermisoRol;
import com.gestion.empresa.backend.gestion_empresa.models.Servicios;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiciosRepository extends JpaRepository<Servicios, Long> {
}
