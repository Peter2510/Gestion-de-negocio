package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.dto.ActualizacionUsuarioAdminDTO;
import com.gestion.empresa.backend.gestion_empresa.dto.ActualizarContraseniaDTO;
import com.gestion.empresa.backend.gestion_empresa.models.Usuarios;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.UsuarioServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuariosControllerTest {

    @InjectMocks
    private UsuariosController usuariosController;

    @Mock
    private UsuarioServiceImpl usuarioServiceImpl;

    private ActualizacionUsuarioAdminDTO actualizacionUsuarioAdminDTO;
    private ActualizarContraseniaDTO actualizarContraseniaDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        actualizacionUsuarioAdminDTO = new ActualizacionUsuarioAdminDTO();
        actualizarContraseniaDTO = new ActualizarContraseniaDTO();
    }

    @Test
    public void testObtenerUsuario_Success() {
        Usuarios usuario = new Usuarios();
        ResponseBackend responseBackend = new ResponseBackend(true, HttpStatus.OK, usuario);
        when(usuarioServiceImpl.buscarPorId(1L)).thenReturn(responseBackend);

        ResponseEntity<Object> response = usuariosController.obtenerUsuario(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
    }

    @Test
    public void testObtenerUsuario_NotFound() {
        ResponseBackend responseBackend = new ResponseBackend(false, HttpStatus.NOT_FOUND, "Usuario no encontrado");
        when(usuarioServiceImpl.buscarPorId(1L)).thenReturn(responseBackend);

        ResponseEntity<Object> response = usuariosController.obtenerUsuario(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse((Boolean) ((Map<?, ?>) response.getBody()).get("ok"));
    }

    @Test
    public void testEditarUsuario_Success() {
        ResponseBackend responseBackend = new ResponseBackend(true, HttpStatus.OK, "Usuario actualizado");
        when(usuarioServiceImpl.actualizarUsuario(any())).thenReturn(responseBackend);

        ResponseEntity<Object> response = usuariosController.editarUsuario(actualizacionUsuarioAdminDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) ((Map<?, ?>) response.getBody()).get("ok"));
    }

    @Test
    public void testEditarUsuario_Failure() {
        ResponseBackend responseBackend = new ResponseBackend(false, HttpStatus.BAD_REQUEST, "Error al actualizar");
        when(usuarioServiceImpl.actualizarUsuario(any())).thenReturn(responseBackend);

        ResponseEntity<Object> response = usuariosController.editarUsuario(actualizacionUsuarioAdminDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse((Boolean) ((Map<?, ?>) response.getBody()).get("ok"));
        assertEquals("Error al actualizar", ((Map<?, ?>) response.getBody()).get("mensaje"));
    }

    @Test
    public void testActualizarContrasenia_Success() {
        ResponseBackend responseBackend = new ResponseBackend(true, HttpStatus.OK, "Contraseña actualizada");
        when(usuarioServiceImpl.actualizarContrasenia(any())).thenReturn(responseBackend);

        ResponseEntity<Map<String, Object>> response = usuariosController.actualizarContrasenia(actualizarContraseniaDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) ((Map<?, ?>) response.getBody()).get("ok"));
    }

    @Test
    public void testActualizarContrasenia_Failure() {
        ResponseBackend responseBackend = new ResponseBackend(false, HttpStatus.BAD_REQUEST, "Error al actualizar contraseña");
        when(usuarioServiceImpl.actualizarContrasenia(any())).thenReturn(responseBackend);

        ResponseEntity<Map<String, Object>> response = usuariosController.actualizarContrasenia(actualizarContraseniaDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error al actualizar contraseña", response.getBody().get("mensaje"));
    }
}
