package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.models.EstadoCita;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.EstadoCitaServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class EstadoCitaControllerTest {

    @Mock
    private EstadoCitaServiceImpl estadoCitaServiceImpl;

    @InjectMocks
    private EstadoCitaController estadoCitaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearCategoria_Success() {
        EstadoCita estadoCita = new EstadoCita();
        estadoCita.setNombre("Estado de prueba");

        ResponseBackend respuesta = new ResponseBackend(true, HttpStatus.CREATED, "EstadoCita registrado correctamente");
        when(estadoCitaServiceImpl.registrarEstadoCita(any(EstadoCita.class))).thenReturn(respuesta);

        ResponseEntity<Map<String, Object>> response = estadoCitaController.crearCategoria(estadoCita);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue((Boolean) response.getBody().get("ok"));
        assertEquals("EstadoCita registrado correctamente", response.getBody().get("mensaje"));
    }

    @Test
    void testCrearCategoria_Error() {
        EstadoCita estadoCita = new EstadoCita();
        estadoCita.setNombre("Estado de prueba");

        ResponseBackend respuesta = new ResponseBackend(false, HttpStatus.INTERNAL_SERVER_ERROR, "Error al registrar EstadoCita");
        when(estadoCitaServiceImpl.registrarEstadoCita(any(EstadoCita.class))).thenReturn(respuesta);

        ResponseEntity<Map<String, Object>> response = estadoCitaController.crearCategoria(estadoCita);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertFalse((Boolean) response.getBody().get("ok"));
        assertEquals("Error al registrar EstadoCita", response.getBody().get("mensaje"));
    }

    @Test
    void testObtenerEstadosCitas() {
        EstadoCita estadoCita1 = new EstadoCita();
        estadoCita1.setNombre("Estado 1");
        EstadoCita estadoCita2 = new EstadoCita();
        estadoCita2.setNombre("Estado 2");

        when(estadoCitaServiceImpl.obtenerTodo()).thenReturn(List.of(estadoCita1, estadoCita2));

        ResponseEntity<Map<String, Object>> response = estadoCitaController.obtenerEstadosCitas();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody().get("ok"));
        assertEquals(2, ((List<?>) response.getBody().get("Estados")).size());
    }
}
