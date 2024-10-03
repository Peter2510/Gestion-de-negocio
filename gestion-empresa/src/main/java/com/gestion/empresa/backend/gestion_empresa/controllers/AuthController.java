package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.dto.AuthRespuesta;
import com.gestion.empresa.backend.gestion_empresa.dto.Login;
import com.gestion.empresa.backend.gestion_empresa.dto.RegistroUsuarios;
import com.gestion.empresa.backend.gestion_empresa.models.Usuarios;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.AutenticacionServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.validation.RespuestaLogin;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Auth")
@RequiredArgsConstructor
public class AuthController {


    private final AutenticacionServiceImpl servicio;
    @PostMapping(value = "/login")
    public ResponseEntity<AuthRespuesta> login(@RequestBody Login loginUserDto){
        return ResponseEntity.ok(servicio.login(loginUserDto));

    }


    @PostMapping(value = "/registro")
    public ResponseEntity<AuthRespuesta> registro(@RequestBody RegistroUsuarios registro){
        return ResponseEntity.ok(servicio.registro(registro));
    }


}
