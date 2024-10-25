package com.gestion.empresa.backend.gestion_empresa.dto;

import com.gestion.empresa.backend.gestion_empresa.models.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author: alexxus
 * Created on: 17/10/24
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NuevoServicioDTO {
    private Long idUsuario;
    private Servicios servicios;
    private List<JornadaLaboral> jornadaLaboral;
    private List<DiasLaborales> diasLaborales;
    private List<ServicioPrestado> servicioPrestados;
    private List<DuracionServicioPrestado> duracionServicioPrestados;
    private List<ImagenServicioPrestado> imagenServicioPrestados;
}
