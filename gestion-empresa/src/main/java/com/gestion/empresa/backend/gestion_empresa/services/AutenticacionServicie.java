package com.gestion.empresa.backend.gestion_empresa.services;


import com.gestion.empresa.backend.gestion_empresa.dto.AuthRespuesta;
import com.gestion.empresa.backend.gestion_empresa.models.Usuarios;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface AutenticacionServicie {


    Optional<Usuarios> buscarNombre(String nombreUsuario);
    Optional<AuthRespuesta> validarCredenciales(String password, Optional<Usuarios> usuarios);
    Optional<AuthRespuesta> generacionToken(Optional<Usuarios> usuarios);


}
