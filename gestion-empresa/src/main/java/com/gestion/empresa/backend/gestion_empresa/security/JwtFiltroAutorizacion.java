package com.gestion.empresa.backend.gestion_empresa.security;

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

import org.springframework.http.HttpHeaders;
import java.awt.*;
import java.io.IOException;
import java.security.Security;
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
        // token
        final String token = getTokenRequest(request);
        final String nombreUsuario;

        if (token == null ) {
            filterChain.doFilter(request, response);
            return;
        }


        //aca obtiene al usuario
        nombreUsuario=jwtServicio.extarerNombreUsuario(token);


        // si todo esta bien y esta acutenticado lo jala
        if (nombreUsuario!=null && SecurityContextHolder.getContext().getAuthentication()==null)
        {
            // buscar en base de datos
            UserDetails userDetails=userDetailsService.loadUserByUsername(nombreUsuario);

            // si es valido se genera el token
            if (jwtServicio.esValido(token, userDetails))
            {
                UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                // hace el token de detalles
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));


                // ingresa en seguridad
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        }
//
            filterChain.doFilter(request, response);
//
//        } catch (Exception exception){
//            handlerExceptionResolver.resolveException(request, response, null, exception);
//
//        }
    }

    public String getTokenRequest(HttpServletRequest valor){
        final String authHeader=valor.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")){
            System.out.println(authHeader.substring(7)+"--------------");
            System.out.println(authHeader+"////////////////////////////////////");

        return authHeader.substring(7);

    }
    return null;
    }
}
