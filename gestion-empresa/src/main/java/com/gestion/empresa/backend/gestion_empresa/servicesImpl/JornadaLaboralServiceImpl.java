package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.models.JornadaLaboral;
import com.gestion.empresa.backend.gestion_empresa.repositories.CategoriaServicioRepository;
import com.gestion.empresa.backend.gestion_empresa.repositories.JornadaLaboralRepository;
import com.gestion.empresa.backend.gestion_empresa.services.JornadaLaboralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: alexxus
 * Created on: 13/10/24
 */

@Service
public class JornadaLaboralServiceImpl implements JornadaLaboralService {
    @Autowired
    private JornadaLaboralRepository jornadaLaboralRepository;

    @Override
    public JornadaLaboral ingresarJornada(JornadaLaboral jornadaLaboral) {

        return  this.jornadaLaboralRepository.save(jornadaLaboral);

    }
}
