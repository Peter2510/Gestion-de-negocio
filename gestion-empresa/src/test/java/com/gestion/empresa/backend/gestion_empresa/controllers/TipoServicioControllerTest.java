package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.models.Rol;
import com.gestion.empresa.backend.gestion_empresa.models.TipoServicio;
import com.gestion.empresa.backend.gestion_empresa.repositories.RolRepository;
import com.gestion.empresa.backend.gestion_empresa.repositories.TipoServicioRepository;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.RolServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.TipoServicioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TipoServicioControllerTest {

    @Mock
    private TipoServicioRepository tipoServicioRepository;

    @Mock
    private TipoServicioServiceImpl tipoServicioService;

    @InjectMocks
    private TipoServicioController tipoServicioController;

    private TipoServicio tipoServicio;

    @BeforeEach
    void setUp() {
        //crear un objeto de TipoServicio para los tests
        tipoServicio = new TipoServicio();
        tipoServicio.setId(1L);
        tipoServicio.setNombre("Clinicas");
    }

    @Test
    void obtenerTiposServicioRegistrados_retornaListaDeTiposServicio() {
        List<TipoServicio> tiposServicio = List.of(
                tipoServicio,
                new TipoServicio(2L, "Hospitales"),
                new TipoServicio(3L, "Laboratorios"),
                new TipoServicio(4L, "Farmacias")
        );

        //simular que el servicio retorna la lista de tipos de servicio
        when(tipoServicioService.obtenerTipoServiciosRegistrados()).thenReturn(tiposServicio);

        //llamar al metodo del controlador
        ResponseEntity<Map<String, Object>> response = tipoServicioController.obtenerTiposServicioRegistrados();

        //verificar el controlador
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().containsKey("ok"));
        assertTrue(response.getBody().containsKey("tiposServicio"));
        assertEquals(Map.of("ok", true, "tiposServicio", tiposServicio), response.getBody());

        List<TipoServicio> tiposServicioResponse = (List<TipoServicio>) response.getBody().get("tiposServicio");
        assertEquals(4, tiposServicioResponse.size());
        assertEquals("Clinicas", tiposServicioResponse.get(0).getNombre());
        assertEquals("Hospitales", tiposServicioResponse.get(1).getNombre());

        //verificar que el servicio fue llamado
        verify(tipoServicioService, times(1)).obtenerTipoServiciosRegistrados();

    }

    @Test
    void crearTipoServicio_retornaTipoServicioCreado() {
        //simular que el tipo de servicio no existe
        when(tipoServicioService.buscarPorNombre(tipoServicio.getNombre())).thenReturn(java.util.Optional.empty());
        when(tipoServicioService.crearTipoServicio(tipoServicio)).thenReturn(tipoServicio);

        //llamar al metodo del controlador
        ResponseEntity<Map<String, Object>> response = tipoServicioController.crearTipoServicio(tipoServicio);

        //verificar el controlador
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().containsKey("ok"));
        assertTrue(response.getBody().containsKey("mensaje"));
        assertEquals(Map.of("ok", true, "mensaje", "Tipo de servicio creado exitosamente"), response.getBody());

        //verificar que el servicio fue llamado
        verify(tipoServicioService, times(1)).buscarPorNombre(tipoServicio.getNombre());
        verify(tipoServicioService, times(1)).crearTipoServicio(tipoServicio);
    }

    @Test
    void crearTipoServicio_retornaTipoServicioExistente() {
        //simular que el tipo de servicio ya existe
        when(tipoServicioService.buscarPorNombre(tipoServicio.getNombre())).thenReturn(java.util.Optional.of(tipoServicio));

        //llamar al metodo del controlador
        ResponseEntity<Map<String, Object>> response = tipoServicioController.crearTipoServicio(tipoServicio);

        //verificar el controlador
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertTrue(response.getBody().containsKey("ok"));
        assertTrue(response.getBody().containsKey("mensaje"));
        assertEquals(Map.of("ok", false, "mensaje", "El tipo de servicio " + tipoServicio.getNombre() + " ya existe"), response.getBody());

        //verificar que el servicio fue llamado
        verify(tipoServicioService, times(1)).buscarPorNombre(tipoServicio.getNombre());
        verify(tipoServicioService, never()).crearTipoServicio(tipoServicio);
    }

    @Test
    void crearTipoServicio_retornaErrorAlCrearTipoServicio() {
        //simular que el tipo de servicio no existe
        when(tipoServicioService.buscarPorNombre(tipoServicio.getNombre())).thenReturn(java.util.Optional.empty());
        when(tipoServicioService.crearTipoServicio(tipoServicio)).thenReturn(null);

        //llamar al metodo del controlador
        ResponseEntity<Map<String, Object>> response = tipoServicioController.crearTipoServicio(tipoServicio);

        //verificar el controlador
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().containsKey("ok"));
        assertTrue(response.getBody().containsKey("mensaje"));
        assertEquals(Map.of("ok", false, "mensaje", "No se pudo crear el tipo de servicio"), response.getBody());

        //verificar que el servicio fue llamado
        verify(tipoServicioService, times(1)).buscarPorNombre(tipoServicio.getNombre());
        verify(tipoServicioService, times(1)).crearTipoServicio(tipoServicio);
    }


}