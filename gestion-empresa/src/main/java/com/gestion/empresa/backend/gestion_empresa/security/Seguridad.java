package com.gestion.empresa.backend.gestion_empresa.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class Seguridad {

    private final AuthenticationProvider authenticationProvider;
    private final JwtFiltroAutorizacion jwtFiltroAutorizacion;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desactivar CSRF
                .cors(Customizer.withDefaults()) // Habilitar CORS
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/Auth/**").permitAll() // Permitir acceso a rutas de autenticación
                        .requestMatchers("/health").permitAll() // Permitir acceso a la salud del servicio
                        .requestMatchers("/Genero/**").permitAll() // Permitir acceso a género
                        .requestMatchers("/correo/**").permitAll() // Permitir acceso a correo

                        // Rutas que requieren autenticación
                        .requestMatchers("/buzon/**").permitAll()
                        .requestMatchers("/categorias/**").hasAuthority("Administrador")
                        .requestMatchers("/diasLaborales/**").hasAuthority("Administrador")
                        .requestMatchers("/duracionServicioPrestado/**").hasAuthority("Administrador")
                        .requestMatchers("/empresa/**").hasAnyAuthority("Administrador", "Cliente")
                        .requestMatchers("/estadoServicio/**").hasAuthority("Administrador")
                        .requestMatchers("/jornadaLaboral/**").hasAuthority("Administrador")
                        .requestMatchers("/jornadaPorDia/**").hasAuthority("Administrador")
                        .requestMatchers("/jornadaServicio/**").hasAuthority("Administrador")
                        .requestMatchers("/permiso/**").hasAuthority("Administrador")
                        .requestMatchers("/permiso-rol/**").permitAll()
                        .requestMatchers("/Persona/**").hasAuthority("Administrador")
                        .requestMatchers("/rol/**").hasAuthority("Administrador")
                        .requestMatchers("/servicios/**").hasAuthority("Administrador")
                        .requestMatchers("/tipoAsignacionCita/**").hasAuthority("Administrador")
                        .requestMatchers("/tipoServicio/**").hasAuthority("Administrador")
                        .requestMatchers("/usuarios/**").hasAuthority("Administrador")

                        // Cualquier otra solicitud requiere autenticación
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Configurar como sin estado
                )
                .authenticationProvider(authenticationProvider) // Configurar proveedor de autenticación
                .addFilterBefore(jwtFiltroAutorizacion, UsernamePasswordAuthenticationFilter.class); // Agregar filtro JWT

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Permitir el origen de tu aplicación Angular
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));

        // Métodos permitidos
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Encabezados permitidos
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        // Permitir credenciales (si necesitas enviar cookies o tokens de autenticación)
        configuration.setAllowCredentials(true);

        // Configurar el mapeo de URL
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }





}