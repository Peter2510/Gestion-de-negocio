package com.gestion.empresa.backend.gestion_empresa.servicesImpl;


import com.gestion.empresa.backend.gestion_empresa.models.TipoAsignacionCita;
import com.gestion.empresa.backend.gestion_empresa.repositories.TipoAsignacionCitaRepository;
import com.gestion.empresa.backend.gestion_empresa.services.TipoAsignacionCitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Author: gordillox
 * Created on: 5/10/24
 */
@Service
public class TipoAsignacionCitaServiceImpl implements TipoAsignacionCitaService {

    @Autowired
    private TipoAsignacionCitaRepository tipoAsignacionCitaRepository;

    @Override
    public Optional<TipoAsignacionCita> buscarPorNombre(String tipo) {
        return tipoAsignacionCitaRepository.findByTipo(tipo);
    }

    @Override
    public TipoAsignacionCita crearTipoAsignacionCita(TipoAsignacionCita tipoAsignacionCita) {
        return tipoAsignacionCitaRepository.save(tipoAsignacionCita);
    }

    @Override
    public List<TipoAsignacionCita> obtenerTipoAsignacionCitaRegistrados() {
        return tipoAsignacionCitaRepository.findAll();
    }

    @Override
    public Optional<TipoAsignacionCita> buscarPorId(Long id) {
        return tipoAsignacionCitaRepository.findById(id);
    }

}
