package com.gestion.empresa.backend.gestion_empresa.services;


import com.gestion.empresa.backend.gestion_empresa.dto.ActualizacionUsuarioAdminDTO;
import com.gestion.empresa.backend.gestion_empresa.dto.ActualizarContraseniaDTO;
import com.gestion.empresa.backend.gestion_empresa.dto.RegistroUsuariosDTO;
import com.gestion.empresa.backend.gestion_empresa.models.Usuarios;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UsuarioServicio {

    Optional<Usuarios> buscarPorCui(Long cui);
    ResponseBackend buscarPorId(Long id);
    Optional<Usuarios> buscarNombreUsuario(String nombreUsuario);
    // para que pueda editar
    ResponseBackend actualizarUsuario(ActualizacionUsuarioAdminDTO registro);
    ResponseBackend listarUsuariosPorRol(Long idRol);
    ResponseBackend listarEmpleados(Long idRol);
    ResponseBackend actualizarContrasenia(ActualizarContraseniaDTO registro);
    ResponseBackend recuperarContrasenia(String correo);
    ResponseBackend validarCodigoRecuperacion(String correo, String codigoRecuperacion);
    ResponseBackend cambioContrasenia(ActualizarContraseniaDTO actualizacion);
}
