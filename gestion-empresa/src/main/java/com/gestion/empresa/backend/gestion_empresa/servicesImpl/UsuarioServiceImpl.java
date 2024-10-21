package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.dto.ActualizacionUsuarioAdminDTO;
import com.gestion.empresa.backend.gestion_empresa.dto.RegistroUsuariosDTO;
import com.gestion.empresa.backend.gestion_empresa.models.Persona;
import com.gestion.empresa.backend.gestion_empresa.models.Rol;
import com.gestion.empresa.backend.gestion_empresa.models.Usuarios;
import com.gestion.empresa.backend.gestion_empresa.repositories.GeneroRepository;
import com.gestion.empresa.backend.gestion_empresa.repositories.PersonaRepository;
import com.gestion.empresa.backend.gestion_empresa.repositories.RolRepository;
import com.gestion.empresa.backend.gestion_empresa.repositories.UsuarioRepository;
import com.gestion.empresa.backend.gestion_empresa.services.UsuarioServicio;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Author: alexxus
 * Created on: 11/10/24
 */

@Service

public class UsuarioServiceImpl implements UsuarioServicio {

    //injeccion
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Override
    public Optional<Usuarios> buscarPorCui(Long cui) {
        Optional<Usuarios> usuarioObtenido = this.usuarioRepository.findByPersonaCui(cui);
        if (usuarioObtenido.isPresent()) {
            return usuarioObtenido;
        }
        return Optional.empty();
    }

    @Override
    public Optional<Usuarios> buscarPorId(Long id) {
        Optional<Usuarios> usuarioObtenido = this.usuarioRepository.findById(id);
        if (usuarioObtenido.isPresent()) {
            return usuarioObtenido;
        }
        return Optional.empty();
    }

    @Override
    public Optional<Usuarios> buscarNombreUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }


    @Transactional(rollbackOn = Throwable.class)
    public ResponseBackend actualizarUsuario(ActualizacionUsuarioAdminDTO usuario) {
        //validaciones previas
        ResponseBackend validacionResponse = validarActualizacion(usuario.getIdUsuario(), usuario);
        if (validacionResponse != null) {
            return validacionResponse;
        }

        try {
            //buscar usuario existente
            Usuarios usuarioExistente = usuarioRepository.findById(usuario.getIdUsuario())
                    .orElseThrow(() -> new RuntimeException("El usuario no se encuentra registrado"));

            //actualizar datos de la entidad Persona
            Persona personaExistente = usuarioExistente.getPersona();
            personaExistente.setCui(usuario.getPersona().getCui());
            personaExistente.setNombre(usuario.getPersona().getNombre());
            personaExistente.setNit(usuario.getPersona().getNit());
            personaExistente.setDireccion(usuario.getPersona().getDireccion());
            personaExistente.setTelefono(usuario.getPersona().getTelefono());
            personaExistente.setCorreo(usuario.getCorreo());
            personaExistente.setGenero(generoRepository.findById(usuario.getIdGenero())
                    .orElseThrow(() -> new RuntimeException("El género no se encuentra registrado")));

            //guardar los cambios en la entidad Persona
            personaRepository.save(personaExistente);

            //actualizar los datos de la entidad Usuarios
            usuarioExistente.setNombreUsuario(usuario.getNombreUsuario());

            usuarioExistente.setRol(rolRepository.findById(usuario.getIdRol())
                    .orElseThrow(() -> new RuntimeException("El rol no se encuentra registrado")));

            //guardar los cambios en la entidad Usuarios
            usuarioRepository.save(usuarioExistente);

            return new ResponseBackend(true, HttpStatus.OK, "Usuario actualizado exitosamente");

        } catch (Exception e) {
            System.out.println("Error al actualizar usuario: " + e.getMessage());
            return new ResponseBackend(false, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private ResponseBackend validarActualizacion(Long id, ActualizacionUsuarioAdminDTO validacion) {

        //validar nombre de usuario (si ha cambiado)
        usuarioRepository.findByNombreUsuario(validacion.getNombreUsuario())
                .ifPresent(usuario -> {
                    // Aquí usamos autoboxing para comparar el long con el Long
                    if (!Long.valueOf(usuario.getId()).equals(id)) {
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "El nombre de usuario ya está en uso");
                    }
                });

        //validar correo (si ha cambiado)
        personaRepository.findByCorreo(validacion.getCorreo())
                .ifPresent(persona -> {
                    if (!persona.getCui().equals(validacion.getPersona().getCui())) {
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo electrónico ya está registrado");
                    }
                });

        //validar NIT (si ha cambiado)
        personaRepository.findByNit(validacion.getPersona().getNit())
                .ifPresent(persona -> {
                    if (!persona.getCui().equals(validacion.getPersona().getCui())) {
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "El NIT ya está registrado");
                    }
                });

        //validar teléfono (si ha cambiado)
        personaRepository.findByTelefono(validacion.getPersona().getTelefono())
                .ifPresent(persona -> {
                    if (!persona.getCui().equals(validacion.getPersona().getCui())) {
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "El teléfono ya está registrado");
                    }
                });

        return null;
    }

    @Override
    public ResponseBackend listarUsuariosPorRol(Long idRol) {
        Optional<Rol> rol = rolRepository.findById(idRol);

        return rol.map(value -> {
            List<Usuarios> usuarios = usuarioRepository.findByRol(value);
            List<Map<String, Object>> filteredUsuarios = usuarios.stream()
                    .map(usuario -> {
                        Map<String, Object> filteredUser = new HashMap<>();
                        filteredUser.put("id", usuario.getId());
                        filteredUser.put("nombre_usuario", usuario.getNombreUsuario());
                        filteredUser.put("rol", usuario.getRol());
                        filteredUser.put("persona", usuario.getPersona());
                        filteredUser.put("activo", usuario.isActivo());
                        filteredUser.put("a2f_activo", usuario.isA2fActivo());
                        return filteredUser;
                    })
                    .collect(Collectors.toList());

            return new ResponseBackend(true, HttpStatus.OK, filteredUsuarios);
        }).orElseGet(() -> new ResponseBackend(false, HttpStatus.NOT_FOUND, "El rol no existe"));
    }

    @Override
    public ResponseBackend listarEmpleados(Long idRol) {
        Optional<Rol> rol = rolRepository.findById(idRol);

        return rol.map(value -> {
            List<Usuarios> usuarios = usuarioRepository.findByRolIdNot(idRol);
            List<Map<String, Object>> filteredUsuarios = usuarios.stream()
                    .map(usuario -> {
                        Map<String, Object> filteredUser = new HashMap<>();
                        filteredUser.put("id", usuario.getId());
                        filteredUser.put("nombre_usuario", usuario.getNombreUsuario());
                        filteredUser.put("rol", usuario.getRol());
                        filteredUser.put("persona", usuario.getPersona());
                        filteredUser.put("activo", usuario.isActivo());
                        filteredUser.put("a2f_activo", usuario.isA2fActivo());
                        return filteredUser;
                    })
                    .collect(Collectors.toList());

            return new ResponseBackend(true, HttpStatus.OK, filteredUsuarios);
        }).orElseGet(() -> new ResponseBackend(false, HttpStatus.NOT_FOUND, "El rol no existe"));
    }

}
