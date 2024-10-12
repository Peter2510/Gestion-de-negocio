package com.gestion.empresa.backend.gestion_empresa.services;


import com.gestion.empresa.backend.gestion_empresa.models.Empresa;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Author: gordillox
 * Created on: 5/10/24
 */
@Service
public interface EmpresaService {
    Optional<Empresa> findById(Long id);
    Empresa crearEmpresa(Empresa empresa);
    @Transactional
    void actualizarEmpresa(Empresa empresa);
}
