package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.dto.AuthRespuesta;
import com.gestion.empresa.backend.gestion_empresa.dto.Login;
import com.gestion.empresa.backend.gestion_empresa.dto.RegistroUsuarios;
import com.gestion.empresa.backend.gestion_empresa.models.Rol;
import com.gestion.empresa.backend.gestion_empresa.models.Usuarios;
import com.gestion.empresa.backend.gestion_empresa.repositories.UsuarioRepository;
import com.gestion.empresa.backend.gestion_empresa.security.JwtServicio;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
@Service
@RequiredArgsConstructor
public class AutenticacionServiceImpl {
    private final UsuarioRepository userRepository;
    private  final AuthenticationManager authenticationManager;
    private final JwtServicio jwtServicio;




public AuthRespuesta login(Login valor){
    System.out.println(valor.getNombreUsuario()+"------------"+valor.getPassword());
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(valor.getNombreUsuario(), valor.getPassword()));
    Usuarios user=userRepository.findBynombreUsuario(valor.getNombreUsuario()).orElseThrow();
    System.out.println(user);

    String token=jwtServicio.obtenerToken(user);
    return AuthRespuesta.builder()
            .token(token)
            .build();
}



    /// funcion de registro
    public AuthRespuesta registro(RegistroUsuarios registros){
        System.out.println(registros+"----------------------");
        Rol nuevoRol= new Rol((long) 2,"Cliente","Persona que utiliza los servicios dentro de la empresa");
        Usuarios nuevoUsuario = Usuarios.builder().nombreUsuario(registros.getNombreUsuario())
                .password(registros.getPassword())
                .rol(nuevoRol)
                .persona(registros.getPersona())
                .activo(registros.isActivo())
                .a2fActivo(registros.isA2fActivo()).build();
        userRepository.save(nuevoUsuario);
        return AuthRespuesta.builder().token(jwtServicio.obtenerToken(nuevoUsuario)).build();
    }

}
