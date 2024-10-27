package com.gestion.empresa.backend.gestion_empresa.controllers;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.gestion.empresa.backend.gestion_empresa.models.Genero;
import com.gestion.empresa.backend.gestion_empresa.models.Persona;
import com.gestion.empresa.backend.gestion_empresa.repositories.PersonaRepository;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.PersonaServicioImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class PersonaControllerTest {

    @InjectMocks
    private PersonaController personaController;

    @Mock
    private PersonaServicioImpl servicio;

    @Mock
    private PersonaRepository personaRepository;

    Persona p;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        p =  new Persona();
        p.setCui(1234567890123L);
        p.setNombre("Persona 1");
        p.setNit("123456789012");
        p.setCorreo("correo@correo.com");
        p.setDireccion("Dirección de la persona 1");
        p.setTelefono("1234567890");
        Genero g = new Genero();
        g.setId(1L);
        g.setGenero("Masculino");
        p.setGenero(g);
    }

    @Test
    void testObtenerPersonas() {
        List<Persona> listaPersonas = List.of(p);
        when(servicio.listarPersonas()).thenReturn(listaPersonas);

        ResponseEntity<List<Persona>> response = personaController.obtenerPersonas();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(listaPersonas, response.getBody());
    }


}
