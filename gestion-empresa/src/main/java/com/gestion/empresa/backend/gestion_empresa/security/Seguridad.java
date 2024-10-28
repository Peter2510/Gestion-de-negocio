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
                .csrf(csrf -> csrf.disable()) //desactivar CSRF
                .cors(Customizer.withDefaults()) //habilitar CORS
                .authorizeHttpRequests(auth -> auth
                        //rutas públicas que no requieren autenticación
                        .requestMatchers("/Auth/**").permitAll()
                        .requestMatchers("/health").authenticated()
                        .requestMatchers("/Genero/**").permitAll()
                        .requestMatchers("/correo/**").permitAll()

                        //rutas que requieren un JWT válido pero sin verificar roles
                        .requestMatchers("/buzon/**").authenticated()
                        .requestMatchers("/categorias/**").authenticated()
                        .requestMatchers("/diasLaborales/**").authenticated()
                        .requestMatchers("/duracionServicioPrestado/**").authenticated()
                        .requestMatchers("/empresa/**").authenticated()
                        .requestMatchers("/estadoServicio/**").authenticated()
                        .requestMatchers("/jornadaLaboral/**").authenticated()
                        .requestMatchers("/jornadaPorDia/**").authenticated()
                        .requestMatchers("/jornadaServicio/**").authenticated()
                        .requestMatchers("/permiso/**").authenticated()
                        .requestMatchers("/permiso-rol/**").authenticated()
                        .requestMatchers("/Persona/**").authenticated()
                        .requestMatchers("/rol/**").authenticated()
                        .requestMatchers("/servicios/**").authenticated()
                        .requestMatchers("/tipoAsignacionCita/**").authenticated()
                        .requestMatchers("/tipoServicio/**").authenticated()
                        .requestMatchers("/usuarios/**").authenticated()
                        .requestMatchers("/citas/**").authenticated()

                        //cualquier otra solicitud requiere JWT válido
                        .anyRequest().permitAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFiltroAutorizacion, UsernamePasswordAuthenticationFilter.class); //filtro JWT

        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        //permitir el origen de tu aplicación Angular
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));

        //metodos permitidos
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        //encabezados permitidos
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        //permitir credenciales
        configuration.setAllowCredentials(true);

        //configurar el mapeo de URL
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }





}