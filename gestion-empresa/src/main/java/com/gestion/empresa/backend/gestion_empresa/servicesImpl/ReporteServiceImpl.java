package com.gestion.empresa.backend.gestion_empresa.servicesImpl;


import com.gestion.empresa.backend.gestion_empresa.dto.AnioCitasDTO;
import com.gestion.empresa.backend.gestion_empresa.dto.MesCitasDTO;
import com.gestion.empresa.backend.gestion_empresa.dto.SemanaCitasDTO;
import com.gestion.empresa.backend.gestion_empresa.repositories.CitasRepository;
import com.gestion.empresa.backend.gestion_empresa.repositories.UsuarioRepository;
import com.gestion.empresa.backend.gestion_empresa.services.ReporteService;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: gordillox
 * Created on: 29/10/24
 */

@Service
public class ReporteServiceImpl implements ReporteService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CitasRepository citasRepository;

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


    public List<SemanaCitasDTO> contarCitasPorSemana(int anio) {
        return citasRepository.countCitasPorSemana(anio);
    }

    public List<MesCitasDTO> contarCitasPorMes(int anio) {
        return citasRepository.countCitasPorMes(anio);
    }

    public List<AnioCitasDTO> contarCitasPorAnio(int anio) {
        List<Tuple> tuples = citasRepository.countCitasPorAnio(anio);
        return tuples.stream()
                .map(tuple -> new AnioCitasDTO(tuple.get(0, Integer.class), tuple.get(1, Long.class)))
                .collect(Collectors.toList());
    }

    public List<Object[]> getEmpleadosPorRol() {
        return usuarioRepository.findEmpleadosPorRol();
    }

    public List<Object[]> getCitasPorServicio() {
        return citasRepository.findCitasPorServicio();
    }

    public List<Object[]> getCitasPorEstado() {
        return citasRepository.findCitasPorEstado();
    }


}
