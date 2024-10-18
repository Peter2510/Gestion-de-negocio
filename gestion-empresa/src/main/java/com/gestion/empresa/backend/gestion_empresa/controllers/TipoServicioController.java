package com.gestion.empresa.backend.gestion_empresa.controllers;


import com.gestion.empresa.backend.gestion_empresa.models.TipoServicio;
import com.gestion.empresa.backend.gestion_empresa.repositories.TipoServicioRepository;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.TipoServicioServiceImpl;
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
 * Created on: 4/10/24
 */

@RestController
@RequestMapping("/tipoServicio")
@Validated
public class TipoServicioController {

    @Autowired
    private TipoServicioServiceImpl tipoServicioService;

    @GetMapping("/obtener-tipos-servicio")
    public ResponseEntity<Map<String, Object>> obtenerTiposServicioRegistrados() {
        List<TipoServicio> tiposServicio = tipoServicioService.obtenerTipoServiciosRegistrados();
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("ok", true, "tiposServicio", tiposServicio));
    }

    @PostMapping("/crear-tipo-servicio")
    public ResponseEntity<Map<String, Object>> crearTipoServicio(@Validated @RequestBody TipoServicio tipoServicio) {
        //verificar si el tipo de servicio ya existe
        if (tipoServicioService.buscarPorNombre(tipoServicio.getNombre()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("ok", false, "mensaje", "El tipo de servicio " + tipoServicio.getNombre() + " ya existe"));
        }

        //si no existe se crea el tipo de servicio
        TipoServicio tipoServicioCreado = tipoServicioService.crearTipoServicio(tipoServicio);

        //verificar si el tipo de servicio fue creado
        if (tipoServicioCreado == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("ok", false, "mensaje", "No se pudo crear el tipo de servicio"));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("ok", true, "mensaje", "Tipo de servicio creado exitosamente"));
    }

}
