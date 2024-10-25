package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.models.Genero;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.GeneroServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GeneroControllerTest {

    @InjectMocks
    private GeneroController generoController;

    @Mock
    private GeneroServiceImpl generoService;

    private List<Genero> generos;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        generos = new ArrayList<>();
        generos.add(new Genero(1L, "Masculino"));
        generos.add(new Genero(2L, "Femenino"));
    }

    @Test
    void testObtenerGenerosRegistrados() {
        when(generoService.findAll()).thenReturn(generos);

        ResponseEntity<List<Genero>> response = generoController.obtenerGenerosRegistrados();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(generos, response.getBody());
    }
}