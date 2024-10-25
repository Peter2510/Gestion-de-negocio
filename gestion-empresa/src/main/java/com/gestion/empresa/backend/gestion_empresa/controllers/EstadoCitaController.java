package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.models.CategoriaServicio;
import com.gestion.empresa.backend.gestion_empresa.models.EstadoCita;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.CategoriaServicioServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.EstadoCitaServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Author: alexxus
 * Created on: 24/10/24
 */

@RestController
@Validated
@RequestMapping("/estadoCitas")
public class EstadoCitaController {

    @Autowired
    private EstadoCitaServiceImpl estadoCitaServiceImpl;


    // creacion de categorias para usuarios que trabajen ahi
    @PostMapping("/crear-EstadoCita")
    public ResponseEntity<Map<String, Object>> crearCategoria(
            @Validated @RequestBody EstadoCita estadoCita
    ) {
        //crea la categoria
        EstadoCita nuevoEstadoCita = new EstadoCita();
        nuevoEstadoCita.setNombre(estadoCita.getNombre());

        ResponseBackend respuesta = estadoCitaServiceImpl.registrarEstadoCita(nuevoEstadoCita);

        return ResponseEntity.status(respuesta.getStatus()).body(Map.of("ok", respuesta.getOk(), "mensaje", respuesta.getMensaje()));

    }

    // PARA LISTAR A TODOS
    @GetMapping("/obtener-EstadoCita")
    public ResponseEntity<Map<String, Object>> obtenerEstadosCitas() {
        List<EstadoCita> todosEstadoCitas = this.estadoCitaServiceImpl.obtenerTodo();
        //ENVIAR UNA NUEVA RESPUESTA
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("ok", true, "Estados" , todosEstadoCitas));
    }
}
