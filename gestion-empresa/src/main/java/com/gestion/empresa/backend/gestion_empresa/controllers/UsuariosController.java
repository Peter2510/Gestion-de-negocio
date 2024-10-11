package com.gestion.empresa.backend.gestion_empresa.controllers;


import com.gestion.empresa.backend.gestion_empresa.dto.Login;
import com.gestion.empresa.backend.gestion_empresa.models.Usuarios;
import com.gestion.empresa.backend.gestion_empresa.security.JwtServicio;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.AutenticacionServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.RolServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.UsuarioServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.validation.RespuestaLogin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {
    private final JwtServicio jwtService;

    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;

    public UsuariosController(JwtServicio jwtService) {
        this.jwtService = jwtService;
    }
    @PostMapping(value = "/vista")
    public ResponseEntity<Object> ingreso(){


        return ResponseEntity.ok("desde usuario");
    }


    @GetMapping(value = "/ObtenerUsuario")
    public  ResponseEntity<Object> obtenerUsuario(@RequestParam long id){


        Optional<Usuarios> usuarioIndividual = this.usuarioServiceImpl.findById(id);

        if (usuarioIndividual.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(usuarioIndividual);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

    }
    // para generar su edicion

    @PutMapping(value = "/editarUsuario")
    public ResponseEntity<Object> editarUsuario(@RequestBody Usuarios usuario) {
        Optional<Usuarios> usuarioEditado = this.usuarioServiceImpl.editarUsuario( usuario);

        if (usuarioEditado.isPresent()) {
            return ResponseEntity.ok("Usuario actualizado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }


}
