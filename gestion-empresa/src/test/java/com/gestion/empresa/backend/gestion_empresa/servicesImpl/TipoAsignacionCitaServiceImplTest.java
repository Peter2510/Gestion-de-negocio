package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.models.TipoAsignacionCita;
import com.gestion.empresa.backend.gestion_empresa.repositories.TipoAsignacionCitaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TipoAsignacionCitaServiceImplTest {

    @Mock
    private TipoAsignacionCitaRepository tipoAsignacionCitaRepository;

    @InjectMocks
    private TipoAsignacionCitaServiceImpl tipoAsignacionCitaService;

    private TipoAsignacionCita tipoAsignacionCita;

    @BeforeEach
    void setUp() {
        tipoAsignacionCita = new TipoAsignacionCita();
        tipoAsignacionCita.setId(1L);
        tipoAsignacionCita.setTipo("Aleatorio");
        tipoAsignacionCita.setActivo(true);
    }

    @Test
    void buscarPorNombreExistenteExiste(){
        when(tipoAsignacionCitaRepository.findByTipo(tipoAsignacionCita.getTipo())).thenReturn(Optional.of(tipoAsignacionCita));
        Optional<TipoAsignacionCita> busqueda = tipoAsignacionCitaService.buscarPorNombre(tipoAsignacionCita.getTipo());
        assertTrue(busqueda.isPresent(), "El tipo asignacion cita debería estar presente");
        busqueda.ifPresent(asignacionCita -> assertEquals("Aleatorio", asignacionCita.getTipo()));
        verify(tipoAsignacionCitaRepository).findByTipo(tipoAsignacionCita.getTipo());
    }

    @Test
    void buscarPorNombreExistenteNoExiste(){
        when(tipoAsignacionCitaRepository.findByTipo(tipoAsignacionCita.getTipo())).thenReturn(Optional.empty());
        Optional<TipoAsignacionCita> busqueda = tipoAsignacionCitaService.buscarPorNombre(tipoAsignacionCita.getTipo());
        assertTrue(busqueda.isEmpty(), "El tipo asignacion cita no debería estar presente");
        verify(tipoAsignacionCitaRepository).findByTipo(tipoAsignacionCita.getTipo());
    }

    @Test
    void crearTipoAsignacionCita(){
        when(tipoAsignacionCitaRepository.save(tipoAsignacionCita)).thenReturn(tipoAsignacionCita);
        TipoAsignacionCita guardado = tipoAsignacionCitaService.crearTipoAsignacionCita(tipoAsignacionCita);
        assertEquals("Aleatorio", guardado.getTipo());
        verify(tipoAsignacionCitaRepository).save(tipoAsignacionCita);
    }

    @Test
    void obtenerTipoAsignacionRegistradosLleno(){
        when(tipoAsignacionCitaRepository.findAll()).thenReturn(List.of(tipoAsignacionCita));

        List<TipoAsignacionCita> guardados = tipoAsignacionCitaService.obtenerTipoAsignacionCitaRegistrados();
        assertEquals(1, guardados.size());
        assertEquals("Aleatorio", guardados.get(0).getTipo());
        verify(tipoAsignacionCitaRepository, times(1)).findAll();
    }

    @Test
    void obtenerTipoAsignacionRegistradosVacio(){
        when(tipoAsignacionCitaRepository.findAll()).thenReturn(List.of());
        List<TipoAsignacionCita> guardados = tipoAsignacionCitaService.obtenerTipoAsignacionCitaRegistrados();
        assertEquals(0, guardados.size());
        verify(tipoAsignacionCitaRepository, times(1)).findAll();
    }

    @Test
    void buscarTipoAsignacionCitaPorIdNoRegistrado(){
        when(tipoAsignacionCitaRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<TipoAsignacionCita> busqueda = tipoAsignacionCitaService.buscarPorId(1L);
        assertTrue(busqueda.isEmpty());
        verify(tipoAsignacionCitaRepository, times(1)).findById(1L);
    }

    @Test
    void buscarTipoAsignacionCitaPorIdRegistrado(){
        when(tipoAsignacionCitaRepository.findById(1L)).thenReturn(Optional.of(tipoAsignacionCita));
        Optional<TipoAsignacionCita> busqueda = tipoAsignacionCitaService.buscarPorId(1L);
        assertFalse(busqueda.isEmpty());
        verify(tipoAsignacionCitaRepository, times(1)).findById(1L);
    }

}