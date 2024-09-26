package com.gestion.empresa.backend.gestion_empresa.repositorio;

import com.gestion.empresa.backend.gestion_empresa.models.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepositorio extends JpaRepository<Persona, Long> {
}
