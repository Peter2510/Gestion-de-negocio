package com.gestion.empresa.backend.gestion_empresa.controllers;


import com.gestion.empresa.backend.gestion_empresa.servicesImpl.ReporteServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/obtener-usuarios-por-rol")
    public ResponseEntity<Map<String, Object>> obtenerUsuariosPorRol() {

        ResponseBackend response = reporteService.usuariosPorRol();

        return ResponseEntity.status(response.getStatus()).body(Map.of(
                "ok", response.getOk(),
                "usuariosPorRol", response.getData()
        ));

    }

}
