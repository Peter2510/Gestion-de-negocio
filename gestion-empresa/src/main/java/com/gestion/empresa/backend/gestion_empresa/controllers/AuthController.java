package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.dto.AuthRespuesta;
import com.gestion.empresa.backend.gestion_empresa.dto.Login;
import com.gestion.empresa.backend.gestion_empresa.dto.RegistroUsuarios;
import com.gestion.empresa.backend.gestion_empresa.models.Usuarios;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.AutenticacionServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.validation.RespuestaLogin;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/Auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {


    private final AutenticacionServiceImpl servicio;
    @PostMapping(value = "/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Login loginUserDto) {
        Optional<AuthRespuesta> respuesta = servicio.login(loginUserDto);
        System.out.println(respuesta);
        if (respuesta.isEmpty()) {
            System.out.println("no existe");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("ok", false, "mensaje", "Usuario no existe"));
        } else if (respuesta.get().getToken().equals("no coincide")) {


        System.out.println("no coincide");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("ok", false, "mensaje", "Credenciales no coinciden"));

    }
        System.out.println("ok");
        return  ResponseEntity.status(HttpStatus.OK).body(Map.of("ok",true, "mensaje",respuesta.get().getToken())) ;
    }


    @PostMapping(value = "/registro")
    public ResponseEntity<AuthRespuesta> registro(@RequestBody RegistroUsuarios registro){
        return ResponseEntity.ok(servicio.registro(registro));
    }


}
