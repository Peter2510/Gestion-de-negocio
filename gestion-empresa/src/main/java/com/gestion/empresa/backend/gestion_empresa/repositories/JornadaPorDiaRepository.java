package com.gestion.empresa.backend.gestion_empresa.repositories;

import com.gestion.empresa.backend.gestion_empresa.models.JornadaPorDia;
import com.gestion.empresa.backend.gestion_empresa.models.PermisoRol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JornadaPorDiaRepository extends JpaRepository<JornadaPorDia, Long> {
}
