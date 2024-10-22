package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.repositories.JornadaServicioRepository;
import com.gestion.empresa.backend.gestion_empresa.services.JornadaServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: alexxus
 * Created on: 21/10/24
 */

@Service
public class JornadaServicioServiceImpl implements JornadaServicioService {

    @Autowired
    private JornadaServicioRepository jornadaServicioRepository;
    @Override
    public List<Object[]> obtenerTodasJornadas(Long idServicio) {
        System.out.println(idServicio);
        return jornadaServicioRepository.findByServicio(idServicio);

    }
}
