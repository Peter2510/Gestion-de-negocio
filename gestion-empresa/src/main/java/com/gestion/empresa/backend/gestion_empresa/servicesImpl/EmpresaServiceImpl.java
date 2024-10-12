package com.gestion.empresa.backend.gestion_empresa.servicesImpl;


import com.gestion.empresa.backend.gestion_empresa.models.Empresa;
import com.gestion.empresa.backend.gestion_empresa.repositories.EmpresaRepository;
import com.gestion.empresa.backend.gestion_empresa.services.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Author: gordillox
 * Created on: 5/10/24
 */
@Service
public class EmpresaServiceImpl implements EmpresaService {
    @Autowired
    private EmpresaRepository empresaRepository;


    @Override
    public Optional<Empresa> findById(Long id) {
        return empresaRepository.findById(id);
    }

    @Override
    public Empresa crearEmpresa(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    @Override
    public void actualizarEmpresa(Empresa empresa) {
        empresaRepository.findById(empresa.getId()).map(empresaActual -> {
            empresaActual.setNombre(empresa.getNombre());
            empresaActual.setDescripcion(empresa.getDescripcion());
            empresaActual.setEmail(empresa.getEmail());
            empresaActual.setTelefono(empresa.getTelefono());
            empresaActual.setDireccion(empresa.getDireccion());
            empresaActual.setLogo(empresa.getLogo());
            empresaActual.setTipoAsignacionCita(empresa.getTipoAsignacionCita());
            empresaActual.setTipoServicio(empresa.getTipoServicio());

            return empresaRepository.save(empresaActual);
        });
    }

}
