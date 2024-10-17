package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.models.JornadaLaboral;
import com.gestion.empresa.backend.gestion_empresa.models.JornadaPorDia;
import com.gestion.empresa.backend.gestion_empresa.services.JornadaLaboralService;
import com.gestion.empresa.backend.gestion_empresa.services.JornadaPorDiaService;
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
 * Created on: 15/10/24
 */

@RestController
@Validated
@RequestMapping("/jornadaPorDia")
public class JornadaPorDiaController {

    @Autowired
    private JornadaPorDiaService jornadaPorDiaService;


    // creacion de categorias para usuarios que trabajen ahi
    @PostMapping("/asignacionJornadaDia")
    public ResponseEntity<Map<String, Object>> asignarJornada(
            @RequestBody JornadaPorDia jornadaPorDia
    ) {
        try {
            System.out.println(jornadaPorDia);
            //crea la categoria
            JornadaPorDia nuevaJornadaPorDia = new JornadaPorDia();
            nuevaJornadaPorDia.setIdDiaLaboral(jornadaPorDia.getIdDiaLaboral());
            nuevaJornadaPorDia.setIdJornadaLaboral(jornadaPorDia.getIdJornadaLaboral());

            JornadaPorDia respuesta = this.jornadaPorDiaService.ingresarJornadaDia(nuevaJornadaPorDia);

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
