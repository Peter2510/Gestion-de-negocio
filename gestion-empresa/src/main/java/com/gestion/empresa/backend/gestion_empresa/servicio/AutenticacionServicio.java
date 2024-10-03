package com.gestion.empresa.backend.gestion_empresa.servicio;

import com.gestion.empresa.backend.gestion_empresa.Dto.AuthRespuesta;
import com.gestion.empresa.backend.gestion_empresa.Dto.Login;
import com.gestion.empresa.backend.gestion_empresa.Dto.RegistroUsuarios;
import com.gestion.empresa.backend.gestion_empresa.models.Rol;
import com.gestion.empresa.backend.gestion_empresa.models.Usuarios;
import com.gestion.empresa.backend.gestion_empresa.repositorio.RepositorioUsuario;
import com.gestion.empresa.backend.gestion_empresa.seguridad.JwtServicio;
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
public class AutenticacionServicio {
    private final RepositorioUsuario userRepository;
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
        Rol nuevoRol= new Rol(2, "Cliente");
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
