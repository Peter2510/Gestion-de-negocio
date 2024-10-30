package com.gestion.empresa.backend.gestion_empresa.repositories;

import com.gestion.empresa.backend.gestion_empresa.models.PermisoRol;
import com.gestion.empresa.backend.gestion_empresa.models.Servicios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiciosRepository extends JpaRepository<Servicios, Long> {

    @Query("SELECT COUNT(s) FROM Servicios s")
    Long cantidadServiciosRegistrados();
}
