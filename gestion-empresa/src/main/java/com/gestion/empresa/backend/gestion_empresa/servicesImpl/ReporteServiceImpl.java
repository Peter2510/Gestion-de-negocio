package com.gestion.empresa.backend.gestion_empresa.servicesImpl;


import com.gestion.empresa.backend.gestion_empresa.repositories.UsuarioRepository;
import com.gestion.empresa.backend.gestion_empresa.services.ReporteService;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: gordillox
 * Created on: 29/10/24
 */

@Service
public class ReporteServiceImpl implements ReporteService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public ResponseBackend cantidadServiciosRegistrados() {
        return null;
    }

    @Override
    public ResponseBackend cantidadPersonasPorGenero(Long idGenero) {
        return null;
    }

    @Override
    public ResponseBackend citasPorSemana(int numeroSemana, int anio) {
        return null;
    }

    @Override
    public ResponseBackend citasPorMes(int numeroMes, int anio) {
        return null;
    }

    @Override
    public ResponseBackend citasPorFecha(DateTimeFormat fecha) {
        return null;
    }

    @Override
    public ResponseBackend citasPorUsuario(Long idUsuario) {
        return null;
    }

    @Override
    public ResponseBackend citasPorServicio(Long idServicio) {
        return null;
    }

    @Override
    public ResponseBackend citasPorEstado(Long idEstado) {
        return null;
    }

    @Override
    public ResponseBackend citasPorUsuarioYEstado(Long idUsuario, Long idEstado) {
        return null;
    }

    @Override
    public ResponseBackend citasPorAnio(int anio) {
        return null;
    }

    @Override
    public ResponseBackend gananciasPorDia(int dia, int anio) {
        return null;
    }

    @Override
    public ResponseBackend gananciasPorSemana(int numeroSemana, int anio) {
        return null;
    }

    @Override
    public ResponseBackend gananciasPorMes(int numeroMes, int anio) {
        return null;
    }

    @Override
    public ResponseBackend gananciasPorAnio(int anio) {
        return null;
    }

    @Override
    public ResponseBackend gananciasPorUsuario(Long idUsuario) {
        return null;
    }

    @Override
    public ResponseBackend gananciasPorServicio(Long idServicio) {
        return null;
    }

    @Override
    public ResponseBackend empleadosPorServicio(Long idServicio) {
        return null;
    }

    @Override
    public ResponseBackend usuariosPorRol() {

        List<Object[]> resultado = usuarioRepository.countUsuariosByRol();
        Map<String, Long> usuariosPorRol = new HashMap<>();

        for (Object[] row : resultado) {
            String rol = (String) row[0];
            Long count = (Long) row[1];
            usuariosPorRol.put(rol, count);
        }

        System.out.println(usuariosPorRol);

        return new ResponseBackend(true, HttpStatus.OK, "Usuarios por rol", usuariosPorRol);

    }
}
