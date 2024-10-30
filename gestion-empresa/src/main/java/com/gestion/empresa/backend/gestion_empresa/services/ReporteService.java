package com.gestion.empresa.backend.gestion_empresa.services;


import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

/**
 * Author: gordillox
 * Created on: 29/10/24
 */
@Service
public interface ReporteService {
    ResponseBackend cantidadServiciosRegistrados();
    ResponseBackend cantidadPersonasPorGenero(Long idGenero);
    ResponseBackend citasPorSemana(int numeroSemana, int anio);
    ResponseBackend citasPorMes(int numeroMes, int anio);
    ResponseBackend citasPorFecha(DateTimeFormat fecha);
    ResponseBackend citasPorUsuario(Long idUsuario);
    ResponseBackend citasPorServicio(Long idServicio);
    ResponseBackend citasPorEstado(Long idEstado);
    ResponseBackend citasPorUsuarioYEstado(Long idUsuario, Long idEstado);
    ResponseBackend citasPorAnio(int anio);
    ResponseBackend gananciasPorDia(int dia, int anio);
    ResponseBackend gananciasPorSemana(int numeroSemana, int anio);
    ResponseBackend gananciasPorMes(int numeroMes, int anio);
    ResponseBackend gananciasPorAnio(int anio);
    ResponseBackend gananciasPorUsuario(Long idUsuario);
    ResponseBackend gananciasPorServicio(Long idServicio);
    ResponseBackend empleadosPorServicio(Long idServicio);
    ResponseBackend usuariosPorRol();
}
