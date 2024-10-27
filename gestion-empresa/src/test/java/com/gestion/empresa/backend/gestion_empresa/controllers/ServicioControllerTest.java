package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.dto.DevolverTodoServiciosDTO;
import com.gestion.empresa.backend.gestion_empresa.dto.NuevoServicioDTO;
import com.gestion.empresa.backend.gestion_empresa.models.Servicios;
import com.gestion.empresa.backend.gestion_empresa.services.ServiciosService;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.ServiciosServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ServicioControllerTest {

    @InjectMocks
    private ServicioController servicioController;

    @Mock
    private ServiciosService serviciosService;

    @Mock
    private ServiciosServiceImpl serviciosServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreacionServicioSuccess() {
        Servicios nuevoServicio = new Servicios();
        nuevoServicio.setNombre("Servicio 1");
        nuevoServicio.setDescripcion("Descripción del servicio 1");

        when(serviciosService.crearServicio(any(Servicios.class))).thenReturn(nuevoServicio);

        ResponseEntity<Map<String, Object>> response = servicioController.creacionServicio(nuevoServicio);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(true, response.getBody().get("ok"));
        assertEquals("Servicio creado exitosamente", response.getBody().get("mensaje"));
    }

    @Test
    void testCreacionServicioFailure() {
        Servicios nuevoServicio = new Servicios();
        nuevoServicio.setNombre("Servicio 1");
        nuevoServicio.setDescripcion("Descripción del servicio 1");

        when(serviciosService.crearServicio(any(Servicios.class))).thenReturn(null);

        ResponseEntity<Map<String, Object>> response = servicioController.creacionServicio(nuevoServicio);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(false, response.getBody().get("ok"));
        assertEquals("Error en la creacion del servicio", response.getBody().get("mensaje"));
    }

    @Test
    void testCreacionServicioException() {
        Servicios nuevoServicio = new Servicios();
        nuevoServicio.setNombre("Servicio 1");
        nuevoServicio.setDescripcion("Descripción del servicio 1");

        when(serviciosService.crearServicio(any(Servicios.class))).thenThrow(new RuntimeException("Error inesperado"));

        ResponseEntity<Map<String, Object>> response = servicioController.creacionServicio(nuevoServicio);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(false, response.getBody().get("ok"));
        assertEquals("Error al guardar la jornada laboral", response.getBody().get("mensaje"));
        assertEquals("Error inesperado", response.getBody().get("error"));
    }

    @Test
    void testCreateOrUpdateServicio() {
        NuevoServicioDTO nuevoServicioDTO = new NuevoServicioDTO();
        ResponseBackend responseBackend = new ResponseBackend(true, HttpStatus.CREATED, "Servicio registrado exitosamente");

        when(serviciosServiceImpl.registroServicio(any(NuevoServicioDTO.class))).thenReturn(responseBackend);

        ResponseEntity<Map<String, Object>> response = servicioController.createOrUpdateServicio(nuevoServicioDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Servicio registrado exitosamente", response.getBody().get("mensaje"));
        assertEquals(true, response.getBody().get("ok"));
    }

    @Test
    void testObtenerServiciosGenerales() {
        DevolverTodoServiciosDTO devolverTodoServiciosDTO = new DevolverTodoServiciosDTO();
        when(serviciosServiceImpl.obtenerServicios()).thenReturn(devolverTodoServiciosDTO);

        ResponseEntity<Map<String, Object>> response = servicioController.obtenerServicios();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody().get("ok"));
        assertEquals(devolverTodoServiciosDTO, response.getBody().get("todoServicios"));
    }

    @Test
    void testObtenerTodosServicios() {
        when(serviciosServiceImpl.obtenerTodosServicios()).thenReturn(List.of(new Servicios()));

        ResponseEntity<Map<String, Object>> response = servicioController.obtenerTodosServicios();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody().get("ok"));
        assertEquals(List.of(new Servicios()), response.getBody().get("todoServicios"));
    }

    @Test
    void testObtenerTodosServiciosEspecificos() {
        Long id = 1L; // ID de ejemplo
        List<Servicios> serviciosEspecificos = List.of(new Servicios()); // Inicializa con un servicio de ejemplo

        // Simulamos la respuesta del servicio
        when(serviciosServiceImpl.obtenerTodosServicios()).thenReturn(serviciosEspecificos);

        // Llamamos al método del controlador
        ResponseEntity<Map<String, Object>> response = servicioController.obtenerTodosServiciosEspecificos();

        // Verificamos el resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody().get("ok"));
        assertEquals(serviciosEspecificos, response.getBody().get("todoServicios"));
    }


}
