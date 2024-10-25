package com.gestion.empresa.backend.gestion_empresa.repositories;

import com.gestion.empresa.backend.gestion_empresa.models.PermisoRol;
import com.gestion.empresa.backend.gestion_empresa.models.Servicios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiciosRepository extends JpaRepository<Servicios, Long> {

//    @Query("SELECT dsp.duracion, dsp.nombre , sp.nombre , sp.precio " +
//            "from DuracionServicioPrestado dsp " +
//            "join ServicioPrestado sp on dsp.idServicioPrestado.id = sp.id " +
//            "join ServiciosAsignado sa on sa.idServicioPrestado.id = sp.id " +
//            "WHERE sa.idServicio.id = :idServicio")
//    List<Object[]> findByIdUsuario(@Param("idUsuario") Long idUsuario);
}
