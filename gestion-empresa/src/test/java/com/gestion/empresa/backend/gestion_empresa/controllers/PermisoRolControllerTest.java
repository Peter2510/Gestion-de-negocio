package com.gestion.empresa.backend.gestion_empresa.controllers;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.gestion.empresa.backend.gestion_empresa.dto.ActualizacionRolDTO;
import com.gestion.empresa.backend.gestion_empresa.models.Rol;
import com.gestion.empresa.backend.gestion_empresa.projections.PermisoRolProjection;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.PermisoRolServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.RolServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PermisoRolControllerTest {

    @InjectMocks
    private PermisoRolController permisoRolController;

    @Mock
    private RolServiceImpl rolServiceImpl;

    @Mock
    private PermisoRolServiceImpl permisoRolService;

    private Rol rol;

    @BeforeEach
    void setUp() {
        rol = new Rol();
        rol.setId(1L);
        rol.setNombre("Admin");
        rol.setDescripcion("Administrator role");
    }

    @Test
    void testObtenerPermisosRolesById_RolNoExistente() {
        Long rolId = 1L;

        when(rolServiceImpl.buscarPorId(rolId)).thenReturn(Optional.empty());

        ResponseEntity<Map<String, Object>> response = permisoRolController.obtenerPermisosRolesById(rolId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(false, response.getBody().get("ok"));
        assertEquals("El rol no existe", response.getBody().get("mensaje"));
    }

    @Test
    void testObtenerPermisosRolesById_RolExistente() {
        Long rolId = 1L;

        when(rolServiceImpl.buscarPorId(rolId)).thenReturn(Optional.of(rol));

        List<PermisoRolProjection> permisos = Collections.emptyList();
        when(permisoRolService.obtenerPermisosPorRol(rolId)).thenReturn(permisos);

        ResponseEntity<Map<String, Object>> response = permisoRolController.obtenerPermisosRolesById(rolId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody().get("ok"));
        assertEquals(Optional.of(rol), response.getBody().get("rol"));
        assertEquals(permisos, response.getBody().get("permisos"));
    }

    @Test
    void testActualizarPermisosRol_RolNoExistente() {
        Long rolId = 1L;
        ActualizacionRolDTO nuevosDatos = new ActualizacionRolDTO();
        nuevosDatos.setNombre("Admin");
        nuevosDatos.setDescripcion("Administrator role");
        nuevosDatos.setPermisos(Collections.emptyList());

        when(rolServiceImpl.buscarPorId(rolId)).thenReturn(Optional.empty());

        ResponseEntity<Map<String, Object>> response = permisoRolController.actualizarPermisosRol(rolId, nuevosDatos);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(false, response.getBody().get("ok"));
        assertEquals("El rol no existe", response.getBody().get("mensaje"));
    }

    @Test
    void testActualizarPermisosRol_CambioDeNombreYDescripcion() {
        Long rolId = 1L;
        ActualizacionRolDTO nuevosDatos = new ActualizacionRolDTO();
        nuevosDatos.setNombre("Super Admin");
        nuevosDatos.setDescripcion("Super Administrator role");
        nuevosDatos.setPermisos(Collections.emptyList());

        when(rolServiceImpl.buscarPorId(rolId)).thenReturn(Optional.of(rol));

        ResponseEntity<Map<String, Object>> response = permisoRolController.actualizarPermisosRol(rolId, nuevosDatos);

        verify(rolServiceImpl).crearRol(any(Rol.class));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody().get("ok"));
        assertEquals("Rol actualizado correctamente", response.getBody().get("mensaje"));
    }

    @Test
    void testActualizarPermisosRol_SinCambioEnNombreYDescripcion() {
        Long rolId = 1L;
        ActualizacionRolDTO nuevosDatos = new ActualizacionRolDTO();
        nuevosDatos.setNombre("Admin");
        nuevosDatos.setDescripcion("Administrator role");
        nuevosDatos.setPermisos(Collections.emptyList());

        when(rolServiceImpl.buscarPorId(rolId)).thenReturn(Optional.of(rol));

        ResponseEntity<Map<String, Object>> response = permisoRolController.actualizarPermisosRol(rolId, nuevosDatos);

        verify(rolServiceImpl, never()).crearRol(any(Rol.class));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody().get("ok"));
        assertEquals("Rol actualizado correctamente", response.getBody().get("mensaje"));
    }

    @Test
    void testActualizarPermisosRol_ErrorEnActualizacion() {
        Long rolId = 1L;
        ActualizacionRolDTO nuevosDatos = new ActualizacionRolDTO();
        nuevosDatos.setNombre("Super Admin");
        nuevosDatos.setDescripcion("Super Administrator role");
        nuevosDatos.setPermisos(Collections.emptyList());

        when(rolServiceImpl.buscarPorId(rolId)).thenReturn(Optional.of(rol));
        doThrow(new RuntimeException("Error en la actualización")).when(permisoRolService).actualizarPermisosRol(nuevosDatos.getPermisos(), rolId);

        ResponseEntity<Map<String, Object>> response = permisoRolController.actualizarPermisosRol(rolId, nuevosDatos);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(false, response.getBody().get("ok"));
        assertEquals("Error al actualizar el rol y sus permisos", response.getBody().get("mensaje"));
    }

}
