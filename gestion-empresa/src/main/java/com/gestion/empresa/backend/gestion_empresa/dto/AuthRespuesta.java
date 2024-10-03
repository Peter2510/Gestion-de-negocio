package com.gestion.empresa.backend.gestion_empresa.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
public class AuthRespuesta {
    String token;
}
