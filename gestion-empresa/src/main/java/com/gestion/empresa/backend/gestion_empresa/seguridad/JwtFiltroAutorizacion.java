package com.gestion.empresa.backend.gestion_empresa.seguridad;

import ch.qos.logback.core.util.StringUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.awt.*;
import java.io.IOException;
import java.net.http.HttpHeaders;
import java.security.Security;
import java.util.Collection;
import java.util.List;

@Component
// se ejecuta solo una vez por http
public class JwtFiltroAutorizacion extends OncePerRequestFilter {
    private JwtServicio jwtServicio;
    private HandlerExceptionResolver handlerExceptionResolver;
    private UserDetailsService userDetailsService;


    //constructor

    public JwtFiltroAutorizacion(
            JwtServicio jwtServicio,
            UserDetailsService userDetailsService,
            HandlerExceptionResolver handlerExceptionResolver
    ) {
        this.jwtServicio = jwtServicio;
        this.userDetailsService = userDetailsService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }


    // metodo general
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //llmamamos al header
        final String authHeader = request.getHeader("Authorization");


        // token
        final String token = getTokenRequest(request);

        if (token == null ) {
            filterChain.doFilter(request, response);
            return;
        }


        try {
            final String jwt = authHeader.substring(7);
            //extae en base al substring del header
            final String correo = jwtServicio.extarerNombreUsuario(jwt);

            //elemento de autenticacion

            Authentication autenticacion = SecurityContextHolder.getContext().getAuthentication();
            System.out.println(authHeader+ " *-*-*-*-*-*-*-*-*-*-*-* "+ correo+"   +------------   "+autenticacion);


            // compropbacion
            if (correo != null && autenticacion != null) {
                UserDetails detalles = this.userDetailsService.loadUserByUsername(correo);

                if (jwtServicio.esValido(jwt, detalles )){
                    UsernamePasswordAuthenticationToken tokenAutorizado = new UsernamePasswordAuthenticationToken(detalles, null, detalles.getAuthorities());

                    tokenAutorizado.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(tokenAutorizado);
                }

            }


            filterChain.doFilter(request, response);

        } catch (Exception exception){
            handlerExceptionResolver.resolveException(request, response, null, exception);

        }
    }

    private String getTokenRequest(HttpServletRequest valor){
        final String authHeader = valor.getHeader("Authorization");
    if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer")){
        return authHeader.substring(7);

    }
    return null;
    }
}
