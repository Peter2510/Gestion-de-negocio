package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.models.Notificacion;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.BuzonServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.NotificacionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuzonControllerTest {

    @InjectMocks
    private BuzonController buzonController;

    @Mock
    private BuzonServiceImpl buzonService;

    @Mock
    private NotificacionServiceImpl notificacionService;

    private Notificacion notificacion;
    private List<Notificacion> notificaciones;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        notificacion = new Notificacion();
        notificacion.setId(1L);
        notificacion.setLeido(false);

        notificaciones = new ArrayList<>();
        notificaciones.add(notificacion);
    }

    @Test
    void testObtenerBuzonUsuario() {
        Long idUsuario = 1L;

        when(buzonService.buscarBuzonesPorUsuario(idUsuario)).thenReturn(notificaciones);

        ResponseEntity<Map<String, Object>> response = buzonController.obtenerBuzonUsuario(idUsuario);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody().get("ok"));
        assertEquals(notificaciones, response.getBody().get("notificaciones"));
    }

    @Test
    void testActualizarMensajeNotificacionEncontrada() {
        Long idNotificacion = 1L;

        when(notificacionService.buscarNotificacionPorId(idNotificacion)).thenReturn(Optional.of(notificacion));

        ResponseEntity<Map<String, Object>> response = buzonController.actualizarMensaje(idNotificacion);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody().get("ok"));
        assertEquals("Notificación actualizada correctamente", response.getBody().get("mensaje"));

        assertEquals(true, notificacion.isLeido());
        verify(notificacionService, times(1)).actualizarNotificacion(notificacion);
    }

    @Test
    void testActualizarMensajeNotificacionNoEncontrada() {
        Long idNotificacion = 1L;

        when(notificacionService.buscarNotificacionPorId(idNotificacion)).thenReturn(Optional.empty());

        ResponseEntity<Map<String, Object>> response = buzonController.actualizarMensaje(idNotificacion);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(false, response.getBody().get("ok"));
        assertEquals("Notificación no encontrada", response.getBody().get("mensaje"));
    }
}