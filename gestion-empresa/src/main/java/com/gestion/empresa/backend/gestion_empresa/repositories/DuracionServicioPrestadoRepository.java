package com.gestion.empresa.backend.gestion_empresa.repositories;

import com.gestion.empresa.backend.gestion_empresa.models.*;
import com.gestion.empresa.backend.gestion_empresa.projections.PermisoRolProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DuracionServicioPrestadoRepository extends JpaRepository<DuracionServicioPrestado, Long> {

    @Query("SELECT dsp.duracion, dsp.nombre , sp.nombre , sp.precio " +
            "from DuracionServicioPrestado dsp " +
            "join ServicioPrestado sp on dsp.idServicioPrestado.id = sp.id " +
            "join ServiciosAsignado sa on sa.idServicioPrestado.id = sp.id " +
            "WHERE sa.idServicio.id = :idServicio")
    List<Object[]> findByServicio(@Param("idServicio") Long idServicio);


    DuracionServicioPrestado findAllByIdServicioPrestado(ServicioPrestado idServicioPrestado); // Busca por el objeto Servicios






}
