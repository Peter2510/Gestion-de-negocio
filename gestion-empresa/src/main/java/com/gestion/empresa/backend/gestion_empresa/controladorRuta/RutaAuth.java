package com.gestion.empresa.backend.gestion_empresa.controladorRuta;

import com.gestion.empresa.backend.gestion_empresa.Dto.Login;
import com.gestion.empresa.backend.gestion_empresa.models.Usuarios;
import com.gestion.empresa.backend.gestion_empresa.validacion.RespuestaLogin;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Auth")
public class RutaAuth {

    @PostMapping(value = "/login")
    public ResponseEntity<Object> authenticate(@RequestBody Login loginUserDto){


        return ResponseEntity.ok("login");
    }
}
