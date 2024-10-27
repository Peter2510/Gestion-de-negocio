package com.gestion.empresa.backend.gestion_empresa.controllers;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.gestion.empresa.backend.gestion_empresa.models.EstadoServicio;
import com.gestion.empresa.backend.gestion_empresa.services.EstadoServicioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class EstadoServicioControllerTest {

    @InjectMocks
    private EstadoServicioController estadoServicioController;

    @Mock
    private EstadoServicioService estadoServicioService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCrearEstadoServicio_Success() {
        EstadoServicio estadoServicio = new EstadoServicio();
        estadoServicio.setEstado("Activo");

        EstadoServicio respuestaServicio = new EstadoServicio();
        respuestaServicio.setEstado("Activo");

        when(estadoServicioService.ingresarEstado(any())).thenReturn(respuestaServicio);

        ResponseEntity<Map<String, Object>> response = estadoServicioController.crearCategoria(estadoServicio);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue((Boolean) response.getBody().get("ok"));
        assertEquals("estado creado correctamente", response.getBody().get("mensaje"));

        verify(estadoServicioService, times(1)).ingresarEstado(any());
    }

    @Test
    void testCrearEstadoServicio_Error() {
        EstadoServicio estadoServicio = new EstadoServicio();
        estadoServicio.setEstado("Inactivo");

        when(estadoServicioService.ingresarEstado(any())).thenReturn(null);

        ResponseEntity<Map<String, Object>> response = estadoServicioController.crearCategoria(estadoServicio);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertFalse((Boolean) response.getBody().get("ok"));
        assertEquals("error en su creacions", response.getBody().get("mensaje"));

        verify(estadoServicioService, times(1)).ingresarEstado(any());
    }

    @Test
    void testCrearEstadoServicio_Exception() {
        EstadoServicio estadoServicio = new EstadoServicio();
        estadoServicio.setEstado("Error");

        when(estadoServicioService.ingresarEstado(any())).thenThrow(new RuntimeException("Error de prueba"));

        ResponseEntity<Map<String, Object>> response = estadoServicioController.crearCategoria(estadoServicio);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertFalse((Boolean) response.getBody().get("ok"));
        assertEquals("Error al guardar el estado", response.getBody().get("mensaje"));
        assertEquals("Error de prueba", response.getBody().get("error"));

        verify(estadoServicioService, times(1)).ingresarEstado(any());
    }

    @Test
    void testObtenerEstadosServicios_Success() {
        EstadoServicio estadoServicio1 = new EstadoServicio();
        estadoServicio1.setEstado("Activo");

        EstadoServicio estadoServicio2 = new EstadoServicio();
        estadoServicio2.setEstado("Inactivo");

        List<EstadoServicio> estadosServicios = Arrays.asList(estadoServicio1, estadoServicio2);

        when(estadoServicioService.obtenerTodo()).thenReturn(estadosServicios);

        ResponseEntity<List<EstadoServicio>> response = estadoServicioController.obtenerEstadosServicios();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(estadosServicios, response.getBody());

        verify(estadoServicioService, times(1)).obtenerTodo();
    }
}
