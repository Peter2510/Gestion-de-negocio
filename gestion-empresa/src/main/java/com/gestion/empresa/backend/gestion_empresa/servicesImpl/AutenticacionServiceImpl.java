package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.dto.AuthRespuesta;
import com.gestion.empresa.backend.gestion_empresa.dto.Login;
import com.gestion.empresa.backend.gestion_empresa.dto.RegistroUsuarios;
import com.gestion.empresa.backend.gestion_empresa.models.Persona;
import com.gestion.empresa.backend.gestion_empresa.models.Rol;
import com.gestion.empresa.backend.gestion_empresa.models.Usuarios;
import com.gestion.empresa.backend.gestion_empresa.repositories.PersonaRepository;
import com.gestion.empresa.backend.gestion_empresa.repositories.UsuarioRepository;
import com.gestion.empresa.backend.gestion_empresa.security.JwtServicio;
import com.gestion.empresa.backend.gestion_empresa.services.AutenticacionServicie;
import com.gestion.empresa.backend.gestion_empresa.services.UsuarioServicio;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Optionals;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
@Service
@RequiredArgsConstructor
public class AutenticacionServiceImpl implements AutenticacionServicie {
    private final UsuarioRepository userRepository;
    private final PersonaRepository personaRepository;
    private  final AuthenticationManager authenticationManager;
    private final JwtServicio jwtServicio;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioRepository usuarioRepository;


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
            return Optional.ofNullable(AuthRespuesta.builder()
                    .token("no coincide")
                    .build());
        }
        // Si la autenticación es correcta, generar el token JWT
        String token = jwtServicio.obtenerToken(user.get());

        return Optional.ofNullable(AuthRespuesta.builder()
                .token(token)
                .build());
    }



    /// funcion de registro
    public AuthRespuesta registro(RegistroUsuarios registros){

        String passwordEncriptada = passwordEncoder.encode(registros.getPassword());
        System.out.println(registros+"----------------------------"+passwordEncriptada+"---------------"+registros.getPassword());

        //rpimero crear a la persona
        Persona nuevaPersona = new Persona();
        nuevaPersona.setNombre(registros.getPersona().getNombre());
        //nuevaPersona.setCui(registros.getPersona().getCui());
        nuevaPersona.setDireccion(registros.getPersona().getDireccion());
        nuevaPersona.setCorreo(registros.getPersona().getCorreo());
        nuevaPersona.setTelefono(registros.getPersona().getTelefono());
        nuevaPersona.setNumero(registros.getPersona().getTelefono());
        nuevaPersona.setNit(registros.getPersona().getNit());
        nuevaPersona.setGenero(registros.getPersona().getGenero());
        System.out.println(nuevaPersona+";;;;;;");

        personaRepository.save(nuevaPersona);



        // Obtener el id de la persona creada
        Long personaId = nuevaPersona.getCui();


        /// ya con eso crear al usuarii
        Rol nuevoRol= new Rol((long) 2,"Cliente","Persona que utiliza los servicios dentro de la empresa");
        Usuarios nuevoUsuario = Usuarios.builder()
                .nombreUsuario(registros.getNombreUsuario())
                .password(passwordEncriptada)
                .rol(nuevoRol)
                .persona(nuevaPersona)
                .activo(true)
                .a2fActivo(registros.isA2fActivo()).build();
        userRepository.save(nuevoUsuario);
        return AuthRespuesta.builder().token(jwtServicio.obtenerToken(nuevoUsuario)).build();
    }

    @Override
    public Optional<Usuarios> buscarNombre(String nombreUsuario) {        // Buscar al usuario en la base de datos
        return userRepository.findByNombreUsuario(nombreUsuario);

    }



    public Optional<AuthRespuesta> validarCredenciales(String password, Optional<Usuarios> usuarios) {
        if ((password== usuarios.get().getPassword())) {
            // lógica para cuando las credenciales coinciden
            return (generacionToken(usuarios));
        } else {
            return Optional.empty(); // o la lógica que necesites en caso de falla
        }
    }


    @Override
    public Optional<AuthRespuesta> generacionToken(Optional<Usuarios> usuarios) {
        String token = jwtServicio.obtenerToken(usuarios.get());

        return Optional.ofNullable(AuthRespuesta.builder()
                .token(token)
                .build());
    }
}
