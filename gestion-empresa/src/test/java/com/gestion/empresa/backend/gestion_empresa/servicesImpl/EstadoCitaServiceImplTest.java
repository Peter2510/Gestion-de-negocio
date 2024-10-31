package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.models.EstadoCita;
import com.gestion.empresa.backend.gestion_empresa.repositories.EstadoCitaRepository;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
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

public class EstadoCitaServiceImplTest {

    @InjectMocks
    private EstadoCitaServiceImpl estadoCitaService;

    @Mock
    private EstadoCitaRepository estadoCitaRepository;

    private EstadoCita estadoCita;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        estadoCita = new EstadoCita(1L, "Estado Activo"); // Ajusta los parámetros según tu modelo
    }

    @Test
    public void testObtenerTodo() {
        List<EstadoCita> listaEstados = new ArrayList<>();
        listaEstados.add(estadoCita);

        when(estadoCitaRepository.findAll()).thenReturn(listaEstados);

        List<EstadoCita> result = estadoCitaService.obtenerTodo();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Estado Activo", result.get(0).getNombre());
        verify(estadoCitaRepository, times(1)).findAll();
    }

    @Test
    public void testIngresarEstadoCita() {
        when(estadoCitaRepository.save(any(EstadoCita.class))).thenReturn(estadoCita);

        EstadoCita result = estadoCitaService.ingresarEstadoCita(estadoCita);

        assertNotNull(result);
        assertEquals("Estado Activo", result.getNombre());
        verify(estadoCitaRepository, times(1)).save(estadoCita);
    }

    @Test
    public void testRegistrarEstadoCita() {
        when(estadoCitaRepository.save(any(EstadoCita.class))).thenReturn(estadoCita);

        ResponseBackend response = estadoCitaService.registrarEstadoCita(estadoCita);

        assertTrue(response.getOk());
        assertEquals(HttpStatus.CREATED, response.getStatus());
        assertEquals("Categoria registrada correctamente", response.getMensaje());
    }

    @Test
    public void testRegistrarEstadoCita_FalloEnCreacion() {
        when(estadoCitaRepository.save(any(EstadoCita.class))).thenReturn(null);

        ResponseBackend response = estadoCitaService.registrarEstadoCita(estadoCita);

        assertFalse(response.getOk());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        assertEquals("Error al crear la categoria", response.getMensaje());
    }
}
