package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.models.CategoriaServicio;
import com.gestion.empresa.backend.gestion_empresa.repositories.CategoriaServicioRepository;
import com.gestion.empresa.backend.gestion_empresa.services.CategoriaServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: alexxus
 * Created on: 13/10/24
 */
@Service
public class CategoriaServicioServiceImpl implements CategoriaServicioService {

    @Autowired
    private CategoriaServicioRepository categoriaServicioRepository;


    @Override
    public List<CategoriaServicio> obtenerTodo() {
        return this.categoriaServicioRepository.findAll();
    }

    @Override
    public CategoriaServicio ingresarCategoria(CategoriaServicio categoria) {
        //aca seria de generar las nuevas catagorias
        return  this.categoriaServicioRepository.save(categoria);
    }


}
