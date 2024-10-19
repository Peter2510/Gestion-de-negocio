package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.models.*;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.CategoriaServicioServiceImpl;
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
 * Created on: 13/10/24
 */

@RestController
@Validated
@RequestMapping("/categorias")
public class CategoriasController {

    @Autowired
    private CategoriaServicioServiceImpl categoriaServicioService;


    // creacion de categorias para usuarios que trabajen ahi
    @PostMapping("/crear-categoria")
    public ResponseEntity<Map<String, Object>> crearCategoria(
            @Validated @RequestBody CategoriaServicio tipo
    ) {
        //crea la categoria
        CategoriaServicio categoria = new CategoriaServicio();
        categoria.setTipo(tipo.getTipo());

        ResponseBackend respuesta = categoriaServicioService.registrarCategoria(categoria);

        return ResponseEntity.status(respuesta.getStatus()).body(Map.of("ok", respuesta.getOk(), "mensaje", respuesta.getMensaje()));

    }

    // PARA LISTAR A TODOS
    @GetMapping("/obtener-categorias")
    public ResponseEntity<Map<String, Object>> obtenerCategorias() {
        List<CategoriaServicio> todasCategorias = this.categoriaServicioService.obtenerTodo();
        //ENVIAR UNA NUEVA RESPUESTA
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("ok", true, "categorias" , todasCategorias));
    }

    @PutMapping("actualizar-categoria")
    public ResponseEntity<Map<String, Object>> actualizarCategoria(
            @Validated @RequestBody CategoriaServicio categoria
    ) {
        ResponseBackend respuesta = categoriaServicioService.actualizarCategoria(categoria);

        return ResponseEntity.status(respuesta.getStatus()).body(Map.of("ok", respuesta.getOk(), "mensaje", respuesta.getMensaje()));
    }

    @GetMapping("/obtener-categoria/{id}")
    public ResponseEntity<Map<String, Object>> obtenerCategoria(@PathVariable Long id) {

        ResponseBackend respuesta = categoriaServicioService.buscarPorId(id);

        return ResponseEntity.status(respuesta.getStatus()).body(
                respuesta.getOk()
                        ? Map.of("ok", true, "categoria", respuesta.getData())
                        : Map.of("ok", false, "mensaje", respuesta.getMensaje())
        );

    }


}
