package com.gestion.empresa.backend.gestion_empresa.dto;


import com.gestion.empresa.backend.gestion_empresa.models.Permiso;
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
public class RolPermisoDTO {

    private String nombre;
    private String descripcion;
    private List<Permiso> permisos;

}
