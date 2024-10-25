package com.gestion.empresa.backend.gestion_empresa.repositories;

import com.gestion.empresa.backend.gestion_empresa.models.DuracionServicioPrestado;
import com.gestion.empresa.backend.gestion_empresa.models.EstadoCita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoCitaRepository extends JpaRepository<EstadoCita, Long> {
}
