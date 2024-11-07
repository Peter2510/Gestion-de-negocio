package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.dto.*;
import com.gestion.empresa.backend.gestion_empresa.models.Usuarios;
import com.gestion.empresa.backend.gestion_empresa.services.PersonaService;
import com.gestion.empresa.backend.gestion_empresa.services.UsuarioServicio;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.AutenticacionServiceImpl;

import com.gestion.empresa.backend.gestion_empresa.servicesImpl.PersonaServicioImpl;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.UsuarioServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/Auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {


    @Autowired
    private final AutenticacionServiceImpl autenticacionService;

    @Autowired
    private final PersonaServicioImpl personaServicio;

    @Autowired
    private final UsuarioServiceImpl usuarioService;


    @PostMapping(value = "/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Login loginUserDto) {
        Optional<AuthRespuesta> respuesta = autenticacionService.login(loginUserDto);
        System.out.println(respuesta);
        System.out.println("hola");
        if (respuesta.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("ok", false, "mensaje", "El usuario no esta registrado"));

        } else if (respuesta.get().getToken().equals("no coincide")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("ok", false, "mensaje", "Credenciales incorrectas"));
        }

        if(respuesta.get().getStatus() != null){
            if(respuesta.get().getStatus().equals(HttpStatus.INTERNAL_SERVER_ERROR)){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("ok", false, "mensaje", respuesta.get().getToken()));
            }

            if(respuesta.get().getStatus().equals(HttpStatus.OK)){
                return ResponseEntity.status(HttpStatus.OK).body(Map.of("ok", true, "mensaje", "Autenticacion doble factor activa, "+respuesta.get().getToken(),
                        "idUsuario",respuesta.get().getIdUsuario()));
            }
        }



        return  ResponseEntity.status(HttpStatus.OK).body(Map.of("ok",true, "mensaje",respuesta.get().getToken())) ;
    }

    @PostMapping("/validar-codigo-a2f")
    public ResponseEntity<Map<String, Object>> validarCodigo(@RequestBody A2FDTO validacion){

        ResponseBackend response = autenticacionService.validacionCodigoA2F(validacion.getIdUsuario(), validacion.getCodigo());
        System.out.println(response);
        return ResponseEntity.status(response.getStatus()).body(
                response.getOk()
                        ? Map.of("ok", true, "mensaje", "Código válido", "token", response.getMensaje())
                        : Map.of("ok", false , "mensaje", response.getMensaje())
        );

    }


    @PostMapping(value = "/registro")
    public ResponseEntity<Map<String, Object>> registro(@RequestBody RegistroUsuariosDTO registro){
        ResponseBackend respuesta = autenticacionService.registrarUsuario(registro);
        return ResponseEntity.status(respuesta.getStatus()).body(Map.of("ok",respuesta.getOk(), "mensaje",respuesta.getMensaje()));
    }


}
