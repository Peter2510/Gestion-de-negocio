package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.models.CategoriaServicio;
import com.gestion.empresa.backend.gestion_empresa.models.EstadoServicio;
import com.gestion.empresa.backend.gestion_empresa.services.CategoriaServicioService;
import com.gestion.empresa.backend.gestion_empresa.services.EstadoServicioService;
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
@RequestMapping("/estadoServicio")
public class EstadoServicioController {

    @Autowired
    private EstadoServicioService estadoServicioService;


    // creacion de categorias para usuarios que trabajen ahi
    @PostMapping("/crearEstadosServicios")
    public ResponseEntity<Map<String, Object>> crearCategoria(
            @RequestBody EstadoServicio estadoServicio
    ) {
        try {
            //crea la categoria
            EstadoServicio estados = new EstadoServicio();
            estados.setEstado(estadoServicio.getEstado());

            EstadoServicio respuesta =this.estadoServicioService.ingresarEstado(estados);

            if(respuesta!= null) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(Map.of("ok", true, "mensaje", "estado creado correctamente"));
            }
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("ok", false, "mensaje", "error en su creacions"));

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("ok", false, "mensaje", "Error al guardar el estado", "error", e.getMessage()));
        }

    }


    // PARA LISTAR A TODOS
    @GetMapping("/obtenerTodosEstadosServicios")
    public ResponseEntity<List<EstadoServicio>> obtenerEstadosServicios() {
        List<EstadoServicio>   todosEstadosServicio  = this.estadoServicioService.obtenerTodo();
        //ENVIAR UNA NUEVA RESPUESTA
        return new ResponseEntity<>(todosEstadosServicio, HttpStatus.OK);
    }

}
