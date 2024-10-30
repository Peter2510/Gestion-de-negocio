package com.gestion.empresa.backend.gestion_empresa.services;


import com.gestion.empresa.backend.gestion_empresa.dto.AuthRespuesta;
import com.gestion.empresa.backend.gestion_empresa.models.Usuarios;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface AutenticacionServicie {


    Optional<AuthRespuesta> validarCredenciales(String password, Optional<Usuarios> usuarios);
    Optional<AuthRespuesta> generacionToken(Optional<Usuarios> usuarios);
    ResponseBackend validacionCodigoA2F(Long idUsuario, String codigo);

}
