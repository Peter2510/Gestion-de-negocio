package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.repositories.JornadaPorDiaRepository;
import com.gestion.empresa.backend.gestion_empresa.repositories.PermisoRolRepository;
import com.gestion.empresa.backend.gestion_empresa.services.JornadaPorDiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: alexxus
 * Created on: 15/10/24
 */


@Service

public class JornadaPorDiaServiceImpl implements JornadaPorDiaService {
    @Autowired
    private JornadaPorDiaRepository jornadaPorDiaRepository;
}
