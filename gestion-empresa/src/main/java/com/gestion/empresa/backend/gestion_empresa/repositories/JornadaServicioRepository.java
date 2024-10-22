package com.gestion.empresa.backend.gestion_empresa.repositories;

import com.gestion.empresa.backend.gestion_empresa.models.JornadaServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JornadaServicioRepository extends JpaRepository<JornadaServicio, Long> {

    @Query("    SELECT jl.nombre , jl.horaInicio , jl.horaFin , dl.nombre  from JornadaServicio js  join JornadaLaboral jl   on jl.id   = js.idJornadaDia.id " +
            "    join JornadaPorDia jpd  on jpd.idDiaLaboral.id  = jl.id" +
            "    join DiasLaborales dl  on dl.id  = jpd.idDiaLaboral.id" +
            "    WHERE js.idServicio.id  =:idServicio")
    List<Object[]> findByServicio(@Param("idServicio") Long idServicio);
}
