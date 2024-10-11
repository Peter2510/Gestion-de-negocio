package com.gestion.empresa.backend.gestion_empresa.services;


import com.gestion.empresa.backend.gestion_empresa.models.Usuarios;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UsuarioServicio {

    Optional<Usuarios> findByPersonaCui(Long cui);
    Optional<Usuarios> findById(Long id);
    Optional<Usuarios> buscarNombre(String nombreUsuario);

    // para que pueda editar
    Optional<Usuarios> editarUsuario(Usuarios usuarios);
}
