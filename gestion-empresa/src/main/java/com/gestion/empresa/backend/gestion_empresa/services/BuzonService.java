package com.gestion.empresa.backend.gestion_empresa.services;


import com.gestion.empresa.backend.gestion_empresa.models.Buzon;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
    Author: peterg
    Created on: 21/10/24
*/
@Service
public interface BuzonService {
    Buzon crearBuzon(Buzon buzon);
    Buzon actualizarBuzon(Buzon buzon);
    Optional<Buzon> buscarBuzonPorId(Long id);
    List<Buzon> buscarBuzonesPorUsuario(Long idUsuario);
}
