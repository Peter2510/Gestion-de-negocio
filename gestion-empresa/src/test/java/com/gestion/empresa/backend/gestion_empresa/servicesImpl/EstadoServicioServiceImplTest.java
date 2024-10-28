package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.models.EstadoServicio;
import com.gestion.empresa.backend.gestion_empresa.repositories.EstadoServicioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstadoServicioServiceImplTest {

    @Mock
    private EstadoServicioRepository estadoServicioRepository;

    @InjectMocks
    private EstadoServicioServiceImpl estadoServicioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTodo() {
        EstadoServicio estado1 = new EstadoServicio();
        EstadoServicio estado2 = new EstadoServicio();
        List<EstadoServicio> estados = new ArrayList<>();
        estados.add(estado1);
        estados.add(estado2);

        when(estadoServicioRepository.findAll()).thenReturn(estados);

        List<EstadoServicio> result = estadoServicioService.obtenerTodo();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(estadoServicioRepository, times(1)).findAll();
    }

    @Test
    void testIngresarEstado() {
        EstadoServicio estadoServicio = new EstadoServicio();
        when(estadoServicioRepository.save(estadoServicio)).thenReturn(estadoServicio);

        EstadoServicio result = estadoServicioService.ingresarEstado(estadoServicio);

        assertNotNull(result);
        assertEquals(estadoServicio, result);
        verify(estadoServicioRepository, times(1)).save(estadoServicio);
    }
}
