package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.entities.Rol;
import com.gestion.empresa.backend.gestion_empresa.repositories.RolRepository;
import com.gestion.empresa.backend.gestion_empresa.services.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;


    @Override
    public Rol buscarPorNombre(String nombre) {
        Optional<Rol> rolOptional = rolRepository.findByNombre(nombre);

        return rolOptional.orElse(null);
    }

    public List<Rol> findAll(){
        return rolRepository.findAll();
    }

    @Override
    public Rol crearRol(Rol rol) {
        return rolRepository.save(rol);
    }
}
