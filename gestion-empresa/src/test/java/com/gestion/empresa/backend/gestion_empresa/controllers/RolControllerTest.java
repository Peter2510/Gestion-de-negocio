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

import java.util.*;

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
        //simular comportamiento de la lista de roles
        List<Rol> roles = new ArrayList<>();
        roles.add(rol);
        when(rolService.obtenerRolesRegistrados()).thenReturn(roles);
        //llamada al controlador
        ResponseEntity<Map<String,Object>> response = rolController.obtenerRolesRegistrados();
        // Verificar resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Hacer un cast explícito a List antes de llamar a size()
        List<Rol> rolesResponse = (List<Rol>) response.getBody().get("roles");
        assertEquals(1, rolesResponse.size());
        // Verificar que el servicio fue llamado
        verify(rolService, times(1)).obtenerRolesRegistrados();
    }
//    @Test
//    void crearRol_RolNuevo() {
//        //simular que no existe un rol con ese nombre
//        when(rolService.buscarPorNombre(any(String.class))).thenReturn(Optional.empty());
//        when(rolService.crearRol(any(Rol.class))).thenReturn(rol);
//        //llamada al controlador
//        ResponseEntity<Map<String,Object>> response = rolController.crearRol(rol);
//        //verificar resultado
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals("Rol creado exitosamente", Objects.requireNonNull(response.getBody()).get("mensaje"));
//        //verificar que el repositorio y servicio fueron llamados
//        verify(rolService, times(1)).buscarPorNombre(rol.getNombre());
//        verify(rolService, times(1)).crearRol(any(Rol.class));
//    }
//
//    @Test
//    void crearRol_RolExistente() {
//
//        when(rolService.buscarPorNombre(any(String.class))).thenReturn(Optional.of(rol));
//
//        ResponseEntity<Map<String,Object>> response = rolController.crearRol(rol);
//
//        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
//        assertEquals("El rol Contabilidad ya existe", Objects.requireNonNull(response.getBody()).get("mensaje"));
//        verify(rolService, times(0)).crearRol(any(Rol.class));
//    }
//
//    @Test
//    void crearRol_noSeCreaRol(){
//        when(rolService.buscarPorNombre(any(String.class))).thenReturn(Optional.empty());
//        when(rolService.crearRol(any(Rol.class))).thenReturn(null);
//
//        ResponseEntity<Map<String,Object>> response = rolController.crearRol(rol);
//
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//        assertEquals("Error al crear el rol",response.getBody().get("mensaje"));
//
//        verify(rolService, times(1)).crearRol(any(Rol.class));
//    }
}