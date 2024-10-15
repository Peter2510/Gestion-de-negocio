package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.models.JornadaLaboral;
import com.gestion.empresa.backend.gestion_empresa.services.JornadaLaboralService;
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

import java.time.LocalTime;
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
public class JornadaLaboralControllerTest {

    @Mock
    private JornadaLaboralService jornadaLaboralService;

    @InjectMocks
    private JornadaLaboralController jornadaLaboralController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearJornada_Success() {
        // Datos de prueba
        JornadaLaboral jornadaLaboral = new JornadaLaboral();
        jornadaLaboral.setNombre("Jornada Matutina");
        jornadaLaboral.setHoraInicio(LocalTime.of(8, 0));
        jornadaLaboral.setHoraFin(LocalTime.of(16, 0));

        JornadaLaboral nuevaJornada = new JornadaLaboral(1L, "Jornada Matutina", LocalTime.of(8, 0), LocalTime.of(16, 0));

        when(jornadaLaboralService.ingresarJornada(any(JornadaLaboral.class))).thenReturn(nuevaJornada);

        // Ejecutar el método
        ResponseEntity<Map<String, Object>> response = jornadaLaboralController.crearJornada(jornadaLaboral);

        // Verificar resultados
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(true, response.getBody().get("ok"));
        assertEquals("jornada laboral creada correctamente", response.getBody().get("mensaje"));
    }

    @Test
    void testCrearJornada_ErrorAlGuardar() {
        // Datos de prueba
        JornadaLaboral jornadaLaboral = new JornadaLaboral();
        jornadaLaboral.setNombre("Jornada Matutina");
        jornadaLaboral.setHoraInicio(LocalTime.of(8, 0));
        jornadaLaboral.setHoraFin(LocalTime.of(16, 0));

        when(jornadaLaboralService.ingresarJornada(any(JornadaLaboral.class))).thenThrow(new RuntimeException("Error al guardar"));

        // Ejecutar el método
        ResponseEntity<Map<String, Object>> response = jornadaLaboralController.crearJornada(jornadaLaboral);

        // Verificar resultados
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(false, response.getBody().get("ok"));
        assertEquals("Error al guardar la jornada laboral", response.getBody().get("mensaje"));
        assertEquals("Error al guardar", response.getBody().get("error"));
    }

    @Test
    void testCrearJornada_ErrorEnCreacion() {
        // Datos de prueba
        JornadaLaboral jornadaLaboral = new JornadaLaboral();
        jornadaLaboral.setNombre("Jornada Matutina");
        jornadaLaboral.setHoraInicio(LocalTime.of(8, 0));
        jornadaLaboral.setHoraFin(LocalTime.of(16, 0));

        when(jornadaLaboralService.ingresarJornada(any(JornadaLaboral.class))).thenReturn(null);

        // Ejecutar el método
        ResponseEntity<Map<String, Object>> response = jornadaLaboralController.crearJornada(jornadaLaboral);

        // Verificar resultados
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(false, response.getBody().get("ok"));
        assertEquals("error en su creacions", response.getBody().get("mensaje"));
    }
}
