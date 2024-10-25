package com.gestion.empresa.backend.gestion_empresa.repositories;

import com.gestion.empresa.backend.gestion_empresa.models.DetalleCita;
import com.gestion.empresa.backend.gestion_empresa.models.DuracionServicioPrestado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleCitaRepository extends JpaRepository<DetalleCita, Long> {
}
