package com.gestion.empresa.backend.gestion_empresa.controllers;


import com.gestion.empresa.backend.gestion_empresa.models.Rol;
import com.gestion.empresa.backend.gestion_empresa.repositories.RolRepository;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.RolServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/rol")
@Validated
public class RolController {

    @Autowired
    private RolServiceImpl rolService;

    @GetMapping("/obtener-roles")
    public ResponseEntity<Map<String, Object>> obtenerRolesRegistrados() {
        List<Rol> roles = rolService.obtenerRolesRegistrados();
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("ok", true, "roles", roles));
    }

    @PostMapping("/crear-rol")
    public ResponseEntity<Map<String, Object>> crearRol(@Valid @RequestBody Rol rol) {
        //verificar si el rol ya existe
        if (rolService.buscarPorNombre(rol.getNombre()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("ok", false, "mensaje", "El rol " + rol.getNombre() + " ya existe"));
        }

        //si no existe se crea el rol
        Rol rolCreado = rolService.crearRol(rol);

        //verificar si el rol fue creado
        if (rolCreado == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("ok", false, "mensaje", "Error al crear el rol"));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("ok", true, "mensaje", "Rol creado exitosamente"));
    }
}