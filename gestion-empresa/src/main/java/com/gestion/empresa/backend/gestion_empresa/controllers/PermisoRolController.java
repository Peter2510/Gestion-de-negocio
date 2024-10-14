package com.gestion.empresa.backend.gestion_empresa.controllers;


import com.gestion.empresa.backend.gestion_empresa.dto.PermisoRolDTO;
import com.gestion.empresa.backend.gestion_empresa.models.Permiso;
import com.gestion.empresa.backend.gestion_empresa.models.PermisoRol;
import com.gestion.empresa.backend.gestion_empresa.models.Rol;
import com.gestion.empresa.backend.gestion_empresa.projections.PermisoRolProjection;
import com.gestion.empresa.backend.gestion_empresa.services.PermisoRolService;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.PermisoServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.RolServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Author: gordillox
 * Created on: 14/10/24
 */

@RestController
@RequestMapping("permiso-rol")
@Validated
public class PermisoRolController {

    @Autowired
    private PermisoRolService permisoRolService;

    @Autowired
    private PermisoServiceImpl permisoServiceImpl;

    @Autowired
    private RolServiceImpl rolServiceImpl;

    @PostMapping("crear-permiso-rol")
    public ResponseEntity<Map<String, Object>> crearPermisoRol(@RequestBody List<PermisoRolDTO> permisoRol){

        for(int i=0; i<permisoRol.size(); i++){

            Optional<Permiso> permiso = permisoServiceImpl.buscarPorId(permisoRol.get(i).getIdPermiso());

            if(permiso.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("ok", false, "mensaje", "No existe el permiso"));
            }

            Optional<Rol> rol = rolServiceImpl.buscarPorId(permisoRol.get(i).getIdRol());

            if(rol.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("ok", false, "mensaje", "No existe el rol"));
            }

            PermisoRol nuevoPermisoRol = new PermisoRol();
            nuevoPermisoRol.setRol(rol.get());
            nuevoPermisoRol.setPermiso(permiso.get());

            PermisoRol creado = permisoRolService.crearPermisoRol(nuevoPermisoRol);

            if(creado == null){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("ok", false, "mensaje", "Error al la asignacion"));
            }

        }

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("ok", true, "mensaje", "Asignacion exitosa"));

    }

    @GetMapping("obtener-permisos-roles")
    public ResponseEntity<Map<String, Object>> obtenerPermisosRoles(){
        List<PermisoRol> permisosRoles = permisoRolService.obtenerRegistros();
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("ok", false, "permisosRoles", permisosRoles));

    }

    @GetMapping("obtener-permiso-rol/{id}")
    public ResponseEntity<Map<String, Object>> obtenerPermisosRolesById(@PathVariable("id") Long id){
        List<PermisoRolProjection> permisoRol = permisoRolService.obtenerPermisosPorRol(id);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("ok", true, "permisos", permisoRol));
    }



}
