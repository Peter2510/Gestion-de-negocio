package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.models.Rol;
import com.gestion.empresa.backend.gestion_empresa.repositories.RolRepository;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.RolServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RolControllerTest {

    @Mock
    private RolRepository rolRepository;

    @Mock
    private RolServiceImpl rolService;

    @InjectMocks
    private RolController rolController;

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
    void obtenerRolesRegistrados() {
        // Simular comportamiento de la lista de roles
        List<Rol> roles = new ArrayList<>();
        roles.add(rol);

        when(rolService.findAll()).thenReturn(roles);

        // Llamada al controlador
        ResponseEntity<List<Rol>> response = rolController.obtenerRolesRegistrados();

        // Verificar resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Contabilidad", response.getBody().get(0).getNombre());

        // Verificar que el servicio fue llamado
        verify(rolService, times(1)).findAll();
    }

    @Test
    void crearRol_RolNuevo() {
        // Simular que no existe un rol con ese nombre
        when(rolRepository.findByNombre(any(String.class))).thenReturn(Optional.empty());
        when(rolService.crearRol(any(Rol.class))).thenReturn(rol);

        // Llamada al controlador
        ResponseEntity<String> response = rolController.crearRol(rol);

        // Verificar resultado
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("El rol Contabilidad creado con éxito", response.getBody());

        // Verificar que el repositorio y servicio fueron llamados
        verify(rolRepository, times(1)).findByNombre(any(String.class));
        verify(rolService, times(1)).crearRol(any(Rol.class));
    }

    @Test
    void crearRol_RolExistente() {
        // Simular que ya existe un rol con ese nombre
        when(rolRepository.findByNombre(any(String.class))).thenReturn(Optional.of(rol));

        // Llamada al controlador
        ResponseEntity<String> response = rolController.crearRol(rol);

        // Verificar resultado
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("El rol con nombre Contabilidad ya existe", response.getBody());

        // Verificar que el repositorio fue llamado
        verify(rolRepository, times(1)).findByNombre(any(String.class));

        // Verificar que el servicio no fue llamado porque ya existe el rol
        verify(rolService, times(0)).crearRol(any(Rol.class));
    }
}
