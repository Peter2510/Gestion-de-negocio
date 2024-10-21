package com.gestion.empresa.backend.gestion_empresa.dto;


import com.gestion.empresa.backend.gestion_empresa.models.Persona;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Author: gordillox
 * Created on: 20/10/24
 */

@Data
@Getter
@Setter
public class ActualizacionUsuarioAdminDTO {
    private Long idUsuario;
    private String nombreUsuario;
    private Long idRol;
    private Long idGenero;
    private Persona persona;
    private String correo;
    private boolean activo;
}
