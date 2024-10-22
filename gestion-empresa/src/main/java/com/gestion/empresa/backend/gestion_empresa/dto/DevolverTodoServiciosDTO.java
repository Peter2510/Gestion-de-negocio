package com.gestion.empresa.backend.gestion_empresa.dto;

import com.gestion.empresa.backend.gestion_empresa.models.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

/**
 * Author: alexxus
 * Created on: 20/10/24
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DevolverTodoServiciosDTO {
    private List<Servicios> servicios;
   // private  List<JornadaServicio> jornadaServicioList;
    private List<JornadaDTO> jornadaServicio;  // Cambiamos para incluir el desglose de jornada

    // Otros atributos relacionados
    private List<DuracionServicioPrestado> duracionServicioPrestados;
    private List<ImagenServicioPrestado> imagenServicioPrestados;

    // Clase interna para modelar el desglose de Jornada
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class JornadaDTO {
        private Servicios servicios;
        private ServicioDTO serviciosEspecificos;
        private JornadaPorDia jornadaPorDia;  // Incluye JornadaPorDia y sus relaciones
        private JornadaLaboral jornadaLaboral;
        private DiasLaborales diasLaborales;
    }

//clase para servicios

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ServicioDTO {
        private DuracionServicioPrestado duracionServicioPrestado; // para los servicios
        private ServiciosAsignado serviciosAsignado;

    }
}
