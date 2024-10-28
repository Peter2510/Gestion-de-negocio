package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.models.JornadaPorDia;
import com.gestion.empresa.backend.gestion_empresa.services.JornadaPorDiaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class JornadaPorDiaControllerTest {

    @InjectMocks
    private JornadaPorDiaController jornadaPorDiaController;

    @Mock
    private JornadaPorDiaService jornadaPorDiaService;

    private JornadaPorDia jornadaPorDia;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        jornadaPorDia = new JornadaPorDia();
    }

    @Test
    public void testAsignarJornada_CreationSuccess() {
        when(jornadaPorDiaService.ingresarJornadaDia(jornadaPorDia)).thenReturn(jornadaPorDia);

        ResponseEntity<Map<String, Object>> response = jornadaPorDiaController.asignarJornada(jornadaPorDia);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(true, response.getBody().get("ok"));
        assertEquals("jornada laboral creada correctamente", response.getBody().get("mensaje"));

        verify(jornadaPorDiaService).ingresarJornadaDia(jornadaPorDia);
    }

    @Test
    public void testAsignarJornada_CreationFailure() {
        when(jornadaPorDiaService.ingresarJornadaDia(jornadaPorDia)).thenReturn(null);

        ResponseEntity<Map<String, Object>> response = jornadaPorDiaController.asignarJornada(jornadaPorDia);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(false, response.getBody().get("ok"));
        assertEquals("error en su creacions", response.getBody().get("mensaje"));

        verify(jornadaPorDiaService).ingresarJornadaDia(jornadaPorDia);
    }

    @Test
    public void testAsignarJornada_ExceptionHandling() {
        when(jornadaPorDiaService.ingresarJornadaDia(jornadaPorDia)).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<Map<String, Object>> response = jornadaPorDiaController.asignarJornada(jornadaPorDia);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(false, response.getBody().get("ok"));
        assertEquals("Error al guardar la jornada laboral", response.getBody().get("mensaje"));
        assertEquals("Database error", response.getBody().get("error"));

        verify(jornadaPorDiaService).ingresarJornadaDia(jornadaPorDia);
    }
}
