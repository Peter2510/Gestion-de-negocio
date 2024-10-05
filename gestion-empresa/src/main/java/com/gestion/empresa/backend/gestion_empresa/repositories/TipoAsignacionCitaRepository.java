package com.gestion.empresa.backend.gestion_empresa.repositories;


import com.gestion.empresa.backend.gestion_empresa.models.TipoAsignacionCita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Author: gordillox
 * Created on: 5/10/24
 */

public interface TipoAsignacionCitaRepository extends JpaRepository<TipoAsignacionCita, Long> {

    Optional<TipoAsignacionCita> findByTipo(String nombre);

}

