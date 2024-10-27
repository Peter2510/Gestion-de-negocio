package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.dto.RolPermisoDTO;
import com.gestion.empresa.backend.gestion_empresa.models.Permiso;
import com.gestion.empresa.backend.gestion_empresa.models.Rol;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.PermisoRolServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.RolServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RolControllerTest {

    @InjectMocks
    private RolController rolController;

    @Mock
    private RolServiceImpl rolService;

    @Mock
    private PermisoRolServiceImpl permisoRolService;

    private RolPermisoDTO rolPermisoDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        rolPermisoDTO = new RolPermisoDTO();
        rolPermisoDTO.setNombre("Admin");
        rolPermisoDTO.setDescripcion("Rol de administrador");
        rolPermisoDTO.setPermisos(Arrays.asList(new Permiso(1L, "READ"), new Permiso(2L, "WRITE")));
    }

    @Test
    public void testObtenerRolesRegistrados_Success() {
        Rol rol = new Rol();
        rol.setNombre("Admin");
        List<Rol> roles = List.of(rol);

        when(rolService.obtenerRolesRegistrados()).thenReturn(roles);

        ResponseEntity<Map<String, Object>> response = rolController.obtenerRolesRegistrados();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody().get("ok"));
        assertEquals(1, ((List<?>) response.getBody().get("roles")).size());
    }

    @Test
    public void testCrearRol_Success() {
        when(rolService.buscarPorNombre("Admin")).thenReturn(Optional.empty());

        Rol nuevoRol = new Rol();
        nuevoRol.setNombre("Admin");
        nuevoRol.setDescripcion("Rol de administrador");
        when(rolService.crearRol(any())).thenReturn(nuevoRol);

        ResponseEntity<Map<String, Object>> response = rolController.crearRol(rolPermisoDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue((Boolean) response.getBody().get("ok"));
        assertEquals("Rol creado exitosamente", response.getBody().get("mensaje"));
        verify(permisoRolService, times(2)).crearPermisoRol(any());
    }

    @Test
    public void testCrearRol_Conflict() {
        when(rolService.buscarPorNombre("Admin")).thenReturn(Optional.of(new Rol()));

        ResponseEntity<Map<String, Object>> response = rolController.crearRol(rolPermisoDTO);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertFalse((Boolean) response.getBody().get("ok"));
        assertEquals("El rol Admin ya existe", response.getBody().get("mensaje"));
    }

    @Test
    public void testCrearRol_InternalServerError() {
        when(rolService.buscarPorNombre("Admin")).thenReturn(Optional.empty());
        when(rolService.crearRol(any())).thenReturn(null);

        ResponseEntity<Map<String, Object>> response = rolController.crearRol(rolPermisoDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertFalse((Boolean) response.getBody().get("ok"));
        assertEquals("Error al crear el rol", response.getBody().get("mensaje"));
    }
}
