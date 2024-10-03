package com.gestion.empresa.backend.gestion_empresa.servicesImpl;


import com.gestion.empresa.backend.gestion_empresa.models.Persona;
import com.gestion.empresa.backend.gestion_empresa.repositories.PersonaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonaServicioImpl {

    private final PersonaRepository servicioPersonas;



    @Autowired
    public PersonaServicioImpl(PersonaRepository servicioPersonas) {
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
