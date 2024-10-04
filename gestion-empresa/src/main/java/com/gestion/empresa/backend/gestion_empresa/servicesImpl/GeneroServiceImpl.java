package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.models.Genero;
import com.gestion.empresa.backend.gestion_empresa.repositories.GeneroRepository;
import com.gestion.empresa.backend.gestion_empresa.services.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
// obtiene todas las funciones posibles de la interfaz
public class GeneroServiceImpl implements GeneroService {

    //injeccion
    @Autowired
    private GeneroRepository generoRepository;



    @Override
    public List<Genero> findAll() {
        return generoRepository.findAll();
    }
}
