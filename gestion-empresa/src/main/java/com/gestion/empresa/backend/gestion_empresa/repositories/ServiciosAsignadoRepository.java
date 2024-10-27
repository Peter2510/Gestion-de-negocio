package com.gestion.empresa.backend.gestion_empresa.repositories;

import com.gestion.empresa.backend.gestion_empresa.models.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServiciosAsignadoRepository extends JpaRepository<ServiciosAsignado, Long> {

    Optional<ServiciosAsignado> findByidServicioPrestado(ServicioPrestado idServicioPrestado);

    List<ServicioPrestado> findAllById(Long idServicio);
  //  List<ServicioPrestado> findAllByIdServicio(Long idServicio);

    //List<ServiciosAsignado> findAllByIdServicio(Long idServicio);

    List<ServiciosAsignado> findAllByIdServicio(Servicios idServicio); // Busca por el objeto Servicios

    List<ServiciosAsignado> findAllByIdServicio_Id(Long idServicio);
}

