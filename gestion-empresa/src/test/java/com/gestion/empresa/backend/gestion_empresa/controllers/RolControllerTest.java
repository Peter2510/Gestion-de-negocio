package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.models.Rol;
import com.gestion.empresa.backend.gestion_empresa.repositories.RolRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        //inicializar los mocks
        MockitoAnnotations.openMocks(this);
        //crear un objeto de Rol para los tests
        rol = new Rol();
        rol.setId(1L);
        rol.setNombre("Contabilidad");
        rol.setDescripcion("Contabilidad del sistema");
    }

    @Test
    void obtenerRolesRegistrados_retornaListaDeRoles() {
        List<Rol> roles = List.of(
                rol,
                new Rol(2L, "Cliente", "Utiliza los servicios de la plataforma"),
                new Rol(3L, "Administrador", "Responsable de administrar el sitio"),
                new Rol(4L, "Secretaria", "Encargado de agendar citas")
        );

        //simular que el servicio retorna la lista de roles
        when(rolService.obtenerRolesRegistrados()).thenReturn(roles);

        //llamada al controlador
        ResponseEntity<Map<String, Object>> response = rolController.obtenerRolesRegistrados();

        //verificar resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("roles")); //verificar que la clave "roles" esté presente
        assertTrue(response.getBody().containsKey("ok")); //verificar que la clave "ok" esté presente

        List<Rol> rolesResponse = (List<Rol>) response.getBody().get("roles");
        assertEquals(4, rolesResponse.size()); //verificar que la lista de roles tenga el tamaño correcto
        assertEquals("Contabilidad", rolesResponse.get(0).getNombre()); //verificar el primer rol
        assertEquals("Contabilidad del sistema", rolesResponse.get(0).getDescripcion()); //verificar la descripcion
        assertEquals("Cliente", rolesResponse.get(1).getNombre()); // Verificar el segundo rol
        assertEquals("Utiliza los servicios de la plataforma", rolesResponse.get(1).getDescripcion());//verificar la descripcion

        //verificar que el servicio fue llamado
        verify(rolService, times(1)).obtenerRolesRegistrados();

    }

    @Test
    void crearRol_RolNuevo() {

        when(rolService.crearRol(any(Rol.class))).thenReturn(rol);

        //llamada al controlador
        ResponseEntity<Map<String, Object>> response = rolController.crearRol(rol);

        //verificar resultado
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("ok")); //verificar que la clave "ok" esté presente
        assertTrue((Boolean) response.getBody().get("ok")); //verificar que el valor de "ok" sea true
        assertEquals("Rol creado exitosamente", response.getBody().get("mensaje")); //verificar el mensaje

        //verificar que el repositorio y servicio fueron llamados
        verify(rolService, times(1)).buscarPorNombre(rol.getNombre());//verificar que buscarPorNombre fue llamado

        verify(rolService, times(1)).crearRol(rol);//verificar que se llama a crearRol con el rol correcto
    }

    @Test
    void crearRol_RolExistente() {
        when(rolService.buscarPorNombre("Contabilidad")).thenReturn(Optional.of(rol));

        //llamada al controlador
        ResponseEntity<Map<String, Object>> response = rolController.crearRol(rol);

        // Verificar resultado
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("ok")); //verificar que la clave ok esté presente
        assertFalse((Boolean) response.getBody().get("ok")); //verificar que el valor de ok sea false
        assertEquals("El rol Contabilidad ya existe", response.getBody().get("mensaje")); //verificar el mensaje

        //verificar que el repositorio y servicio fueron llamados
        verify(rolService, times(1)).buscarPorNombre(rol.getNombre());//verificar que buscarPorNombre fue llamado
    }

    @Test
    void crearRol_respuestaNull() {
        when(rolService.buscarPorNombre("Contabilidad")).thenReturn(Optional.empty());

        //llamada al controlador
        ResponseEntity<Map<String, Object>> response = rolController.crearRol(rol);

        // Verificar resultado
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("ok")); //verificar que la clave ok esté presente
        assertFalse((Boolean) response.getBody().get("ok")); //verificar que el valor de ok sea false
        assertEquals("No se pudo crear el rol", response.getBody().get("mensaje")); //verificar el mensaje

        //verificar que el repositorio y servicio fueron llamados
        verify(rolService, times(1)).buscarPorNombre(rol.getNombre());//verificar que buscarPorNombre fue llamado
        verify(rolService, times(1)).crearRol(rol);
    }
}
