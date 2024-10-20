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
    public ResponseBackend editarUsuario(ActualizacionUsuarioAdminDTO actualizacionUsuario) {

//        //validaciones
//        ResponseBackend validacionResponse = validarRegistro(actualizacionUsuario);
//        if (validacionResponse != null) {
//            return validacionResponse;
//        }
//
//        try {
//            //crear Persona
//            Persona persona = new Persona();
//            persona.setCui(actualizacionUsuario.getPersona().getCui());
//            persona.setNombre(actualizacionUsuario.getPersona().getNombre());
//            persona.setNit(actualizacionUsuario.getPersona().getNit());
//            persona.setDireccion(actualizacionUsuario.getPersona().getDireccion());
//            persona.setTelefono(actualizacionUsuario.getPersona().getTelefono());
//            persona.setCorreo(actualizacionUsuario.getCorreo());
//            persona.setGenero(generoRepository.findById(actualizacionUsuario.getIdGenero())
//                    .orElseThrow(() -> new RuntimeException("El genero no se encuentra registrado")));
//
//            //guardar persona
//            Persona nuevaPersona = personaRepository.save(persona);
//
//            //crear Usuario
//            Usuarios usuario = new Usuarios();
//            usuario.setNombreUsuario(actualizacionUsuario.getNombreUsuario());
//            usuario.setRol(rolRepository.findById(actualizacionUsuario.getIdRol())
//                    .orElseThrow(() -> new RuntimeException("El rol no se encuentra registrado")));
//            usuario.setPersona(nuevaPersona); // Cambia a nuevaPersona
//            usuario.setActivo(actualizacionUsuario.isActivo());
//
//            //guardar usuario
//            usuarioRepository.save(usuario);
//
//            //exito zzzzz
//            return new ResponseBackend(true, HttpStatus.CREATED, "Usuario creado exitosamente");
//
//        } catch (Exception e) {
//            System.out.println("Error al registrar usuario: " + e.getMessage());
//            // La transacción se marca automáticamente para rollback
//            return new ResponseBackend(false, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
//        }

        return new ResponseBackend(false, HttpStatus.INTERNAL_SERVER_ERROR, "e.getMessage()");
    }


//    private ResponseBackend validarRegistro(ActualizacionUsuarioAdminDTO registro) {
//
//        if (usuarioRepository.findByNombreUsuario(registro.getNombreUsuario()).isPresent()) {
//            return new ResponseBackend(false, HttpStatus.CONFLICT, "El nombre de usuario ya está en uso");
//        }
//
//        if (personaRepository.findByCorreo(registro.getCorreo()).isPresent()) {
//            return new ResponseBackend(false, HttpStatus.CONFLICT, "El correo electrónico ya se encuentra registrado");
//        }
//
//        if (personaRepository.findByCui(registro.getPersona().getCui()).isPresent()) {
//            return new ResponseBackend(false, HttpStatus.CONFLICT, "El CUI ya se encuentra registrado");
//        }
//
//        if (personaRepository.findByNit(registro.getPersona().getNit()).isPresent()) {
//            return new ResponseBackend(false, HttpStatus.CONFLICT, "El NIT ya se encuentra registrado");
//        }
//
//        if (personaRepository.findByTelefono(registro.getPersona().getTelefono()).isPresent()) {
//            return new ResponseBackend(false, HttpStatus.CONFLICT, "El teléfono ya se encuentra registrado");
//        }
//
//        return null;
//    }

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
