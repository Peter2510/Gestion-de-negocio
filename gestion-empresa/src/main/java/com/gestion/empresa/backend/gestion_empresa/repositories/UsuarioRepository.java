package com.gestion.empresa.backend.gestion_empresa.repositories;

import com.gestion.empresa.backend.gestion_empresa.models.Usuarios;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {

    Optional<Usuarios> findById(Long aLong);
    Optional<Usuarios> findByPersonaCui(Long cui);
    Optional<Usuarios> findByNombreUsuario(String nombreUsuario);

}
