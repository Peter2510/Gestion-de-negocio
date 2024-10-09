package com.gestion.empresa.backend.gestion_empresa.utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: gordillox
 * Created on: 5/10/24
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();

        // Recolecta los errores en una lista
        List<Map<String, String>> errores = new ArrayList<>();
        bindingResult.getFieldErrors().forEach(error -> {
            Map<String, String> errorDetalle = new HashMap<>();
            errorDetalle.put("campo", error.getField());
            errorDetalle.put("mensaje", error.getDefaultMessage());
            errores.add(errorDetalle);
        });

        // Devuelve el error como un array de errores
        Map<String, Object> response = new HashMap<>();
        response.put("ok", false);
        response.put("errores", errores);

        return ResponseEntity.badRequest().body(response);
    }

    // Manejo de MissingServletRequestParameterException
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, Object>> handleMissingParams(MissingServletRequestParameterException ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("ok", false);
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put("mensaje", "El parámetro '" + ex.getParameterName() + "' es obligatorio.");
        body.put("path", request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /*@ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("ok", false, "mensaje", ex.getMessage()));
    }*/
}