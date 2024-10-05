package com.gestion.empresa.backend.gestion_empresa.controllers;


import com.gestion.empresa.backend.gestion_empresa.models.TipoAsignacionCita;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.TipoAsignacionCitaServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Author: gordillox
 * Created on: 5/10/24
 */

@RestController
@Validated
@RequestMapping(path="/tipoAsignacionCita")
public class TipoAsignacionCitaController {

    @Autowired
    private TipoAsignacionCitaServiceImpl tipoAsignacionCitaService;

    @GetMapping("/obtenerTiposAsignacionCita")
    public ResponseEntity<Map<String, Object>> obtenerTiposAsignacionCitaRegistrados() {
        List<TipoAsignacionCita> tiposAsignacionCita = tipoAsignacionCitaService.obtenerTipoAsignacionCitaRegistrados();
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("ok", true, "tiposAsignacionCita", tiposAsignacionCita));
    }

    @PostMapping("/crearTipoAsignacionCita")
    public ResponseEntity<Map<String, Object>> crearTipoAsignacionCita(@Valid @RequestBody TipoAsignacionCita tipoAsignacionCita) {
        //verificar si el tipo de asignacion de cita ya existe
        if (tipoAsignacionCitaService.buscarPorNombre(tipoAsignacionCita.getTipo()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("ok", false, "mensaje", "El tipo de asignacion de cita " + tipoAsignacionCita.getTipo() + " ya existe"));
        }

        //si no existe se crea el tipo de asignacion de cita
        TipoAsignacionCita tipoAsignacionCitaCreado = tipoAsignacionCitaService.crearTipoAsignacionCita(tipoAsignacionCita);

        //verificar si el tipo de asignacion de cita fue creado
        if (tipoAsignacionCitaCreado == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("ok", false, "mensaje", "No se pudo crear el tipo de asignacion de cita"));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("ok", true, "mensaje", "Tipo de asignacion de cita creado exitosamente"));
    }



}
