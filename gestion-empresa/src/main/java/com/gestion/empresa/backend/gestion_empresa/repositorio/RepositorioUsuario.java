package com.gestion.empresa.backend.gestion_empresa.repositorio;

import com.gestion.empresa.backend.gestion_empresa.models.Usuarios;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositorioUsuario extends JpaRepository<Usuarios, Long> {

    //función para encontrar por id
    Optional<Usuarios> findById(Long aLong);

    //para encontrar por correo
    Optional<Usuarios> findByNombreUsuario(String nombreUsuario);


}
