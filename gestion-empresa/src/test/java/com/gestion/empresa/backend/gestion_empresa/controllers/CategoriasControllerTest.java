package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.models.CategoriaServicio;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.CategoriaServicioServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriasControllerTest {

    @InjectMocks
    private CategoriasController categoriasController;

    @Mock
    private CategoriaServicioServiceImpl categoriaServicioService;

    private CategoriaServicio categoriaServicio;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        categoriaServicio = new CategoriaServicio();
        categoriaServicio.setTipo("Tipo1");
    }

    @Test
    public void testCrearCategoria_Success() {
        ResponseBackend respuesta = new ResponseBackend(true, HttpStatus.CREATED, "Categoria creada exitosamente");
        when(categoriaServicioService.registrarCategoria(any())).thenReturn(respuesta);

        ResponseEntity<Map<String, Object>> response = categoriasController.crearCategoria(categoriaServicio);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue((Boolean) response.getBody().get("ok"));
        assertEquals("Categoria creada exitosamente", response.getBody().get("mensaje"));
    }

    @Test
    public void testObtenerCategorias_Success() {
        List<CategoriaServicio> categorias = Arrays.asList(categoriaServicio);
        when(categoriaServicioService.obtenerTodo()).thenReturn(categorias);

        ResponseEntity<Map<String, Object>> response = categoriasController.obtenerCategorias();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody().get("ok"));
        assertEquals(1, ((List<?>) response.getBody().get("categorias")).size());
    }

    @Test
    public void testActualizarCategoria_Success() {
        ResponseBackend respuesta = new ResponseBackend(true, HttpStatus.OK, "Categoria actualizada exitosamente");
        when(categoriaServicioService.actualizarCategoria(any())).thenReturn(respuesta);

        ResponseEntity<Map<String, Object>> response = categoriasController.actualizarCategoria(categoriaServicio);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody().get("ok"));
        assertEquals("Categoria actualizada exitosamente", response.getBody().get("mensaje"));
    }

    @Test
    public void testObtenerCategoria_Success() {
        Long id = 1L;
        ResponseBackend respuesta = new ResponseBackend(true, HttpStatus.OK, categoriaServicio);
        when(categoriaServicioService.buscarPorId(id)).thenReturn(respuesta);

        ResponseEntity<Map<String, Object>> response = categoriasController.obtenerCategoria(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody().get("ok"));
        assertEquals(categoriaServicio, response.getBody().get("categoria"));
    }

    @Test
    public void testObtenerCategoria_NotFound() {
        Long id = 1L;
        ResponseBackend respuesta = new ResponseBackend(false, HttpStatus.NOT_FOUND, "Categoria no encontrada");
        when(categoriaServicioService.buscarPorId(id)).thenReturn(respuesta);

        ResponseEntity<Map<String, Object>> response = categoriasController.obtenerCategoria(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse((Boolean) response.getBody().get("ok"));
        assertEquals("Categoria no encontrada", response.getBody().get("mensaje"));
    }
}
