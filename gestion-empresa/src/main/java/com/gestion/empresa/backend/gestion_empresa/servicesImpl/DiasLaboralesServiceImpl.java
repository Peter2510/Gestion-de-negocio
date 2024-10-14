package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.models.CategoriaServicio;
import com.gestion.empresa.backend.gestion_empresa.models.DiasLaborales;
import com.gestion.empresa.backend.gestion_empresa.repositories.DiasLaboralesRepository;
import com.gestion.empresa.backend.gestion_empresa.services.DiasLaboralesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: alexxus
 * Created on: 13/10/24
 */

@Service
public class DiasLaboralesServiceImpl implements DiasLaboralesService {


    @Autowired
    private DiasLaboralesRepository diasLaboralesRepository;




    @Override
    public List<DiasLaborales> obtenerTodosDias() {
        return this.diasLaboralesRepository.findAll();

    }

    @Override
    public DiasLaborales ingresarDias(DiasLaborales diasLaborales) {
        return  this.diasLaboralesRepository.save(diasLaborales);

    }
}
