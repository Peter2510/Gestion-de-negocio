package com.gestion.empresa.backend.gestion_empresa.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: gordillox
 * Created on: 29/10/24
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecuperarContraseniaDTO {

    private String correo;
    private String codigo;

}
