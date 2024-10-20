package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.models.Permiso;
import com.gestion.empresa.backend.gestion_empresa.repositories.PermisoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PermisoServiceImplTest {

    @Mock
    private PermisoRepository permisoRepository;

    @InjectMocks
    private PermisoServiceImpl permisoService;

    private Permiso permiso;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        permiso = new Permiso();
        permiso.setId(1L);
        permiso.setNombre("PERMISO_ADMIN");
    }

    @Test
    void testCrearPermiso() {
        when(permisoRepository.save(permiso)).thenReturn(permiso);

        Permiso resultado = permisoService.crearPermiso(permiso);

        assertNotNull(resultado, "El permiso creado no debe ser nulo");
        assertEquals(permiso, resultado, "El permiso devuelto debe coincidir con el mock");

        verify(permisoRepository, times(1)).save(permiso);
    }

    @Test
    void testBuscarPorId_PermisoEncontrado() {
        when(permisoRepository.findById(1L)).thenReturn(Optional.of(permiso));

        Optional<Permiso> resultado = permisoService.buscarPorId(1L);

        assertTrue(resultado.isPresent(), "El permiso debe estar presente");
        assertEquals(permiso, resultado.get(), "El permiso devuelto debe coincidir con el mock");

        verify(permisoRepository, times(1)).findById(1L);
    }

    @Test
    void testBuscarPorId_PermisoNoEncontrado() {
        when(permisoRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Permiso> resultado = permisoService.buscarPorId(2L);

        assertFalse(resultado.isPresent(), "El permiso no debe estar presente");

        verify(permisoRepository, times(1)).findById(2L);
    }

    @Test
    void testObtenerPermisosRegistrados() {
        List<Permiso> permisos = List.of(permiso, new Permiso());

        when(permisoRepository.findAll()).thenReturn(permisos);

        List<Permiso> resultado = permisoService.obtenerPermisosRegistrados();

        assertNotNull(resultado, "La lista de permisos no debe ser nula");
        assertEquals(2, resultado.size(), "El tamaño de la lista debe ser 2");
        verify(permisoRepository, times(1)).findAll();
    }

    @Test
    void testActualizarPermiso() {
        when(permisoRepository.save(permiso)).thenReturn(permiso);

        Permiso resultado = permisoService.actualizarPermiso(permiso);

        assertNotNull(resultado, "El permiso actualizado no debe ser nulo");
        assertEquals(permiso, resultado, "El permiso devuelto debe coincidir con el mock");
        verify(permisoRepository, times(1)).save(permiso);
    }

    @Test
    void testBuscarPermisoPorNombre_PermisoEncontrado() {
        when(permisoRepository.findByNombre("PERMISO_ADMIN")).thenReturn(Optional.of(permiso));

        Optional<Permiso> resultado = permisoService.buscarPermisoPorNombre("PERMISO_ADMIN");

        assertTrue(resultado.isPresent(), "El permiso debe estar presente");
        assertEquals(permiso, resultado.get(), "El permiso devuelto debe coincidir con el mock");

        verify(permisoRepository, times(1)).findByNombre("PERMISO_ADMIN");
    }

    @Test
    void testBuscarPermisoPorNombre_PermisoNoEncontrado() {
        when(permisoRepository.findByNombre("PERMISO_USER")).thenReturn(Optional.empty());

        Optional<Permiso> resultado = permisoService.buscarPermisoPorNombre("PERMISO_USER");

        assertFalse(resultado.isPresent(), "El permiso no debe estar presente");

        verify(permisoRepository, times(1)).findByNombre("PERMISO_USER");
    }
  
}