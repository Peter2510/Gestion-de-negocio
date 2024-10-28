package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.services.DuracionServicioPrestadoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DuracionServicioPrestadoControllerTest {

    @Mock
    private DuracionServicioPrestadoService duracionServicioPrestadoService;

    @InjectMocks
    private DuracionServicioPrestadoController duracionServicioPrestadoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTodosServiciosEspecificos_Success() {
        Long idServicio = 1L;
        List<Object[]> servicios = List.of(new Object[]{"Servicio 1", 30}, new Object[]{"Servicio 2", 60});
        when(duracionServicioPrestadoService.obtenerTodosServicios(idServicio)).thenReturn(servicios);

        ResponseEntity<Map<String, Object>> response = duracionServicioPrestadoController.obtenerTodosServiciosEspecificos(idServicio);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody().get("ok"));
        assertEquals(2, ((List<?>) response.getBody().get("todoServicios")).size());
    }

    @Test
    void testObtenerTodosServiciosEspecificos_EmptyList() {
        Long idServicio = 1L;
        when(duracionServicioPrestadoService.obtenerTodosServicios(idServicio)).thenReturn(List.of());

        ResponseEntity<Map<String, Object>> response = duracionServicioPrestadoController.obtenerTodosServiciosEspecificos(idServicio);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody().get("ok"));
        assertEquals(0, ((List<?>) response.getBody().get("todoServicios")).size());
    }
}
