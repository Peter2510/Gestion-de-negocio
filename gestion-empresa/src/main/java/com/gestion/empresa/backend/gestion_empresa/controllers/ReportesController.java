package com.gestion.empresa.backend.gestion_empresa.controllers;


import com.gestion.empresa.backend.gestion_empresa.dto.AnioCitasDTO;
import com.gestion.empresa.backend.gestion_empresa.dto.MesCitasDTO;
import com.gestion.empresa.backend.gestion_empresa.dto.SemanaCitasDTO;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.ReporteServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Author: gordillox
 * Created on: 29/10/24
 */
@RestController
@RequestMapping("/reportes")
public class ReportesController {

    @Autowired
    private ReporteServiceImpl reporteService;

    @Autowired
    private ReporteServiceImpl citaService;


     //endpoint para contar las citas por semana y año.
    @GetMapping("/citas/semana")
    public List<SemanaCitasDTO> contarCitasPorSemana(@RequestParam("anio") int anio) {
        return citaService.contarCitasPorSemana(anio);
    }

     //endpoint para contar las citas por mes y año.
    @GetMapping("/citas/mes")
    public List<MesCitasDTO> contarCitasPorMes(@RequestParam("anio") int anio) {
        return citaService.contarCitasPorMes(anio);
    }

    // Endpoint para contar las citas por año.
    @GetMapping("/citas/anio/{anio}")
    public List<AnioCitasDTO> contarCitasPorAnio(@PathVariable int anio) {
        return citaService.contarCitasPorAnio(anio);
    }

    @GetMapping("/citas/por-servicio")
    public List<Object[]> getCitasPorServicio() {
        return citaService.getCitasPorServicio();
    }

    @GetMapping("/citas/por-estado")
    public List<Object[]> getCitasPorEstado() {
        return citaService.getCitasPorEstado();
    }

    @GetMapping("/empleados/por-rol")
    public List<Object[]> getEmpleadosPorRol() {
        return citaService.getEmpleadosPorRol();
    }

    @GetMapping("/obtener-usuarios-por-rol")
    public ResponseEntity<Map<String, Object>> obtenerUsuariosPorRol() {
        ResponseBackend response = reporteService.usuariosPorRol();
        return ResponseEntity.status(response.getStatus()).body(Map.of(
                "ok", response.getOk(),
                "usuariosPorRol", response.getData()
        ));
    }

}
