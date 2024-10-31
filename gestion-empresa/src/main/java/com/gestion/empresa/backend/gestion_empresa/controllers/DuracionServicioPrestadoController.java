package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.models.Servicios;
import com.gestion.empresa.backend.gestion_empresa.services.DuracionServicioPrestadoService;
import com.gestion.empresa.backend.gestion_empresa.services.ServiciosService;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.DuracionServicioPrestadoServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.ServiciosServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Author: alexxus
 * Created on: 21/10/24
 */

@RestController
@Validated
@RequestMapping("/duracionServicioPrestado")
public class DuracionServicioPrestadoController {

    @Autowired
    private DuracionServicioPrestadoService duracionServicioPrestadoService;

    @Autowired
    private ServiciosServiceImpl serviciosServiceImpl;

    @GetMapping("/obtenerTodosServiciosEspecificos/{idServicio}")
    public ResponseEntity<Map<String, Object>> obtenerTodosServiciosEspecificos(@PathVariable Long idServicio) {
        List<Object[]> response = duracionServicioPrestadoService.obtenerTodosServicios(idServicio);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("ok", true, "todoServicios", response));
    }

    @GetMapping("/duracionServicioEspecifico/{idServicio}")
    public ResponseEntity<Map<String, Object>> obtenerDuracionServiciosEspecificos(@PathVariable Long idServicio) {
        Object response = serviciosServiceImpl.obtenerDuracionSeriviosPrestadosId(idServicio);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("ok", true, "duracion", response));
    }



}
