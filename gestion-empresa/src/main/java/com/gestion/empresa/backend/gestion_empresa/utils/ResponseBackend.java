package com.gestion.empresa.backend.gestion_empresa.utils;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Author: gordillox
 * Created on: 16/10/24
 */

@Data
@NoArgsConstructor
public class ResponseBackend {

    private Boolean ok;
    private HttpStatus status;
    private String mensaje;
    private Object data;


    public ResponseBackend(Boolean ok, HttpStatus status, String mensaje) {
        this.ok = ok;
        this.status = status;
        this.mensaje = mensaje;
    }

    public ResponseBackend(Boolean ok, HttpStatus status, Object data) {
        this.ok = ok;
        this.status = status;
        this.data = data;
    }

    public ResponseBackend(Boolean ok, HttpStatus status, String mensaje, Object data) {
        this.ok = ok;
        this.status = status;
        this.mensaje = mensaje;
        this.data = data;
    }

}
