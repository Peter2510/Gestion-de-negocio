package com.gestion.empresa.backend.gestion_empresa.servicesImpl;


import com.gestion.empresa.backend.gestion_empresa.models.Persona;
import com.gestion.empresa.backend.gestion_empresa.repositories.PersonaRepository;

import com.gestion.empresa.backend.gestion_empresa.services.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaServicioImpl implements PersonaService {

    @Autowired
    PersonaRepository personaRepository;



    @Override
    public Optional<Persona> buscarPorCorreo(String correo) {
        return personaRepository.findByCorreo(correo);
    }

    @Override
    public Optional<Persona> buscarPorNit(String nit) {
        return personaRepository.findByNit(nit);
    }

    @Override
    public Optional<Persona> buscarPorTelefono(String telefono) {
        return personaRepository.findByTelefono(telefono);
    }

    @Override
    public Persona guardarPersona(Persona persona) {
        return personaRepository.save(persona);
    }

    public List<Persona> listarPersonas(){
        return  personaRepository.findAll();
    }

}
