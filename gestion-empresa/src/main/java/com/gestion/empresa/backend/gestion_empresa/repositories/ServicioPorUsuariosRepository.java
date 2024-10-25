package com.gestion.empresa.backend.gestion_empresa.repositories;

import com.gestion.empresa.backend.gestion_empresa.models.PermisoRol;
import com.gestion.empresa.backend.gestion_empresa.models.ServicioPorUsuarios;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicioPorUsuariosRepository  extends JpaRepository<ServicioPorUsuarios, Long> {
}
