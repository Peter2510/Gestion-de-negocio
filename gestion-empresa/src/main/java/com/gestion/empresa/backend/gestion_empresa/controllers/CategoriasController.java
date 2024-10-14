package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.models.*;
import com.gestion.empresa.backend.gestion_empresa.services.CategoriaServicioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private CategoriaServicioService categoriaServicioService;


    // creacion de categorias para usuarios que trabajen ahi
    @PostMapping("/crearCategoria")
    public ResponseEntity<Map<String, Object>> crearCategoria(
            @RequestBody CategoriaServicio tipo
    ) {
        try {
            //crea la categoria
            CategoriaServicio categoria = new CategoriaServicio();
            categoria.setTipo(tipo.getTipo());

            CategoriaServicio respuesta =this.categoriaServicioService.ingresarCategoria(categoria);

            if(respuesta!= null) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(Map.of("ok", true, "mensaje", "Categoria creada correctamente"));
            }
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("ok", false, "mensaje", "error en su creacions"));

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("ok", false, "mensaje", "Error al guardar la Categoria", "error", e.getMessage()));
        }

    }


    // PARA LISTAR A TODOS
    @GetMapping("/obtenerTodasCategorias")
    public ResponseEntity<List<CategoriaServicio>> obtenerCategorias() {
        List<CategoriaServicio>   todasCategorias  = this.categoriaServicioService.obtenerTodo();
        //ENVIAR UNA NUEVA RESPUESTA
        return new ResponseEntity<>(todasCategorias, HttpStatus.OK);
    }



}
