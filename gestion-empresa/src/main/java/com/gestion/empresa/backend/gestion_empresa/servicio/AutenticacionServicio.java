package com.gestion.empresa.backend.gestion_empresa.servicio;

import com.gestion.empresa.backend.gestion_empresa.Dto.AuthRespuesta;
import com.gestion.empresa.backend.gestion_empresa.Dto.Login;
import com.gestion.empresa.backend.gestion_empresa.Dto.RegistroUsuarios;
import com.gestion.empresa.backend.gestion_empresa.models.Rol;
import com.gestion.empresa.backend.gestion_empresa.models.Usuarios;
import com.gestion.empresa.backend.gestion_empresa.repositorio.RepositorioUsuario;
import com.gestion.empresa.backend.gestion_empresa.seguridad.JwtServicio;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
@Service
public class AutenticacionServicio {
    private final RepositorioUsuario userRepository;
    private final JwtServicio jwtServicio;


    public AutenticacionServicio(
            RepositorioUsuario userRepository, JwtServicio jwtServicio
    ) {
        this.userRepository = userRepository;
        this.jwtServicio = jwtServicio;
    }



public AuthRespuesta login(Login valor){
        return null;
}

    public AuthRespuesta registro(RegistroUsuarios registros){
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
