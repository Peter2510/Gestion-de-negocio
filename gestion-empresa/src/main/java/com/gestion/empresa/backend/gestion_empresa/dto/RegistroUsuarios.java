package com.gestion.empresa.backend.gestion_empresa.dto;

import com.gestion.empresa.backend.gestion_empresa.models.Persona;
import com.gestion.empresa.backend.gestion_empresa.models.Rol;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistroUsuarios {
    private long id;
    private String nombreUsuario;
    private String password;
    private Rol rol;
    private Persona persona;
    private String correo;
    private boolean activo;
    private boolean a2fActivo;


}

