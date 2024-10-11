package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.models.Usuarios;
import com.gestion.empresa.backend.gestion_empresa.repositories.GeneroRepository;
import com.gestion.empresa.backend.gestion_empresa.repositories.UsuarioRepository;
import com.gestion.empresa.backend.gestion_empresa.services.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Author: alexxus
 * Created on: 11/10/24
 */

@Service

public class UsuarioServiceImpl implements UsuarioServicio {

    //injeccion
    @Autowired
    private UsuarioRepository usuarioRepository;




    @Override
    public Optional<Usuarios> findByPersonaCui(Long cui) {
        Optional<Usuarios> usuarioObtenido = this.usuarioRepository.findByPersonaCui(cui);
        if (usuarioObtenido.isPresent()) {
            return usuarioObtenido;
        }
        return Optional.empty();
    }

    @Override
    public Optional<Usuarios> findById(Long id) {
        Optional<Usuarios> usuarioObtenido = this.usuarioRepository.findById(id);
        if (usuarioObtenido.isPresent()) {
            return usuarioObtenido;
        }
        return Optional.empty();
    }

    // metodos a implementar
    @Override
    public Optional<Usuarios> buscarNombre(String nombreUsuario) {
        return Optional.empty();
    }

    @Override
    public Optional<Usuarios> editarUsuario(Usuarios usuarios) {
        System.out.println("------"+ usuarios);

        // Busca
        Optional<Usuarios> usuarioExistente = usuarioRepository.findByNombreUsuario(usuarios.getNombreUsuario());


        System.out.println(usuarioExistente+"------"+ usuarios);
        // existe lo actualiza
        if (usuarioExistente.isPresent()) {
            Usuarios usuarioActualizado = usuarioExistente.get();

            // Actualiza los campos que deben cambiar
            usuarioActualizado.setPassword(usuarios.getPassword());

            // Actualiza los datos personales
            usuarioActualizado.getPersona().setNombre(usuarios.getPersona().getNombre());
            usuarioActualizado.getPersona().setNit(usuarios.getPersona().getNit());
            usuarioActualizado.getPersona().setNumero(usuarios.getPersona().getNumero());
            usuarioActualizado.getPersona().setCorreo(usuarios.getPersona().getCorreo());
            usuarioActualizado.getPersona().setDireccion(usuarios.getPersona().getDireccion());
            usuarioActualizado.getPersona().setTelefono(usuarios.getPersona().getTelefono());

            // Guarda los cambios
            usuarioRepository.save(usuarioActualizado);
            return Optional.of(usuarioActualizado);
        }

        // Si el usuario no existe, devuelve un Optional vacío
        return Optional.empty();
    }
}
