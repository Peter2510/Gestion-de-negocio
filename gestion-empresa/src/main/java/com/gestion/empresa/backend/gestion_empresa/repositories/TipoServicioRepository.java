package com.gestion.empresa.backend.gestion_empresa.repositories;

import com.gestion.empresa.backend.gestion_empresa.models.TipoServicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Author: gordillox
 * Created on: 4/10/24
 */

public interface TipoServicioRepository extends JpaRepository<TipoServicio, Long> {

    Optional<TipoServicio> findByNombre(String nombre);

}
