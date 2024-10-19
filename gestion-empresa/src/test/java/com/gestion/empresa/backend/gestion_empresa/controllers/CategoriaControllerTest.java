package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.models.CategoriaServicio;
import com.gestion.empresa.backend.gestion_empresa.services.CategoriaServicioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Author: alexxus
 * Created on: 15/10/24
 */
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class CategoriaControllerTest {

    @InjectMocks
    private CategoriasController categoriasController;

    @Mock
    private CategoriaServicioService categoriaServicioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void testCrearCategoriaExito() {
//        CategoriaServicio mockCategoria = new CategoriaServicio();
//        mockCategoria.setTipo("Test");
//
//        when(categoriaServicioService.ingresarCategoria(any(CategoriaServicio.class)))
//                .thenReturn(mockCategoria);
//
//        ResponseEntity<Map<String, Object>> response = categoriasController.crearCategoria(mockCategoria);
//
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals(true, response.getBody().get("ok"));
//        assertEquals("Categoria creada correctamente", response.getBody().get("mensaje"));
//    }
//
//    @Test
//    void testCrearCategoriaError() {
//        when(categoriaServicioService.ingresarCategoria(any(CategoriaServicio.class)))
//                .thenThrow(new RuntimeException("Error de prueba"));
//
//        CategoriaServicio mockCategoria = new CategoriaServicio();
//        mockCategoria.setTipo("Test");
//
//        ResponseEntity<Map<String, Object>> response = categoriasController.crearCategoria(mockCategoria);
//
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//        assertEquals(false, response.getBody().get("ok"));
//        assertEquals("Error al guardar la Categoria", response.getBody().get("mensaje"));
//        assertEquals("Error de prueba", response.getBody().get("error"));
//    }

//    @Test
//    void testObtenerCategoriasExito() {
//        List<CategoriaServicio> mockCategorias = new ArrayList<>();
//        CategoriaServicio categoria1 = new CategoriaServicio();
//        categoria1.setTipo("Test 1");
//        CategoriaServicio categoria2 = new CategoriaServicio();
//        categoria2.setTipo("Test 2");
//        mockCategorias.add(categoria1);
//        mockCategorias.add(categoria2);
//
//        when(categoriaServicioService.obtenerTodo()).thenReturn(mockCategorias);
//
//        ResponseEntity<List<CategoriaServicio>> response = categoriasController.obtenerCategorias();
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(2, response.getBody().size());
//    }
}
