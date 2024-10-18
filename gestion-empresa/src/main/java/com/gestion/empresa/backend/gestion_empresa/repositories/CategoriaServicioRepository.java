package com.gestion.empresa.backend.gestion_empresa.repositories;

import com.gestion.empresa.backend.gestion_empresa.models.CategoriaServicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaServicioRepository extends JpaRepository<CategoriaServicio, Long> {
    Optional<CategoriaServicio> findByTipo(String tipo);

}
