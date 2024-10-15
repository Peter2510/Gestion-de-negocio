package com.gestion.empresa.backend.gestion_empresa.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author: gordillox
 * Created on: 15/10/24
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolDTO {
    //DTO para la actualizacion de datos del rol
    private String nombre;
    private String descripcion;
    private List<PermisoRolDTO> permisos;
}
