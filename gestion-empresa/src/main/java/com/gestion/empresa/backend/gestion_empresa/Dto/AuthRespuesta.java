package com.gestion.empresa.backend.gestion_empresa.Dto;

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
