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
        //Simulando que existe un rol con ese nombre
        when(rolRepository.findByNombre("Contabilidad")).thenReturn(Optional.of(rol));

        //llamar al metodo
        Optional<Rol> resultado = rolService.buscarPorNombre("Contabilidad");

        // Verificar resultado
        assertNotNull(resultado);
        assertEquals("Contabilidad", resultado.isPresent() ? resultado.get().getNombre() : "");
        //se define un número de invocaciones y se busca al rol Contabilidad
        verify(rolRepository, times(1)).findByNombre("Contabilidad");
    }

    @Test
    void buscarPorNombre_RolNoExistente() {
        //simular que no existe un rol con ese nombre
        when(rolRepository.findByNombre("Administracion")).thenReturn(Optional.empty());

        //llamar al metodo
        Optional<Rol> resultado = rolService.buscarPorNombre("Administracion");

        //verificar resultado
        assertEquals(Optional.empty(), resultado);
        verify(rolRepository, times(1)).findByNombre("Administracion");
    }

    @Test
    void findAll_ExistenRoles() {
        // Simular que hay roles disponibles
        List<Rol> roles = new ArrayList<>();
        roles.add(rol);
        when(rolRepository.findAll()).thenReturn(roles);

        //llamar al metodo
        List<Rol> resultado = rolService.obtenerRolesRegistrados();

        // Verificar resultado
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Contabilidad", resultado.get(0).getNombre());
        verify(rolRepository, times(1)).findAll();
    }

    @Test
    void findAll_NoExistenRoles() {
        //simulando que no hay roles disponibles
        when(rolRepository.findAll()).thenReturn(new ArrayList<>());

        //llamando al metodo
        List<Rol> resultado = rolService.obtenerRolesRegistrados();

        //verificar resultado
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(rolRepository, times(1)).findAll();
    }

    @Test
    void crearRol_CrearNuevoRol() {
        //simular el comportamiento de guardar un nuevo rol
        when(rolRepository.save(rol)).thenReturn(rol);

        //llamar al metodo
        Rol resultado = rolService.crearRol(rol);

        //verificar resultado
        assertNotNull(resultado);
        assertEquals("Contabilidad", resultado.getNombre());
        verify(rolRepository, times(1)).save(rol);
    }

}