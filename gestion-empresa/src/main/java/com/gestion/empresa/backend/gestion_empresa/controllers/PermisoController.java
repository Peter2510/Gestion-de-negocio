package com.gestion.empresa.backend.gestion_empresa.controllers;


import com.gestion.empresa.backend.gestion_empresa.models.Permiso;
import com.gestion.empresa.backend.gestion_empresa.services.PermisoService;
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
 * Created on: 13/10/24
 */

@RestController
@RequestMapping("/permiso")
@Validated
public class PermisoController {

    @Autowired private PermisoService permisoService;

    @PostMapping("crear-permiso")
    public ResponseEntity<Map<String, Object>> crearPermiso(@Valid @RequestBody Permiso permiso) {
            if (permisoService.buscarPermisoPorNombre(permiso.getNombre()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("ok", false, "mensaje", "El permiso " + permiso.getNombre() + " ya esta registrado"));
            }

            Permiso permisoCreado = permisoService.crearPermiso(permiso);

            //verificar si el permiso fue creado
            if (permisoCreado == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("ok", false, "mensaje", "Error al crear el permiso"));
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("ok", true, "mensaje", "Permiso creado exitosamente"));
    }

    @GetMapping("obtener-permisos-registrados")
    public ResponseEntity<Map<String, Object>> obtenerPermisos() {

        List<Permiso> permisos = permisoService.obtenerPermisosRegistrados();

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("ok", true, "permisos", permisos));

    }

}
