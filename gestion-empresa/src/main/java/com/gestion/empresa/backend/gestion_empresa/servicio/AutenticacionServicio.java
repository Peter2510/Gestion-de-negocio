package com.gestion.empresa.backend.gestion_empresa.servicio;

import com.gestion.empresa.backend.gestion_empresa.Dto.Login;
import com.gestion.empresa.backend.gestion_empresa.Dto.RegistroUsuarios;
import com.gestion.empresa.backend.gestion_empresa.models.Usuarios;
import com.gestion.empresa.backend.gestion_empresa.repositorio.RepositorioUsuario;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
@Service
public class AutenticacionServicio {
    private final RepositorioUsuario userRepository;
    private final PasswordEncoder passwordEncoder;
    private  final AuthenticationManager authenticationManager;

    public AutenticacionServicio(
            RepositorioUsuario userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    //funcion de ingreso
    public Usuarios authenticate(Login input) {
        Usuarios user = userRepository.findByNombreUsuario(input.getNombreUsuario())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isEnabled()) {
            throw new RuntimeException("Account not verified. Please verify your account.");
        }

        System.out.println(input.getNombreUsuario()+"   -----"+
                input.getPassword());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getNombreUsuario(),
                        input.getPassword()
                )
        );

        return user;
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }

}
