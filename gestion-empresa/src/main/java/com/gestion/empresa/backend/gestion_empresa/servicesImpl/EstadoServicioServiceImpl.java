package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.models.CategoriaServicio;
import com.gestion.empresa.backend.gestion_empresa.models.EstadoServicio;
import com.gestion.empresa.backend.gestion_empresa.repositories.CategoriaServicioRepository;
import com.gestion.empresa.backend.gestion_empresa.repositories.EstadoServicioRepository;
import com.gestion.empresa.backend.gestion_empresa.services.EstadoServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: alexxus
 * Created on: 15/10/24
 */

@Service
public class EstadoServicioServiceImpl implements EstadoServicioService {

    @Autowired
    private EstadoServicioRepository estadoServicioRepository;


    @Override
    public List<EstadoServicio> obtenerTodo() {
        return this.estadoServicioRepository.findAll();
    }

    @Override
    public EstadoServicio ingresarEstado(EstadoServicio estadoServicio) {
        //aca seria de generar las nuevas catagorias
        return  this.estadoServicioRepository.save(estadoServicio);
    }
}
