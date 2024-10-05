package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.models.Genero;
import com.gestion.empresa.backend.gestion_empresa.models.Rol;
import com.gestion.empresa.backend.gestion_empresa.repositories.GeneroRepository;
import com.gestion.empresa.backend.gestion_empresa.repositories.RolRepository;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.GeneroServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.RolServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Genero")
@Validated
@CrossOrigin(origins = "http://localhost:4200")
public class GeneroController {

    //aca mapero para obtener todo
    @Autowired
    private GeneroServiceImpl generoService;

    @Autowired
    private GeneroRepository generoRepository;


    //ruta de obtener todo aca estara en el defecto de Genero
    @GetMapping
    public ResponseEntity<List<Genero>> obtenerGenerosRegistrados() {
        List<Genero> generos = generoService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(generos);
    }
}
