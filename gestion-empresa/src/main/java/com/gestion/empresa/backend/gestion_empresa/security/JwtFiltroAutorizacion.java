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
        if (token == null ) {
            filterChain.doFilter(request, response);
            return;
        }

//
//        username=jwtServicio.getUsernameFromToken(token);
//
//        if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
//        {
//            UserDetails userDetails=userDetailsService.loadUserByUsername(username);
//
//            if (jwtService.isTokenValid(token, userDetails))
//            {
//                UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(
//                        userDetails,
//                        null,
//                        userDetails.getAuthorities());
//
//                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//
//        }
////
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

        return authHeader.substring(7);

    }
    return null;
    }
}
