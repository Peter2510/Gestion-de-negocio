package com.gestion.empresa.backend.gestion_empresa.utils;

import com.gestion.empresa.backend.gestion_empresa.utils.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    public void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    public void testHandleValidationExceptions() {
        // Simula un BindingResult con un error
        BindingResult bindingResult = mock(BindingResult.class);
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        // Simula el comportamiento del BindingResult
        when(bindingResult.getFieldErrors()).thenReturn(
                List.of(new FieldError("objectName", "fieldName", "El campo es obligatorio"))
        );

        // Llama al método
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleValidationExceptions(ex);

        // Verifica el resultado
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(false, response.getBody().get("ok"));
        assertEquals(1, ((List<?>) response.getBody().get("errores")).size());
        assertEquals("El campo es obligatorio", ((List<Map<String, String>>) response.getBody().get("errores")).get(0).get("mensaje"));
    }

    @Test
    public void testHandleMissingParams() {
        // Simula WebRequest
        WebRequest request = mock(WebRequest.class);
        when(request.getDescription(false)).thenReturn("uri=/api/test");

        // Simula la excepción
        MissingServletRequestParameterException ex = new MissingServletRequestParameterException("paramName", "paramType");

        // Llama al método
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleMissingParams(ex, request);

        // Verifica el resultado
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(false, response.getBody().get("ok"));
        assertEquals("Bad Request", response.getBody().get("error"));
        assertEquals("El parámetro 'paramName' es obligatorio.", response.getBody().get("mensaje"));
        assertEquals("/api/test", response.getBody().get("path"));
    }
}
