package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.models.Rol;
import com.gestion.empresa.backend.gestion_empresa.models.TipoServicio;
import com.gestion.empresa.backend.gestion_empresa.repositories.RolRepository;
import com.gestion.empresa.backend.gestion_empresa.repositories.TipoServicioRepository;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TipoServicioServiceImplTest {

    @Mock
    private TipoServicioRepository tipoServicioRepository;

    @InjectMocks
    private TipoServicioServiceImpl tipoServicioService;

    private TipoServicio tipoServicio;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tipoServicio = new TipoServicio();
        tipoServicio.setId(1L);
        tipoServicio.setNombre("Clinicas");
    }

    @Test
    void buscarPorNombreExistente() {
        when(tipoServicioRepository.findByNombre("Clinicas")).thenReturn(Optional.of(tipoServicio));
        Optional<TipoServicio> resultado = tipoServicioService.buscarPorNombre("Clinicas");
        assertNotNull(resultado);
        assertEquals("Clinicas", resultado.isPresent() ? resultado.get().getNombre() : "");
        verify(tipoServicioRepository, times(1)).findByNombre("Clinicas");
    }

    @Test
    void buscarPorNombreNoExistente() {
        when(tipoServicioRepository.findByNombre("Hospitales")).thenReturn(Optional.empty());
        Optional<TipoServicio> resultado = tipoServicioService.buscarPorNombre("Hospitales");
        assertNotNull(resultado);
        assertEquals("", resultado.isPresent() ? resultado.get().getNombre() : "");
        verify(tipoServicioRepository, times(1)).findByNombre("Hospitales");
    }

    @Test
    void findAll_ExistenRoles() {
        when(tipoServicioRepository.findAll()).thenReturn(java.util.List.of(tipoServicio));
        assertEquals(1, tipoServicioService.obtenerTipoServiciosRegistrados().size());
        verify(tipoServicioRepository, times(1)).findAll();
    }

    @Test
    void crearTipoServicio() {
        when(tipoServicioRepository.save(tipoServicio)).thenReturn(tipoServicio);
        assertEquals(tipoServicio, tipoServicioService.crearTipoServicio(tipoServicio));
        verify(tipoServicioRepository, times(1)).save(tipoServicio);
    }

    @Test
    void buscarTipoServicioPorId() {
        when(tipoServicioRepository.findById(1L)).thenReturn(Optional.of(tipoServicio));
        Optional<TipoServicio> buscar = tipoServicioService.buscarPorId(1L);
        assertEquals(1L, buscar.get().getId());
        verify(tipoServicioRepository, times(1)).findById(1L);
    }


}