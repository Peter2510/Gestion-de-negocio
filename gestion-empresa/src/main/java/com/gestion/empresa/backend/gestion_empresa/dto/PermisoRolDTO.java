package com.gestion.empresa.backend.gestion_empresa.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: gordillox
 * Created on: 14/10/24
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermisoRolDTO {

    @NotNull
    private Long idPermiso;
    @NotNull
    private Long idRol;

}
