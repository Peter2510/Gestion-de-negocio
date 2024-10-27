package com.gestion.empresa.backend.gestion_empresa.validation;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class RespuestaLogin {
    private String token;
    private long expiresIn;

    public RespuestaLogin(String token, long expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
    }
}
