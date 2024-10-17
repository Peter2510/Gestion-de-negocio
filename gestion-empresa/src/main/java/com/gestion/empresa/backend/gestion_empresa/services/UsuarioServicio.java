package com.gestion.empresa.backend.gestion_empresa.services;


import com.gestion.empresa.backend.gestion_empresa.models.Usuarios;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UsuarioServicio {

    Optional<Usuarios> buscarPorCui(Long cui);
    Optional<Usuarios> buscarPorId(Long id);
    Optional<Usuarios> buscarNombreUsuario(String nombreUsuario);
    // para que pueda editar
    Optional<Usuarios> editarUsuario(Usuarios usuarios);
}
