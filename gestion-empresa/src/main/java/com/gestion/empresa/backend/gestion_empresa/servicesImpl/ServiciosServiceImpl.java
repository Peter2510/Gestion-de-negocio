package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.models.Servicios;
import com.gestion.empresa.backend.gestion_empresa.repositories.JornadaPorDiaRepository;
import com.gestion.empresa.backend.gestion_empresa.repositories.ServiciosRepository;
import com.gestion.empresa.backend.gestion_empresa.services.ServiciosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: alexxus
 * Created on: 15/10/24
 */

@Service

public class ServiciosServiceImpl implements ServiciosService {

    @Autowired
    private ServiciosRepository serviciosRepository;

    @Override
    public Servicios crearServicio(Servicios servicios) {
        return  this.serviciosRepository.save(servicios);

    }
}
