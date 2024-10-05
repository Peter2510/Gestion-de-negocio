package com.gestion.empresa.backend.gestion_empresa.servicesImpl;


import com.gestion.empresa.backend.gestion_empresa.models.TipoServicio;
import com.gestion.empresa.backend.gestion_empresa.repositories.TipoServicioRepository;
import com.gestion.empresa.backend.gestion_empresa.services.TipoServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Author: gordillox
 * Created on: 4/10/24
 */

@Service
public class TipoServicioServiceImpl implements TipoServicioService {

    @Autowired
    private TipoServicioRepository tipoServicioRepository;

    @Override
    public Optional<TipoServicio> buscarPorNombre(String nombre) {
        return tipoServicioRepository.findByNombre(nombre);
    }

    @Override
    public TipoServicio crearTipoServicio(TipoServicio tipoServicio) {
        return tipoServicioRepository.save(tipoServicio);
    }

    @Override
    public List<TipoServicio> obtenerTipoServiciosRegistrados() {
        return tipoServicioRepository.findAll();
    }
}