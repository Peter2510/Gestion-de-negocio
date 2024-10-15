package com.gestion.empresa.backend.gestion_empresa.servicesImpl;


import com.gestion.empresa.backend.gestion_empresa.dto.PermisoRolDTO;
import com.gestion.empresa.backend.gestion_empresa.models.Permiso;
import com.gestion.empresa.backend.gestion_empresa.models.PermisoRol;
import com.gestion.empresa.backend.gestion_empresa.models.Rol;
import com.gestion.empresa.backend.gestion_empresa.projections.PermisoRolProjection;
import com.gestion.empresa.backend.gestion_empresa.repositories.PermisoRepository;
import com.gestion.empresa.backend.gestion_empresa.repositories.PermisoRolRepository;
import com.gestion.empresa.backend.gestion_empresa.repositories.RolRepository;
import com.gestion.empresa.backend.gestion_empresa.services.PermisoRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Author: gordillox
 * Created on: 14/10/24
 */
@Service
public class PermisoRolServiceImpl implements PermisoRolService {

    @Autowired
    private PermisoRolRepository permisoRolRepository;

    @Autowired
    private PermisoRepository permisoRepository;

    @Autowired
    private RolRepository rolRepository;

    @Override
    public PermisoRol crearPermisoRol(PermisoRol permisoRol) {
        return permisoRolRepository.save(permisoRol);
    }

    @Override
    public List<PermisoRol> obtenerRegistros() {
        return permisoRolRepository.findAll();
    }

    @Override
    public Optional<PermisoRol> buscarPorId(Long id) {
        return permisoRolRepository.findById(id);
    }

    @Override
    public PermisoRol actualizarPermisoRol(PermisoRol permisoRol) {
        return permisoRolRepository.save(permisoRol);
    }

    @Override
    public List<PermisoRolProjection> obtenerPermisosPorRol(Long idRol) {
        return permisoRolRepository.findByRolId(idRol);
    }


    @Override
    public void actualizarPermisosRol(List<PermisoRolDTO> nuevosPermisos) {
        // Obteniendo el ID del rol
        Long idRol = nuevosPermisos.get(0).getIdRol();

        // Obteniendo los permisos actuales del rol
        List<PermisoRolProjection> permisosActuales = permisoRolRepository.findByRolId(idRol);

        // Obteniendo los IDs de los permisos actuales
        List<Long> idsActuales = permisosActuales.stream()
                .map(PermisoRolProjection::getPermisoId)
                .collect(Collectors.toList());

        // Obteniendo los IDs de los nuevos permisos
        List<Long> idsNuevos = nuevosPermisos.stream()
                .map(PermisoRolDTO::getIdPermiso)
                .collect(Collectors.toList());

        // Eliminando permisos que ya no están en la lista nueva
        for (Long idActual : idsActuales) {
            if (!idsNuevos.contains(idActual)) {
                // Eliminar el PermisoRol correspondiente, no solo por ID de permiso
                permisoRolRepository.deleteByRolIdAndPermisoId(idRol, idActual);
            }
        }

        // Agregando nuevos permisos que no estaban en la lista anterior
        for (PermisoRolDTO nuevoPermiso : nuevosPermisos) {
            if (!idsActuales.contains(nuevoPermiso.getIdPermiso())) {
                PermisoRol permisoRol = new PermisoRol();
                permisoRol.setRol(rolRepository.findById(nuevoPermiso.getIdRol()).orElseThrow(() -> new RuntimeException("Rol no encontrado")));
                permisoRol.setPermiso(permisoRepository.findById(nuevoPermiso.getIdPermiso()).orElseThrow(() -> new RuntimeException("Permiso no encontrado")));
                permisoRolRepository.save(permisoRol);
            }
        }
    }

}
