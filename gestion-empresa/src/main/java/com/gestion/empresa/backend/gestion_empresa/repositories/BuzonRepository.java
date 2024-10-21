package com.gestion.empresa.backend.gestion_empresa.repositories;


/*
    Author: peterg
    Created on: 21/10/24
*/

import com.gestion.empresa.backend.gestion_empresa.models.Buzon;
import com.gestion.empresa.backend.gestion_empresa.models.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuzonRepository extends JpaRepository<Buzon, Long> {
    List<Buzon> findByUsuario_Id(Long idUsuario);
}
