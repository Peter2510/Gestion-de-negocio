package com.gestion.empresa.backend.gestion_empresa.controllers;


import com.gestion.empresa.backend.gestion_empresa.dto.ActualizacionUsuarioAdminDTO;
import com.gestion.empresa.backend.gestion_empresa.dto.Login;
import com.gestion.empresa.backend.gestion_empresa.models.Usuarios;
import com.gestion.empresa.backend.gestion_empresa.security.JwtServicio;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.AutenticacionServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.RolServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.UsuarioServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import com.gestion.empresa.backend.gestion_empresa.validation.RespuestaLogin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
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


    @GetMapping(value = "/obtener-usuario/{id}")
    public  ResponseEntity<Object> obtenerUsuario(@PathVariable Long id){

        ResponseBackend usuarioIndividual = this.usuarioServiceImpl.buscarPorId(id);

        return ResponseEntity.status(usuarioIndividual.getStatus()).body(
                usuarioIndividual.getOk()
                        ? Map.of("ok", true, "usuario", usuarioIndividual.getData())
                        : Map.of("ok", false, "mensaje", usuarioIndividual.getMensaje())
        );

    }
    // para generar su edicion

    @PutMapping(value = "/actualizar-usuario")
    public ResponseEntity<Object> editarUsuario(@RequestBody ActualizacionUsuarioAdminDTO usuario) {
        ResponseBackend response = this.usuarioServiceImpl.actualizarUsuario( usuario);

        return ResponseEntity.status(response.getStatus()).body(
                response.getOk()
                        ? Map.of("ok", true, "mensaje", response.getMensaje())
                        : Map.of("ok", false, "mensaje", response.getMensaje())
        );

    }

    @GetMapping(value="/obtener-usuarios-por-rol/{idRol}")
    public ResponseEntity<Map<String, Object>> obtenerUsuariosPorId(@PathVariable Long idRol){

        ResponseBackend response = usuarioServiceImpl.listarUsuariosPorRol(idRol);

        return ResponseEntity.status(response.getStatus()).body(
                response.getOk()
                        ? Map.of("ok", true, "usuarios", response.getData())
                        : Map.of("ok", false, "mensaje", response.getMensaje())
        );
    }

    @GetMapping(value="/listar-empleados")
    public ResponseEntity<Map<String, Object>> obtenerEmpleadosRegistrados(){

        /*El id del rol que no quiero ver -> clientes */
        ResponseBackend response = usuarioServiceImpl.listarEmpleados(2L);

        return ResponseEntity.status(response.getStatus()).body(
                response.getOk()
                        ? Map.of("ok", true, "empleados", response.getData())
                        : Map.of("ok", false, "mensaje", response.getMensaje())
        );
    }

}
