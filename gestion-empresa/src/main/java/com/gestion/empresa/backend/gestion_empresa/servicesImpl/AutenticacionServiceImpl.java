package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.dto.AuthRespuesta;
import com.gestion.empresa.backend.gestion_empresa.dto.Login;
import com.gestion.empresa.backend.gestion_empresa.dto.RegistroUsuariosDTO;
import com.gestion.empresa.backend.gestion_empresa.models.Genero;
import com.gestion.empresa.backend.gestion_empresa.models.Persona;
import com.gestion.empresa.backend.gestion_empresa.models.Rol;
import com.gestion.empresa.backend.gestion_empresa.models.Usuarios;
import com.gestion.empresa.backend.gestion_empresa.repositories.GeneroRepository;
import com.gestion.empresa.backend.gestion_empresa.repositories.PersonaRepository;
import com.gestion.empresa.backend.gestion_empresa.repositories.RolRepository;
import com.gestion.empresa.backend.gestion_empresa.repositories.UsuarioRepository;
import com.gestion.empresa.backend.gestion_empresa.security.JwtServicio;
import com.gestion.empresa.backend.gestion_empresa.services.AutenticacionServicie;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AutenticacionServiceImpl implements AutenticacionServicie {
    @Autowired
    private final UsuarioRepository userRepository;
    @Autowired
    private final PersonaRepository personaRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtServicio jwtServicio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Optional<AuthRespuesta> login(Login valor) {
        System.out.println(valor);

        // Buscar al usuario en la base de datos
        Optional<Usuarios> user = userRepository.findByNombreUsuario(valor.getNombreUsuario());
        if (user.isEmpty()) {
            System.out.println("vacio");
            return Optional.empty();

        }
        // Comparar la contraseña ingresada con la contraseña encriptada en la base de datos
        if (!passwordEncoder.matches(valor.getPassword(), user.get().getPassword())) {
            return Optional.ofNullable(AuthRespuesta.builder().token("no coincide").build());
        }
        // Si la autenticación es correcta, generar el token JWT
        String token = jwtServicio.obtenerToken(user.get());

        return Optional.ofNullable(AuthRespuesta.builder().token(token).build());
    }

    public Optional<AuthRespuesta> validarCredenciales(String password, Optional<Usuarios> usuarios) {
        if ((password == usuarios.get().getPassword())) {
            return (generacionToken(usuarios));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<AuthRespuesta> generacionToken(Optional<Usuarios> usuarios) {
        String token = jwtServicio.obtenerToken(usuarios.get());

        return Optional.ofNullable(AuthRespuesta.builder().token(token).build());
    }

    private Persona guardarPersona(Persona persona) {
        return personaRepository.save(persona);
    }

    private Usuarios guardarUsuario(Usuarios usuario) {
        return userRepository.save(usuario);
    }


    @Transactional(rollbackOn = Throwable.class)
    public ResponseBackend registrarUsuario(RegistroUsuariosDTO registro) {

        //validaciones
        ResponseBackend validacionResponse = validarRegistro(registro);
        if (validacionResponse != null) {
            return validacionResponse;
        }

        try {
            //crear Persona
            Persona persona = new Persona();
            persona.setCui(registro.getPersona().getCui());
            persona.setNombre(registro.getPersona().getNombre());
            persona.setNit(registro.getPersona().getNit());
            persona.setDireccion(registro.getPersona().getDireccion());
            persona.setTelefono(registro.getPersona().getTelefono());
            persona.setCorreo(registro.getCorreo());
            persona.setGenero(generoRepository.findById(registro.getIdGenero())
                    .orElseThrow(() -> new RuntimeException("El genero no se encuentra registrado")));

            //guardar persona
            Persona nuevaPersona = personaRepository.save(persona);

            //crear Usuario
            Usuarios usuario = new Usuarios();
            usuario.setNombreUsuario(registro.getNombreUsuario());
            usuario.setPassword(passwordEncoder.encode(registro.getPassword()));
            usuario.setRol(rolRepository.findById(registro.getIdRol())
                    .orElseThrow(() -> new RuntimeException("El rol no se encuentra registrado")));
            usuario.setPersona(nuevaPersona); // Cambia a nuevaPersona
            usuario.setA2fActivo(registro.isA2fActivo());
            usuario.setActivo(registro.isActivo());

            //guardar usuario
            usuarioRepository.save(usuario);

            //exito zzzzz
            return new ResponseBackend(true, HttpStatus.CREATED, "Usuario creado exitosamente");

        } catch (Exception e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
            // La transacción se marca automáticamente para rollback
            return new ResponseBackend(false, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


    private ResponseBackend validarRegistro(RegistroUsuariosDTO registro) {

        if (userRepository.findByNombreUsuario(registro.getNombreUsuario()).isPresent()) {
            return new ResponseBackend(false, HttpStatus.CONFLICT, "El nombre de usuario ya está en uso");
        }

        if (personaRepository.findByCorreo(registro.getCorreo()).isPresent()) {
            return new ResponseBackend(false, HttpStatus.CONFLICT, "El correo electrónico ya se encuentra registrado");
        }

        if (personaRepository.findByCui(registro.getPersona().getCui()).isPresent()) {
            return new ResponseBackend(false, HttpStatus.CONFLICT, "El CUI ya se encuentra registrado");
        }

        if (personaRepository.findByNit(registro.getPersona().getNit()).isPresent()) {
            return new ResponseBackend(false, HttpStatus.CONFLICT, "El NIT ya se encuentra registrado");
        }

        if (personaRepository.findByTelefono(registro.getPersona().getTelefono()).isPresent()) {
            return new ResponseBackend(false, HttpStatus.CONFLICT, "El teléfono ya se encuentra registrado");
        }

        return null;
    }

}



