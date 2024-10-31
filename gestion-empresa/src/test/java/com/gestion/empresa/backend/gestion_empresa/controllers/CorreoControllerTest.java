package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.dto.ActualizarContraseniaDTO;
import com.gestion.empresa.backend.gestion_empresa.dto.RecuperarContraseniaDTO;
import com.gestion.empresa.backend.gestion_empresa.models.CorreoConfirmacion;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.CacheServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.CorreoMensajeServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.UsuarioServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CorreoControllerTest {

    @InjectMocks
    private CorreoController correoController;

    @Mock
    private CorreoMensajeServiceImpl emailService;

    @Mock
    private UsuarioServiceImpl usuarioServiceImpl;

    @Mock
    private CacheServiceImpl cacheService;

    @BeforeEach
    public void setUp() {
        // Inicializa los mocks antes de cada prueba
    }

    @Test
    public void testSendEmail() {
        // Dado
        CorreoConfirmacion correo = new CorreoConfirmacion();
        correo.setCorreo("test@example.com");
        correo.setCodigo("123456");

        // Mock del servicio
        ResponseBackend responseBackend = new ResponseBackend(true, HttpStatus.OK, "Correo enviado con éxito");
        when(emailService.generarCorreo(anyString(), anyString(), anyString())).thenReturn(responseBackend);

        // Cuando
        String resultado = correoController.sendEmail(correo);

        // Entonces
        assertEquals("Correo enviado con éxito", resultado);
        verify(emailService, times(1)).generarCorreo(anyString(), anyString(), anyString());
    }

    @Test
    public void testRecuperarContrasenia_Success() {
        // Dado
        RecuperarContraseniaDTO recuperarContraseniaDTO = new RecuperarContraseniaDTO();
        recuperarContraseniaDTO.setCorreo("test@example.com");

        ResponseBackend responseBackend = new ResponseBackend(true, HttpStatus.OK, "Correo de recuperación enviado");
        when(usuarioServiceImpl.recuperarContrasenia(anyString())).thenReturn(responseBackend);

        // Cuando
        ResponseEntity<Map<String, Object>> response = correoController.recuperarContrasenia(recuperarContraseniaDTO);

        // Entonces
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody().get("ok"));
        assertEquals("Correo de recuperación enviado", response.getBody().get("mensaje"));
        verify(usuarioServiceImpl, times(1)).recuperarContrasenia(anyString());
    }

    @Test
    public void testRecuperarContrasenia_Failure() {
        // Dado
        RecuperarContraseniaDTO recuperarContraseniaDTO = new RecuperarContraseniaDTO();
        recuperarContraseniaDTO.setCorreo("test@example.com");

        ResponseBackend responseBackend = new ResponseBackend(false, HttpStatus.BAD_REQUEST, "Correo no encontrado");
        when(usuarioServiceImpl.recuperarContrasenia(anyString())).thenReturn(responseBackend);

        // Cuando
        ResponseEntity<Map<String, Object>> response = correoController.recuperarContrasenia(recuperarContraseniaDTO);

        // Entonces
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse((Boolean) response.getBody().get("ok"));
        assertEquals("Correo no encontrado", response.getBody().get("mensaje"));
        verify(usuarioServiceImpl, times(1)).recuperarContrasenia(anyString());
    }

    @Test
    public void testValidarCodigo_Success() {
        // Dado
        RecuperarContraseniaDTO recuperarContraseniaDTO = new RecuperarContraseniaDTO();
        recuperarContraseniaDTO.setCorreo("test@example.com");
        recuperarContraseniaDTO.setCodigo("123456");

        ResponseBackend responseBackend = new ResponseBackend(true, HttpStatus.OK, "Código validado", "userId");
        when(usuarioServiceImpl.validarCodigoRecuperacion(anyString(), anyString())).thenReturn(responseBackend);

        // Cuando
        ResponseEntity<Map<String, Object>> response = correoController.validarCodigo(recuperarContraseniaDTO);

        // Entonces
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody().get("ok"));
        assertEquals("Código validado", response.getBody().get("mensaje"));
        assertEquals("userId", response.getBody().get("idUsuario"));
        verify(usuarioServiceImpl, times(1)).validarCodigoRecuperacion(anyString(), anyString());
    }

    @Test
    public void testValidarCodigo_Failure() {
        // Dado
        RecuperarContraseniaDTO recuperarContraseniaDTO = new RecuperarContraseniaDTO();
        recuperarContraseniaDTO.setCorreo("test@example.com");
        recuperarContraseniaDTO.setCodigo("123456");

        ResponseBackend responseBackend = new ResponseBackend(false, HttpStatus.BAD_REQUEST, "Código inválido");
        when(usuarioServiceImpl.validarCodigoRecuperacion(anyString(), anyString())).thenReturn(responseBackend);

        // Cuando
        ResponseEntity<Map<String, Object>> response = correoController.validarCodigo(recuperarContraseniaDTO);

        // Entonces
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse((Boolean) response.getBody().get("ok"));
        assertEquals("Código inválido", response.getBody().get("mensaje"));
        verify(usuarioServiceImpl, times(1)).validarCodigoRecuperacion(anyString(), anyString());
    }


}
