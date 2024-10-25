package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.models.CategoriaServicio;
import com.gestion.empresa.backend.gestion_empresa.models.EstadoCita;
import com.gestion.empresa.backend.gestion_empresa.repositories.EstadoCitaRepository;
import com.gestion.empresa.backend.gestion_empresa.services.EstadoCitaService;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Author: alexxus
 * Created on: 24/10/24
 */

@Service
public class EstadoCitaServiceImpl implements EstadoCitaService {
    @Autowired
    private EstadoCitaRepository estadoCitaRepository;
    @Override
    public List<EstadoCita> obtenerTodo() {
        return this.estadoCitaRepository.findAll();
    }

    @Override
    public EstadoCita ingresarEstadoCita(EstadoCita estadoCita) {
        return  this.estadoCitaRepository.save(estadoCita);
    }

    public ResponseBackend registrarEstadoCita(EstadoCita estadoCita) {

        //crear la estado
        EstadoCita creacion = estadoCitaRepository.save(estadoCita);

        if(creacion==null){
            return new ResponseBackend(false, HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear la categoria");
        }

        return new ResponseBackend(true, HttpStatus.CREATED, "Categoria registrada correctamente");

    }
}
