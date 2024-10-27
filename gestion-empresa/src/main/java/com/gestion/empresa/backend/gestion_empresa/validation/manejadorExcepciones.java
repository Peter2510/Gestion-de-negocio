package com.gestion.empresa.backend.gestion_empresa.validation;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Author: alexxus
 * Created on: 7/10/24
 */

@ControllerAdvice
@Data
public class manejadorExcepciones {

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)  // Cambia el status a 401 si es necesario
    public String handleBadCredentialsException(BadCredentialsException ex) {
        return ex.getMessage();  // Retorna el mensaje "Credenciales incorrectas"
    }
}
