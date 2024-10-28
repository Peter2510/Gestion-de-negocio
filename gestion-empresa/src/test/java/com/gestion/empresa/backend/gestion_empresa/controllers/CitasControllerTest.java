package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.dto.RegistroCitasDTO;
import com.gestion.empresa.backend.gestion_empresa.models.Citas;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.CitasServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
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

class CitasControllerTest {

    @Mock
    private CitasServiceImpl citasServiceImpl;

    @InjectMocks
    private CitasController citasController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearCita_Success() {
        RegistroCitasDTO registroCitasDTO = new RegistroCitasDTO();
        // Configura el objeto DTO según sea necesario

        ResponseBackend respuesta = new ResponseBackend(true, HttpStatus.CREATED, "Cita registrada correctamente");
        when(citasServiceImpl.registrarCitas(any(RegistroCitasDTO.class))).thenReturn(respuesta);

        ResponseEntity<Map<String, Object>> response = citasController.crearCategoria(registroCitasDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue((Boolean) response.getBody().get("ok"));
        assertEquals("Cita registrada correctamente", response.getBody().get("mensaje"));
    }

    @Test
    void testCrearCita_Error() {
        RegistroCitasDTO registroCitasDTO = new RegistroCitasDTO();
        // Configura el objeto DTO según sea necesario

        ResponseBackend respuesta = new ResponseBackend(false, HttpStatus.INTERNAL_SERVER_ERROR, "Error al registrar cita");
        when(citasServiceImpl.registrarCitas(any(RegistroCitasDTO.class))).thenReturn(respuesta);

        ResponseEntity<Map<String, Object>> response = citasController.crearCategoria(registroCitasDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertFalse((Boolean) response.getBody().get("ok"));
        assertEquals("Error al registrar cita", response.getBody().get("mensaje"));
    }

    @Test
    void testObtenerEstadosCitas() {
        Citas cita1 = new Citas();
        Citas cita2 = new Citas();
        when(citasServiceImpl.obtenerTodasCitas()).thenReturn(List.of(cita1, cita2));

        ResponseEntity<Map<String, Object>> response = citasController.obtenerEstadosCitas();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody().get("ok"));
        assertEquals(2, ((List<?>) response.getBody().get("Citas")).size());
    }

    @Test
    void testObtenerCitasEspecificas() {
        Long id = 1L;
        Citas cita1 = new Citas();
        when(citasServiceImpl.obtenerCitasId(id)).thenReturn(List.of(cita1));

        ResponseEntity<Map<String, Object>> response = citasController.obtenerCitasEspecificas(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody().get("ok"));
        assertEquals(1, ((List<?>) response.getBody().get("todoServiciosEspecificos")).size());
    }
}
