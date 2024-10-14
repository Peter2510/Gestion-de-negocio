package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.models.CategoriaServicio;
import com.gestion.empresa.backend.gestion_empresa.models.JornadaLaboral;
import com.gestion.empresa.backend.gestion_empresa.services.CategoriaServicioService;
import com.gestion.empresa.backend.gestion_empresa.services.JornadaLaboralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Author: alexxus
 * Created on: 13/10/24
 */
@RestController
@Validated
@RequestMapping("/joranadaLaboral")
public class JornadaLaboralController {

    @Autowired
    private JornadaLaboralService jornadaLaboralService;


    // creacion de categorias para usuarios que trabajen ahi
    @PostMapping("/crearJornada")
    public ResponseEntity<Map<String, Object>> crearJornada(
            @RequestBody JornadaLaboral jornadaLaboral
    ) {
        try {
            //crea la categoria
            JornadaLaboral nuevaJornada = new JornadaLaboral();
            nuevaJornada.setNombre(jornadaLaboral.getNombre());
            nuevaJornada.setHoraInicio(jornadaLaboral.getHoraInicio());
            nuevaJornada.setHoraFin(jornadaLaboral.getHoraFin());

            JornadaLaboral respuesta =this.jornadaLaboralService.ingresarJornada(nuevaJornada);

            if(respuesta!= null) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(Map.of("ok", true, "mensaje", "jornada laboral creada correctamente"));
            }
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("ok", false, "mensaje", "error en su creacions"));

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("ok", false, "mensaje", "Error al guardar la jornada laboral", "error", e.getMessage()));
        }

    }
}
