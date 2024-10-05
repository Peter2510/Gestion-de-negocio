package com.gestion.empresa.backend.gestion_empresa.services;

import com.gestion.empresa.backend.gestion_empresa.models.Rol;
import com.gestion.empresa.backend.gestion_empresa.models.TipoServicio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TipoServicioService {

    Optional<TipoServicio> buscarPorNombre(String nombre);
    TipoServicio crearTipoServicio(TipoServicio tipoServicio);
    List<TipoServicio> obtenerTipoServiciosRegistrados();

}
