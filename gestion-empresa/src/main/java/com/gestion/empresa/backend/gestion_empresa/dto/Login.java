package com.gestion.empresa.backend.gestion_empresa.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Login {
    private String nombreUsuario;
    private String password;
}
