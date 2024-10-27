package com.gestion.empresa.backend.gestion_empresa.controllers;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.gestion.empresa.backend.gestion_empresa.services.JornadaServicioService;
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
class JornadaServicioControllerTest {

    @InjectMocks
    private JornadaServicioController jornadaServicioController;

    @Mock
    private JornadaServicioService jornadaServicioService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testObtenerTodasJornadasEspecificos_Success() {
        Long idServicio = 1L;
        List<Object[]> jornadas = Arrays.asList(
                new Object[]{"Jornada 1", "Detalle 1"},
                new Object[]{"Jornada 2", "Detalle 2"}
        );

        when(jornadaServicioService.obtenerTodasJornadas(idServicio)).thenReturn(jornadas);

        ResponseEntity<Map<String, Object>> response = jornadaServicioController.obtenerTodasJornadasEspecificos(idServicio);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody().get("ok"));
        assertEquals(jornadas, response.getBody().get("todoServicios"));
    }

    @Test
    void testObtenerTodasJornadasEspecificos_NoJornadas() {
        Long idServicio = 1L;
        List<Object[]> jornadas = Arrays.asList();

        when(jornadaServicioService.obtenerTodasJornadas(idServicio)).thenReturn(jornadas);

        ResponseEntity<Map<String, Object>> response = jornadaServicioController.obtenerTodasJornadasEspecificos(idServicio);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody().get("ok"));
        assertEquals(jornadas, response.getBody().get("todoServicios"));
    }
}
