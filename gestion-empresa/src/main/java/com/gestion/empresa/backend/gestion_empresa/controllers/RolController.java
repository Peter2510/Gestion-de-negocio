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
import java.util.Optional;

@RestController
@RequestMapping(path = "/rol")
@Validated
public class RolController {

    @Autowired
    private RolServiceImpl rolService;

    @Autowired
    private RolRepository rolRepository;

    @GetMapping("/obtenerRoles")
    public ResponseEntity<List<Rol>> obtenerRolesRegistrados() {
        List<Rol> roles = rolService.findAll();
        return ResponseEntity.ok(roles);  // Simplificado utilizando ResponseEntity.ok() que ya devuelve 200 OK.
    }

//    @GetMapping("/{id}")
//    public void obtenerRolPorId(@PathVariable("id") String id, @RequestBody Long idRol) {
//
//    }

    @PostMapping("/crearRol")
    public ResponseEntity<String> crearRol(@Valid @RequestBody Rol rol) {
        return rolRepository.findByNombre(rol.getNombre())
                .map(existingRole -> ResponseEntity.badRequest()
                        .body("El rol con nombre " + rol.getNombre() + " ya existe"))
                .orElseGet(() -> {
                    Rol nuevoRol = rolService.crearRol(rol);
                    return nuevoRol != null
                            ? ResponseEntity.status(HttpStatus.CREATED)
                            .body("El rol " + nuevoRol.getNombre() + " creado con éxito")
                            : ResponseEntity.badRequest().body("Error al crear el rol.");
                });
    }
}