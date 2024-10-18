package com.gestion.empresa.backend.gestion_empresa.controllers;


import com.gestion.empresa.backend.gestion_empresa.dto.PermisoRolDTO;
import com.gestion.empresa.backend.gestion_empresa.dto.RolPermisoDTO;
import com.gestion.empresa.backend.gestion_empresa.models.Permiso;
import com.gestion.empresa.backend.gestion_empresa.models.PermisoRol;
import com.gestion.empresa.backend.gestion_empresa.models.Rol;
import com.gestion.empresa.backend.gestion_empresa.repositories.RolRepository;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.PermisoRolServiceImpl;
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

    @Autowired
    private PermisoRolServiceImpl permisoRolService;

    @GetMapping("/obtener-roles")
    public ResponseEntity<Map<String, Object>> obtenerRolesRegistrados() {
        List<Rol> roles = rolService.obtenerRolesRegistrados();
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("ok", true, "roles", roles));
    }

    @PostMapping("/crear-rol")
    public ResponseEntity<Map<String, Object>> crearRol(@Validated @RequestBody RolPermisoDTO rol) {
        //verificar si el rol ya existe
        if (rolService.buscarPorNombre(rol.getNombre()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("ok", false, "mensaje", "El rol " + rol.getNombre() + " ya existe"));
        }

        Rol nuevoRol = new Rol();
        nuevoRol.setNombre(rol.getNombre());
        nuevoRol.setDescripcion(rol.getDescripcion());

        Rol rolCreado = rolService.crearRol(nuevoRol);

        if (rolCreado == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("ok", false, "mensaje", "Error al crear el rol"));
        }

        for(int i = 0; i<rol.getPermisos().size(); i++){

            Permiso permiso = new Permiso();
            permiso.setId(rol.getPermisos().get(i).getId());
            permiso.setNombre(rol.getPermisos().get(i).getNombre());

            PermisoRol permisoRol = new PermisoRol();
            permisoRol.setRol(rolCreado);
            permisoRol.setPermiso(permiso);

            permisoRolService.crearPermisoRol(permisoRol);

        }

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("ok", true, "mensaje", "Rol creado exitosamente"));
    }


//    @PostMapping("actualizar-permiso-rol/{rolId}")
//    public ResponseEntity<Map<String, Object>> actualizarPermisosRol(@PathVariable Long rolId, @RequestBody List<PermisoRolDTO> nuevosPermisos) {
//
//        //verificar existencia de rol
//        Optional<Rol> existenciaRol = rolServiceImpl.buscarPorId(rolId);
//
//        if(existenciaRol.isEmpty()){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("ok", false, "mensaje", "El rol no existe"));
//        }
//
//        permisoRolService.actualizarPermisosRol(nuevosPermisos);
//
//        return null;
//
//    }

}