package com.gestion.empresa.backend.gestion_empresa.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Author: alexxus
 * Created on: 24/10/24
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroCitasDTO {
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime horaFin;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime horaInicio;
    private  Long idEstadoCita;
     private Long idServicio;
     private Long idUsuario;
     private Long idDiaLaboral;
     private List<Long> listadoServiciosEspecificos;
}
