package com.gestion.empresa.backend.gestion_empresa.services;


import com.gestion.empresa.backend.gestion_empresa.models.Persona;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Author: gordillox
 * Created on: 16/10/24
 */

@Service
public interface PersonaService {

    Optional<Persona> buscarPorCorreo(String correo);
    Optional<Persona> buscarPorNit(String nit);
    Optional<Persona> buscarPorTelefono(String telefono);
    Persona guardarPersona(Persona persona);
    List<Persona> listarPersonas();


}
