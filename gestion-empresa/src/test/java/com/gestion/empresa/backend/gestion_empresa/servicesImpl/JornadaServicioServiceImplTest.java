package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.repositories.JornadaServicioRepository;
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
class JornadaServicioServiceImplTest {

    @InjectMocks
    private JornadaServicioServiceImpl jornadaServicioService;

    @Mock
    private JornadaServicioRepository jornadaServicioRepository;

    private Long idServicio;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        idServicio = 1L;
    }

    @Test
    public void testObtenerTodasJornadas() {
        List<Object[]> jornadas = new ArrayList<>();
        Object[] jornada = new Object[]{"Jornada 1", 8};
        jornadas.add(jornada);

        when(jornadaServicioRepository.findByServicio(idServicio)).thenReturn(jornadas);

        List<Object[]> result = jornadaServicioService.obtenerTodasJornadas(idServicio);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Jornada 1", result.get(0)[0]);
        assertEquals(8, result.get(0)[1]);
    }

}