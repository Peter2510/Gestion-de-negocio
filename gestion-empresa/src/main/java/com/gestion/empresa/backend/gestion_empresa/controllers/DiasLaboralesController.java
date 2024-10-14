package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.models.CategoriaServicio;
import com.gestion.empresa.backend.gestion_empresa.models.DiasLaborales;
import com.gestion.empresa.backend.gestion_empresa.services.CategoriaServicioService;
import com.gestion.empresa.backend.gestion_empresa.services.DiasLaboralesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Author: alexxus
 * Created on: 13/10/24
 */

@RestController
@Validated
@RequestMapping("/diasLaborales")
public class DiasLaboralesController {

    @Autowired
    private DiasLaboralesService diasLaboralesService;


    // creacion de dia
    @PostMapping("/crearDia")
    public ResponseEntity<Map<String, Object>> crearDia(
            @RequestBody DiasLaborales dias
    ) {
        try {
            //crea la categoria
            DiasLaborales nuevoDia = new DiasLaborales();
            nuevoDia.setNombre(dias.getNombre());

            DiasLaborales respuesta =this.diasLaboralesService.ingresarDias(nuevoDia);

            if(respuesta!= null) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(Map.of("ok", true, "mensaje", "Dia laboral creado correctamente"));
            }
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("ok", false, "mensaje", "error en su creacions"));

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("ok", false, "mensaje", "Error al guardar el Dia laboral", "error", e.getMessage()));
        }

    }


    // PARA LISTAR A TODOS
    @GetMapping("/obtenerTodosDias")
    public ResponseEntity<List<DiasLaborales>> obtenerDias() {
        List<DiasLaborales>   todosDias  = this.diasLaboralesService.obtenerTodosDias();
        //ENVIAR UNA NUEVA RESPUESTA
        return new ResponseEntity<>(todosDias, HttpStatus.OK);
    }

    // para listar por id
    @GetMapping("/obtenerDiaEspecifico")
    public ResponseEntity<List<DiasLaborales>> obtenerDiasEspecifico() {
        List<DiasLaborales>   todosDias  = this.diasLaboralesService.obtenerTodosDias();
        //ENVIAR UNA NUEVA RESPUESTA
        return new ResponseEntity<>(todosDias, HttpStatus.OK);
    }

}
