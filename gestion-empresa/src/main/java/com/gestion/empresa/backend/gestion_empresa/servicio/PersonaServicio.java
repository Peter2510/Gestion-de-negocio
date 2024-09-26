package com.gestion.empresa.backend.gestion_empresa.servicio;


import com.gestion.empresa.backend.gestion_empresa.models.Persona;
import com.gestion.empresa.backend.gestion_empresa.repositorio.PersonaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonaServicio {

    private final PersonaRepositorio servicioPersonas;



    @Autowired
    public PersonaServicio(PersonaRepositorio servicioPersonas) {
        this.servicioPersonas = servicioPersonas;
    }

    // para agregar nuevas personas

    public Persona agregarPersonas(Persona persona) {

        persona.setCui(123456);
        return  servicioPersonas.save(persona);
    }

    // para listar a las personas
    public List<Persona> listarPersonas(){
        return  servicioPersonas.findAll();
    }

}
