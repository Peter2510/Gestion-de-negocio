package com.gestion.empresa.backend.gestion_empresa.dto;

import com.gestion.empresa.backend.gestion_empresa.models.Persona;
import com.gestion.empresa.backend.gestion_empresa.models.Rol;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RegistroUsuariosDTO {
    private String nombreUsuario;
    private String password;
    private Long idRol;
    private Long idGenero;
    private Persona persona;
    private String correo;
    private boolean activo;
    private boolean a2fActivo;
}

