package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.gestion.empresa.backend.gestion_empresa.dto.IdPermisoDTO;
import com.gestion.empresa.backend.gestion_empresa.models.Permiso;
import com.gestion.empresa.backend.gestion_empresa.models.PermisoRol;
import com.gestion.empresa.backend.gestion_empresa.models.Rol;
import com.gestion.empresa.backend.gestion_empresa.projections.PermisoRolProjection;
import com.gestion.empresa.backend.gestion_empresa.repositories.PermisoRepository;
import com.gestion.empresa.backend.gestion_empresa.repositories.PermisoRolRepository;
import com.gestion.empresa.backend.gestion_empresa.repositories.RolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class PermisoRolServiceImplTest {

    @Mock
    private PermisoRolRepository permisoRolRepository;

    @Mock
    private PermisoRepository permisoRepository;

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private PermisoRolServiceImpl permisoRolService;

    private PermisoRol permisoRol;
    private Rol rol;
    private Permiso permiso;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        permisoRol = new PermisoRol();
        permisoRol.setId(1L);

        rol = new Rol();
        rol.setId(1L);
        permisoRol.setRol(rol);

        permiso = new Permiso();
        permiso.setId(1L);
        permisoRol.setPermiso(permiso);
    }

    @Test
    void testCrearPermisoRol() {
        when(permisoRolRepository.save(permisoRol)).thenReturn(permisoRol);

        PermisoRol resultado = permisoRolService.crearPermisoRol(permisoRol);


        assertNotNull(resultado, "El permiso-rol creado no debe ser nulo");
        assertEquals(permisoRol, resultado, "El permiso-rol devuelto debe coincidir con el esperado");

        verify(permisoRolRepository, times(1)).save(permisoRol);
    }

    @Test
    void testObtenerRegistros() {
        List<PermisoRol> listaPermisoRol = List.of(permisoRol, new PermisoRol());
        when(permisoRolRepository.findAll()).thenReturn(listaPermisoRol);

        List<PermisoRol> resultado = permisoRolService.obtenerRegistros();

        assertNotNull(resultado, "La lista no debe ser nula");
        assertEquals(2, resultado.size(), "La lista debe contener 2 elementos");

        verify(permisoRolRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorId() {
        when(permisoRolRepository.findById(1L)).thenReturn(Optional.of(permisoRol));

        Optional<PermisoRol> resultado = permisoRolService.buscarPorId(1L);

        assertTrue(resultado.isPresent(), "El permiso-rol debe estar presente");
        assertEquals(permisoRol, resultado.get(), "El permiso-rol devuelto debe coincidir con el esperado");

        verify(permisoRolRepository, times(1)).findById(1L);
    }

    @Test
    void testActualizarPermisoRol() {
        when(permisoRolRepository.save(permisoRol)).thenReturn(permisoRol);

        PermisoRol resultado = permisoRolService.actualizarPermisoRol(permisoRol);

        assertNotNull(resultado, "El permiso-rol actualizado no debe ser nulo");
        assertEquals(permisoRol, resultado, "El permiso-rol devuelto debe coincidir con el esperado");

        verify(permisoRolRepository, times(1)).save(permisoRol);
    }

    @Test
    void testObtenerPermisosPorRol() {
        List<PermisoRolProjection> permisos = List.of(
                new PermisoRolProjectionImpl(1L, "Permiso1"),
                new PermisoRolProjectionImpl(2L, "Permiso2")
        );

        when(permisoRolRepository.findByRolId(1L)).thenReturn(permisos);

        List<PermisoRolProjection> resultado = permisoRolService.obtenerPermisosPorRol(1L);

        assertNotNull(resultado, "La lista de permisos no debe ser nula");
        assertEquals(2, resultado.size(), "Debe haber dos permisos devueltos");
        assertEquals("Permiso1", resultado.get(0).getPermisoNombre(), "El nombre del primer permiso debe ser 'Permiso1'");

        verify(permisoRolRepository, times(1)).findByRolId(1L);
    }

    @Test
    void testActualizarPermisosRol() {
        List<PermisoRolProjection> permisosActuales = List.of(
                new PermisoRolProjectionImpl(1L, "Permiso1")
        );
        List<IdPermisoDTO> nuevosPermisos = List.of(
                new IdPermisoDTO(2L)
        );

        when(permisoRolRepository.findByRolId(1L)).thenReturn(permisosActuales);
        when(rolRepository.findById(1L)).thenReturn(Optional.of(rol));
        when(permisoRepository.findById(2L)).thenReturn(Optional.of(permiso));

        permisoRolService.actualizarPermisosRol(nuevosPermisos, 1L);

        verify(permisoRolRepository, times(1)).deleteByRolIdAndPermisoId(1L, 1L);
        verify(permisoRolRepository, times(1)).save(any(PermisoRol.class));
    }

    private static class PermisoRolProjectionImpl implements PermisoRolProjection {
        private Long permisoId;
        private String permisoNombre;

        public PermisoRolProjectionImpl(Long permisoId, String permisoNombre) {
            this.permisoId = permisoId;
            this.permisoNombre = permisoNombre;
        }

        @Override
        public Long getId() {
            return 0L;
        }

        @Override
        public Long getPermisoId() {
            return permisoId;
        }

        @Override
        public String getPermisoNombre() {
            return permisoNombre;
        }
    }
}