package com.gestion.empresa.backend.gestion_empresa.servicesImpl;


import com.gestion.empresa.backend.gestion_empresa.models.PermisoRol;
import com.gestion.empresa.backend.gestion_empresa.repositories.PermisoRolRepository;
import com.gestion.empresa.backend.gestion_empresa.services.PermisoRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Author: gordillox
 * Created on: 14/10/24
 */
@Service
public class PermisoRolServiceImpl implements PermisoRolService {

    @Autowired
    private PermisoRolRepository permisoRolRepository;


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
}
