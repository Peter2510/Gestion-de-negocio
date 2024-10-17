package com.gestion.empresa.backend.gestion_empresa.repositories;

import com.gestion.empresa.backend.gestion_empresa.models.EstadoServicio;
import com.gestion.empresa.backend.gestion_empresa.models.Genero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoServicioRepository extends JpaRepository<EstadoServicio, Long> {
}
