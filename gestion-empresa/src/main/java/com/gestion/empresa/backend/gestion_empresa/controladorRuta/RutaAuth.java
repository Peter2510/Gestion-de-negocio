package com.gestion.empresa.backend.gestion_empresa.controladorRuta;

import com.gestion.empresa.backend.gestion_empresa.Dto.AuthRespuesta;
import com.gestion.empresa.backend.gestion_empresa.Dto.Login;
import com.gestion.empresa.backend.gestion_empresa.Dto.RegistroUsuarios;
import com.gestion.empresa.backend.gestion_empresa.servicio.AutenticacionServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Auth")
@RequiredArgsConstructor
public class RutaAuth {


    private final AutenticacionServicio servicio;
    @PostMapping(value = "/login")
    public ResponseEntity<AuthRespuesta> login(@RequestBody Login loginUserDto){
        return ResponseEntity.ok(servicio.login(loginUserDto));
    }


    @PostMapping(value = "/registro")
    public ResponseEntity<AuthRespuesta> registro(@RequestBody RegistroUsuarios registro){
        return ResponseEntity.ok(servicio.registro(registro));
    }


}
