package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.repositories.DuracionServicioPrestadoRepository;
import com.gestion.empresa.backend.gestion_empresa.services.DuracionServicioPrestadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: alexxus
 * Created on: 21/10/24
 */
@Service
public class DuracionServicioPrestadoServiceImpl implements DuracionServicioPrestadoService {

    @Autowired
    private  DuracionServicioPrestadoRepository duracionServicioPrestadoRepository;

    @Override
    public List<Object[]> obtenerTodosServicios(Long idServicio) {
        return duracionServicioPrestadoRepository.findByServicio(idServicio);
    }

}
