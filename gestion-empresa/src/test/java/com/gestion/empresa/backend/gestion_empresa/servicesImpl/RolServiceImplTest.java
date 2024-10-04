package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.gestion.empresa.backend.gestion_empresa.models.Rol;
import com.gestion.empresa.backend.gestion_empresa.repositories.RolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class RolServiceImplTest {

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private RolServiceImpl rolService;

    private Rol rol;

    @BeforeEach
    void setUp() {
        // Crear un objeto de Rol para los tests
        rol = new Rol();
        rol.setId(1L);
        rol.setNombre("Contabilidad");
        rol.setDescripcion("Contabilidad del sistema");
    }

    @Test
    void buscarPorNombre_RolExistente() {
        // Simular que existe un rol con ese nombre
        when(rolRepository.findByNombre("Contabilidad")).thenReturn(Optional.of(rol));

        // Llamar al método
        Rol resultado = rolService.buscarPorNombre("Contabilidad");

        // Verificar resultado
        assertNotNull(resultado);
        assertEquals("Contabilidad", resultado.getNombre());
        // Se define un número de invocaciones y se y se busca al rol Contabilidad
        verify(rolRepository, times(1)).findByNombre("Contabilidad");
    }

    @Test
    void buscarPorNombre_RolNoExistente() {
        // Simular que no existe un rol con ese nombre
        when(rolRepository.findByNombre("Administración")).thenReturn(Optional.empty());

        // Llamar al método
        Rol resultado = rolService.buscarPorNombre("Administración");

        // Verificar resultado
        assertNull(resultado);
        verify(rolRepository, times(1)).findByNombre("Administración");
    }

    @Test
    void findAll_ExistenRoles() {
        // Simular que hay roles disponibles
        List<Rol> roles = new ArrayList<>();
        roles.add(rol);
        when(rolRepository.findAll()).thenReturn(roles);

        // Llamar al método
        List<Rol> resultado = rolService.findAll();

        // Verificar resultado
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Contabilidad", resultado.get(0).getNombre());
        verify(rolRepository, times(1)).findAll();
    }

    @Test
    void findAll_NoExistenRoles() {
        // Simular que no hay roles disponibles
        when(rolRepository.findAll()).thenReturn(new ArrayList<>());

        // Llamar al método
        List<Rol> resultado = rolService.findAll();

        // Verificar resultado
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(rolRepository, times(1)).findAll();
    }

    @Test
    void crearRol_CrearNuevoRol() {
        // Simular el comportamiento de guardar un nuevo rol
        when(rolRepository.save(rol)).thenReturn(rol);

        // Llamar al método
        Rol resultado = rolService.crearRol(rol);

        // Verificar resultado
        assertNotNull(resultado);
        assertEquals("Contabilidad", resultado.getNombre());
        verify(rolRepository, times(1)).save(rol);
    }

    @Test
    void crearRol_ErrorAlCrear() {
        // Simular el comportamiento de que el rol no se pueda guardar (por ejemplo, un error en la base de datos)
        when(rolRepository.save(rol)).thenThrow(new RuntimeException("Error al crear el rol"));

        // Llamar al método
        Exception exception = assertThrows(RuntimeException.class, () -> rolService.crearRol(rol));

        // Verificar resultado
        assertEquals("Error al crear el rol", exception.getMessage());
        verify(rolRepository, times(1)).save(rol);
    }
}