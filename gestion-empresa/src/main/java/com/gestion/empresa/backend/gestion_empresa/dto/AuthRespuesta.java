package com.gestion.empresa.backend.gestion_empresa.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
public class AuthRespuesta {
    String token;
    Object data;
    HttpStatus status;
    Long idUsuario;
}
