package com.gestion.empresa.backend.gestion_empresa.services;


import com.gestion.empresa.backend.gestion_empresa.models.Rol;
import com.gestion.empresa.backend.gestion_empresa.models.TipoAsignacionCita;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Author: gordillox
 * Created on: 5/10/24
 */
@Service
public interface TipoAsignacionCitaService {

    Optional<TipoAsignacionCita> buscarPorNombre(String tipo);
    TipoAsignacionCita crearTipoAsignacionCita(TipoAsignacionCita tipoAsignacionCita);
    List<TipoAsignacionCita> obtenerTipoAsignacionCitaRegistrados();
    Optional<TipoAsignacionCita> buscarPorId(Long id);

}
