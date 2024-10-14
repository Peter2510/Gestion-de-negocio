package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.models.Permiso;
import com.gestion.empresa.backend.gestion_empresa.services.PermisoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PermisoControllerTest {


    @Mock
    private PermisoService permisoService;

    @InjectMocks
    private PermisoController permisoController;

    private Permiso permiso;

    @BeforeEach
    void setUp() {
        permiso = new Permiso();
        permiso.setId(1L);
        permiso.setNombre("Editar empresa");
    }

    @Test
    void crearPermiso_Exitoso() {
        when(permisoService.buscarPermisoPorNombre(any(String.class))).thenReturn(Optional.empty());
        when(permisoService.crearPermiso(any(Permiso.class))).thenReturn(permiso);

        ResponseEntity<Map<String, Object>> response = permisoController.crearPermiso(permiso);

        assertNotNull(response.getBody(), "La respuesta no debe ser nula");
        assertEquals(true,response.getBody().get("ok"));
        assertEquals("Permiso creado exitosamente",response.getBody().get("mensaje"));

        verify(permisoService, times(1)).buscarPermisoPorNombre(any(String.class));
        verify(permisoService, times(1)).crearPermiso(permiso);

    }

    @Test
    void crearPermiso_Repetido() {

        when(permisoService.buscarPermisoPorNombre(any(String.class))).thenReturn(Optional.of(permiso));

        ResponseEntity<Map<String, Object>> response = permisoController.crearPermiso(permiso);

        assertNotNull(response.getBody(), "La respuesta no debe ser nula");
        assertEquals(false,response.getBody().get("ok"));
        assertEquals("El permiso " + permiso.getNombre() + " ya esta registrado",response.getBody().get("mensaje"));

        verify(permisoService, times(1)).buscarPermisoPorNombre(any(String.class));

    }

    @Test
    void obtenerPermisos() {
        when(permisoService.obtenerPermisosRegistrados()).thenReturn(List.of(permiso));

        ResponseEntity<Map<String, Object>> response = permisoController.obtenerPermisos();

        assertNotNull(response.getBody(), "La respuesta no debe ser nula");
        assertEquals(true,response.getBody().get("ok"));
        assertEquals(List.of(permiso),response.getBody().get("permisos"));

        verify(permisoService, times(1)).obtenerPermisosRegistrados();

    }
}