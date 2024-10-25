package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.models.Notificacion;
import com.gestion.empresa.backend.gestion_empresa.repositories.NotificacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificacionServiceImplTest {

    @Mock
    private NotificacionRepository notificacionRepository;

    @InjectMocks
    private NotificacionServiceImpl notificacionService;

    private Notificacion notificacion;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        notificacion = new Notificacion();
        notificacion.setId(1L);
        notificacion.setTitulo("Prueba");
        notificacion.setMensaje("Mensaje de prueba");
    }

    @Test
    void crearNotificacion() {
        when(notificacionRepository.save(notificacion)).thenReturn(notificacion);

        Notificacion resultado = notificacionService.crearNotificacion(notificacion);

        assertNotNull(resultado);
        assertEquals(notificacion.getTitulo(), resultado.getTitulo());
        verify(notificacionRepository, times(1)).save(notificacion);
    }

    @Test
    void actualizarNotificacion() {
        when(notificacionRepository.save(notificacion)).thenReturn(notificacion);

        Notificacion resultado = notificacionService.actualizarNotificacion(notificacion);

        assertNotNull(resultado);
        assertEquals(notificacion.getTitulo(), resultado.getTitulo());
        verify(notificacionRepository, times(1)).save(notificacion);
    }

    @Test
    void buscarNotificacionPorId_Existente() {
        when(notificacionRepository.findById(1L)).thenReturn(Optional.of(notificacion));

        Optional<Notificacion> resultado = notificacionService.buscarNotificacionPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(notificacion, resultado.get());
        verify(notificacionRepository, times(1)).findById(1L);
    }

    @Test
    void buscarNotificacionPorId_NoExistente() {
        when(notificacionRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Notificacion> resultado = notificacionService.buscarNotificacionPorId(1L);

        assertFalse(resultado.isPresent());
        verify(notificacionRepository, times(1)).findById(1L);
    }

}