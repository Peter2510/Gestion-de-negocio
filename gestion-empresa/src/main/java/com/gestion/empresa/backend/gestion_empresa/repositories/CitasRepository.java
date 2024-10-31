package com.gestion.empresa.backend.gestion_empresa.repositories;

import com.gestion.empresa.backend.gestion_empresa.models.Citas;
import com.gestion.empresa.backend.gestion_empresa.models.Servicios;
import com.gestion.empresa.backend.gestion_empresa.models.ServiciosAsignado;
import com.gestion.empresa.backend.gestion_empresa.models.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CitasRepository extends JpaRepository<Citas, Long> {

    List<Citas> findAllByIdUsuario(Usuarios idUsuarios);
    List<Citas> findAllByIdServicio(Servicios idServicios);
}
