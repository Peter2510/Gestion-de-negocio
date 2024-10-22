package com.gestion.empresa.backend.gestion_empresa.repositories;

import com.gestion.empresa.backend.gestion_empresa.models.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiciosAsignadoRepository extends JpaRepository<ServiciosAsignado, Long> {

    Optional<ServiciosAsignado> findByidServicioPrestado(ServicioPrestado idServicioPrestado);

}
