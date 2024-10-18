package com.gestion.empresa.backend.gestion_empresa.dto;


import com.gestion.empresa.backend.gestion_empresa.models.Permiso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "El nombre del rol es obligatorio")
    private String nombre;

    @NotBlank(message = "La descripcion del rol es obligatoria")
    private String descripcion;

    @NotNull(message = "Los permisos son obligatorios")
    private List<Permiso> permisos;

}
