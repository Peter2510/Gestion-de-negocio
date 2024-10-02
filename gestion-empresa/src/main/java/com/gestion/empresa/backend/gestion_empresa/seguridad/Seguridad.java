package com.gestion.empresa.backend.gestion_empresa.seguridad;

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

// para que determine que es
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class Seguridad {

    private AuthenticationProvider authenticationProvider;
    private JwtFiltroAutorizacion jwtFiltroAutorizacion;
    public Seguridad(
            AuthenticationProvider authenticationProvider,
            JwtFiltroAutorizacion jwtFiltroAutorizacion
    ) {
        this.authenticationProvider = authenticationProvider;
        this.jwtFiltroAutorizacion = jwtFiltroAutorizacion;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // se desabilita esta proteccion de csrf
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize ->
                                authorize
                                        .requestMatchers("/Auth/**").permitAll()
                                        .anyRequest().authenticated()
//                .requestMatchers("/usuarios").authenticated()
//                        // aca ver bien las rutas que no jala
//                        //.requestMatchers("/usuarios/login").permitAll()
//                        .anyRequest().permitAll()
                ).formLogin(withDefaults()).build();
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(jwtFiltroAutorizacion, UsernamePasswordAuthenticationFilter.class);

//        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("https://app-backend.com", "http://localhost:8080"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
