package com.gestion.empresa.backend.gestion_empresa.servicesImpl;


import com.gestion.empresa.backend.gestion_empresa.models.Permiso;
import com.gestion.empresa.backend.gestion_empresa.repositories.PermisoRepository;
import com.gestion.empresa.backend.gestion_empresa.services.PermisoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Author: gordillox
 * Created on: 13/10/24
 */

@Service
public class PermisoServiceImpl implements PermisoService {

    @Autowired
    private PermisoRepository permisoRepository;


    @Override
    public Permiso crearPermiso(Permiso permiso) {
        return permisoRepository.save(permiso);
    }

    @Override
    public Optional<Permiso> buscarPorId(Long id) {
        return permisoRepository.findById(id);
    }

    @Override
    public List<Permiso> obtenerPermisosRegistrados() {
        return permisoRepository.findAll();
    }

    @Override
    public Permiso actualizarPermiso(Permiso permiso) {
        return permisoRepository.save(permiso);
    }

    @Override
    public Optional<Permiso> buscarPermisoPorNombre(String nombre) {
       return permisoRepository.findByNombre(nombre);
    }
}
