package com.gestion.empresa.backend.gestion_empresa.repositories;


import com.gestion.empresa.backend.gestion_empresa.models.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Author: gordillox
 * Created on: 5/10/24
 */

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

}
