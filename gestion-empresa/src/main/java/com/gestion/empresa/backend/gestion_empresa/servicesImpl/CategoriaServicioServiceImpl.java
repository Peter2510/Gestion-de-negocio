package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.models.CategoriaServicio;
import com.gestion.empresa.backend.gestion_empresa.repositories.CategoriaServicioRepository;
import com.gestion.empresa.backend.gestion_empresa.services.CategoriaServicioService;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<CategoriaServicio> buscarPorTipo(String tipo) {
        return categoriaServicioRepository.findByTipo(tipo);
    }

    @Override
    public ResponseBackend buscarPorId(Long id) {

        Optional<CategoriaServicio> categoriaServicio = categoriaServicioRepository.findById(id);
        return categoriaServicio.map(servicio -> new ResponseBackend(true, HttpStatus.OK, servicio)).
                orElseGet(() -> new ResponseBackend(false, HttpStatus.NOT_FOUND, "La categoria no existe"));
    }

    public ResponseBackend registrarCategoria(CategoriaServicio categoria) {

        Optional<CategoriaServicio> busqueda = this.buscarPorTipo(categoria.getTipo());

        if (busqueda.isPresent()) {
            return new ResponseBackend(false, HttpStatus.CONFLICT, "La categoria "+ busqueda.get().getTipo() +" ya esta registrada");
        }

        //crear la categoria
        CategoriaServicio creacion = categoriaServicioRepository.save(categoria);

        if(creacion==null){
            return new ResponseBackend(false, HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear la categoria");
        }

        return new ResponseBackend(true, HttpStatus.CREATED, "Categoria registrada correctamente");

    }

    @Override
    public ResponseBackend actualizarCategoria(CategoriaServicio categoria) {

        Optional<CategoriaServicio> busqueda = categoriaServicioRepository.findById(categoria.getId());

        if (busqueda.isEmpty()) {
            return new ResponseBackend(false, HttpStatus.NOT_FOUND, "La categoria no esta registrada");
        }

        //actualizar la categoria
        categoriaServicioRepository.save(categoria);

        return new ResponseBackend(true, HttpStatus.OK, "Categoria actualizada correctamente");

    }

}
