package com.gestion.empresa.backend.gestion_empresa.controllers;


import com.gestion.empresa.backend.gestion_empresa.entities.Rol;
import com.gestion.empresa.backend.gestion_empresa.repositories.RolRepository;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.RolServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/rol")
@Validated
public class RolController {

    @Autowired
    private RolServiceImpl rolService;

    @Autowired
    private RolRepository rolRepository;

    @GetMapping
    public ResponseEntity<List<Rol>> obtenerRolesRegistrados() {
        List<Rol> roles = rolService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(roles);
    }

    @PostMapping("/crearRol")
    public ResponseEntity<String> crearRol(@Valid @RequestBody Rol rol) {

        Optional<Rol> rolExistente = rolRepository.findByNombre(rol.getNombre());

        if (rolExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El rol con nombre " + rol.getNombre() + " ya existe");
        }

        Rol nuevoRol = rolService.crearRol(rol);

        if (nuevoRol != null) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("El rol" + nuevoRol.getNombre() + "creado con éxito: ");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al crear el rol.");
        }
    }

}