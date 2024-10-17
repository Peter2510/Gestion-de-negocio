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
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBackend {

    private Boolean ok;
    private HttpStatus status;
    private String mensaje;

}
