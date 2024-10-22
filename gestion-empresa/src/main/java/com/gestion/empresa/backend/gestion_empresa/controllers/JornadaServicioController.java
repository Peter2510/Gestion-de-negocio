package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.services.DuracionServicioPrestadoService;
import com.gestion.empresa.backend.gestion_empresa.services.JornadaServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Author: alexxus
 * Created on: 21/10/24
 */

@RestController
@Validated
@RequestMapping("/jornadaServicio")
public class JornadaServicioController {

    @Autowired
    private JornadaServicioService jornadaServicioService;


    @GetMapping("/obtenerTodasJornadas/{idServicio}")
    public ResponseEntity<Map<String, Object>> obtenerTodasJornadasEspecificos(@PathVariable Long idServicio) {
        List<Object[]> response = jornadaServicioService.obtenerTodasJornadas(idServicio);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("ok", true, "todoServicios", response));
    }
}
