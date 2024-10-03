package com.gestion.empresa.backend.gestion_empresa.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.gestion.empresa.backend.gestion_empresa.repositories.UsuarioRepository;

import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class ConfiguracionSeguridad {

    private UsuarioRepository repoUsuario;



    // repositorio de usuarios
    public ConfiguracionSeguridad(UsuarioRepository repoUsuario) {
        this.repoUsuario = repoUsuario;
    }

    @Bean
    UserDetailsService userDetailsService() {
        //me retorna el valor del usario como userdetails
        return username -> repoUsuario.findByNombreUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
}
