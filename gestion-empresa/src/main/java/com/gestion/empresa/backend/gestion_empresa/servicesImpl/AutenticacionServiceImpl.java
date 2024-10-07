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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
@Service
@RequiredArgsConstructor
public class AutenticacionServiceImpl {
    private final UsuarioRepository userRepository;
    private final PersonaRepository personaRepository;
    private  final AuthenticationManager authenticationManager;
    private final JwtServicio jwtServicio;

    @Autowired
    private PasswordEncoder passwordEncoder;




    public AuthRespuesta login(Login valor) {
        System.out.println(valor.getNombreUsuario() + "------------" + valor.getPassword());

        // Buscar al usuario en la base de datos
        Usuarios user = userRepository.findByNombreUsuario(valor.getNombreUsuario())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + valor.getNombreUsuario()));

        // Comparar la contraseña ingresada con la contraseña encriptada en la base de datos
        if (!passwordEncoder.matches(valor.getPassword(), user.getPassword())) {
            System.out.println("mal");
            throw new BadCredentialsException("Credenciales incorrectas");

        }

        // Si la autenticación es correcta, generar el token JWT
        String token = jwtServicio.obtenerToken(user);

        return AuthRespuesta.builder()
                .token(token)
                .build();
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

}
