package com.gestion.empresa.backend.gestion_empresa.repositorio;

import com.gestion.empresa.backend.gestion_empresa.models.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioUsuario extends JpaRepository<Usuarios, Long> {
}
