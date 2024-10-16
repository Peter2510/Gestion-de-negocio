package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.models.DiasLaborales;
import com.gestion.empresa.backend.gestion_empresa.services.DiasLaboralesService;
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
public class DiasLaboralesControllerTest {

    @InjectMocks
    private DiasLaboralesController diasLaboralesController;

    @Mock
    private DiasLaboralesService diasLaboralesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearDiaExito() {
        DiasLaborales mockDia = new DiasLaborales();
        mockDia.setNombre("Lunes");

        when(diasLaboralesService.ingresarDias(any(DiasLaborales.class)))
                .thenReturn(mockDia);

        ResponseEntity<Map<String, Object>> response = diasLaboralesController.crearDia(mockDia);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(true, response.getBody().get("ok"));
        assertEquals("Dia laboral creado correctamente", response.getBody().get("mensaje"));
    }

    @Test
    void testCrearDiaError() {
        when(diasLaboralesService.ingresarDias(any(DiasLaborales.class)))
                .thenThrow(new RuntimeException("Error de prueba"));

        DiasLaborales mockDia = new DiasLaborales();
        mockDia.setNombre("Lunes");

        ResponseEntity<Map<String, Object>> response = diasLaboralesController.crearDia(mockDia);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(false, response.getBody().get("ok"));
        assertEquals("Error al guardar el Dia laboral", response.getBody().get("mensaje"));
        assertEquals("Error de prueba", response.getBody().get("error"));
    }

    @Test
    void testObtenerDiasExito() {
        List<DiasLaborales> mockDias = new ArrayList<>();
        DiasLaborales dia1 = new DiasLaborales();
        dia1.setNombre("Lunes");
        DiasLaborales dia2 = new DiasLaborales();
        dia2.setNombre("Martes");
        mockDias.add(dia1);
        mockDias.add(dia2);

        when(diasLaboralesService.obtenerTodosDias()).thenReturn(mockDias);

        ResponseEntity<List<DiasLaborales>> response = diasLaboralesController.obtenerDias();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testObtenerDiaEspecificoExito() {
        List<DiasLaborales> mockDias = new ArrayList<>();
        DiasLaborales dia1 = new DiasLaborales();
        dia1.setNombre("Miércoles");
        mockDias.add(dia1);

        when(diasLaboralesService.obtenerTodosDias()).thenReturn(mockDias);

        ResponseEntity<List<DiasLaborales>> response = diasLaboralesController.obtenerDiasEspecifico();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Miércoles", response.getBody().get(0).getNombre());
    }
}
