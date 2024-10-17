package com.gestion.empresa.backend.gestion_empresa.repositories;

import com.gestion.empresa.backend.gestion_empresa.models.Persona;
import com.gestion.empresa.backend.gestion_empresa.models.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
    Optional<Persona> findByCorreo(String correo);
    Optional<Persona> findByCui(Long cui);
    Optional<Persona> findByNit(String nit);
    Optional<Persona> findByTelefono(String telefono);

}
