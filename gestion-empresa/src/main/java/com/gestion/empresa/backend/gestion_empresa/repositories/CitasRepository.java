package com.gestion.empresa.backend.gestion_empresa.repositories;

import com.gestion.empresa.backend.gestion_empresa.dto.AnioCitasDTO;
import com.gestion.empresa.backend.gestion_empresa.dto.MesCitasDTO;
import com.gestion.empresa.backend.gestion_empresa.dto.SemanaCitasDTO;
import com.gestion.empresa.backend.gestion_empresa.models.Citas;
import com.gestion.empresa.backend.gestion_empresa.models.Servicios;
import com.gestion.empresa.backend.gestion_empresa.models.ServiciosAsignado;
import com.gestion.empresa.backend.gestion_empresa.models.Usuarios;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CitasRepository extends JpaRepository<Citas, Long> {

    List<Citas> findAllByIdUsuario(Usuarios idUsuarios);

    //citas por semana y año
    @Query("SELECT FUNCTION('WEEK', c.fechaHoraInicio) AS semana, COUNT(c) AS total " +
            "FROM Citas c WHERE FUNCTION('YEAR', c.fechaHoraInicio) = ?1 " +
            "GROUP BY FUNCTION('WEEK', c.fechaHoraInicio)")
    List<SemanaCitasDTO> countCitasPorSemana(int anio);

    //citas por mes y año
    @Query("SELECT FUNCTION('MONTH', c.fechaHoraInicio) AS mes, COUNT(c) AS total " +
            "FROM Citas c WHERE FUNCTION('YEAR', c.fechaHoraInicio) = ?1 " +
            "GROUP BY FUNCTION('MONTH', c.fechaHoraInicio)")
    List<MesCitasDTO> countCitasPorMes(int anio);

    // Citas por año
    @Query("SELECT FUNCTION('YEAR', c.fechaHoraInicio) AS anio, COUNT(c) AS total " +
            "FROM Citas c " +
            "WHERE FUNCTION('YEAR', c.fechaHoraInicio) = :anio " +
            "GROUP BY FUNCTION('YEAR', c.fechaHoraInicio) " +
            "ORDER BY FUNCTION('YEAR', c.fechaHoraInicio) DESC")
    List<Tuple> countCitasPorAnio(@Param("anio") int anio);


    //citas entre dos fechas específicas
    @Query("SELECT c FROM Citas c WHERE c.fechaHoraInicio BETWEEN :inicio AND :fin")
    List<Citas> findCitasByFechaBetween(LocalDateTime inicio, LocalDateTime fin);

    @Query("SELECT c.idServicio AS servicio, COUNT(c) AS totalCitas " +
            "FROM Citas c GROUP BY c.idServicio")
    List<Object[]> findCitasPorServicio();

    @Query("SELECT c.idEstadoCita AS estado, COUNT(c) AS totalCitas " +
            "FROM Citas c GROUP BY c.idEstadoCita")
    List<Object[]> findCitasPorEstado();
}
