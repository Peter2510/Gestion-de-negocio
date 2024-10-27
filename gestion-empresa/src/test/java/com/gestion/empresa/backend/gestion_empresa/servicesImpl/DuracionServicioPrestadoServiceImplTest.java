package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.repositories.DuracionServicioPrestadoRepository;
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
class DuracionServicioPrestadoServiceImplTest {

    @InjectMocks
    private DuracionServicioPrestadoServiceImpl duracionServicioPrestadoService;

    @Mock
    private DuracionServicioPrestadoRepository duracionServicioPrestadoRepository;

    private Long idServicio;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        idServicio = 1L;
    }

    @Test
    public void testObtenerTodosServicios() {
        List<Object[]> servicios = new ArrayList<>();
        Object[] servicio = new Object[]{"Servicio 1", 60};
        servicios.add(servicio);

        when(duracionServicioPrestadoRepository.findByServicio(idServicio)).thenReturn(servicios);

        List<Object[]> result = duracionServicioPrestadoService.obtenerTodosServicios(idServicio);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Servicio 1", result.get(0)[0]);
        assertEquals(60, result.get(0)[1]);
    }
}
