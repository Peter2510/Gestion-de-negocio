package com.gestion.empresa.backend.gestion_empresa.repositories;

import com.gestion.empresa.backend.gestion_empresa.models.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleCitaRepository extends JpaRepository<DetalleCita, Long> {

    DetalleCita findAllByIdCita(Citas idCitas);
}
