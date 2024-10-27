package com.gestion.empresa.backend.gestion_empresa.servicesImpl;
import com.gestion.empresa.backend.gestion_empresa.models.EstadoCita;
import com.gestion.empresa.backend.gestion_empresa.repositories.EstadoCitaRepository;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.EstadoCitaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class EstadoServicioServiceImplTest {
    @Mock
    private EstadoCitaRepository estadoCitaRepository;

    @InjectMocks
    private EstadoCitaServiceImpl estadoCitaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerTodo() {
        EstadoCita estado1 = new EstadoCita();
        EstadoCita estado2 = new EstadoCita();
        List<EstadoCita> estados = new ArrayList<>();
        estados.add(estado1);
        estados.add(estado2);

        when(estadoCitaRepository.findAll()).thenReturn(estados);

        List<EstadoCita> result = estadoCitaService.obtenerTodo();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(estadoCitaRepository, times(1)).findAll();
    }

    @Test
    public void testIngresarEstadoCita() {
        EstadoCita estadoCita = new EstadoCita();
        when(estadoCitaRepository.save(estadoCita)).thenReturn(estadoCita);

        EstadoCita result = estadoCitaService.ingresarEstadoCita(estadoCita);

        assertNotNull(result);
        verify(estadoCitaRepository, times(1)).save(estadoCita);
    }

    @Test
    public void testRegistrarEstadoCita_Success() {
        EstadoCita estadoCita = new EstadoCita();
        when(estadoCitaRepository.save(estadoCita)).thenReturn(estadoCita);

        ResponseBackend response = estadoCitaService.registrarEstadoCita(estadoCita);

        assertNotNull(response);
        assertTrue(response.getOk());
        assertEquals(HttpStatus.CREATED, response.getStatus());
        assertEquals("Categoria registrada correctamente", response.getMensaje());
    }

    @Test
    public void testRegistrarEstadoCita_Error() {
        EstadoCita estadoCita = new EstadoCita();
        when(estadoCitaRepository.save(estadoCita)).thenReturn(null);

        ResponseBackend response = estadoCitaService.registrarEstadoCita(estadoCita);

        assertNotNull(response);
        assertFalse(response.getOk());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        assertEquals("Error al crear la categoria", response.getMensaje());
    }
}