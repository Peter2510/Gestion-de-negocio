package com.gestion.empresa.backend.gestion_empresa.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/*
    Author: peterg
    Created on: 24/10/24
*/
@Data
@Getter
@Setter
public class ActualizarContraseniaDTO {
    private String contraseniaActual;
    private String contraseniaNueva;
    private Long idUsuario;
}
