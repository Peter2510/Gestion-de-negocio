package com.gestion.empresa.backend.gestion_empresa.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gestion.empresa.backend.gestion_empresa.security.JwtFiltroAutorizacion;
import com.gestion.empresa.backend.gestion_empresa.security.JwtServicio;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.HandlerExceptionResolver;

class JwtFiltroAutorizacionTest {

    @InjectMocks
    private JwtFiltroAutorizacion jwtFiltro;

    @Mock
    private JwtServicio jwtServicio;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HandlerExceptionResolver handlerExceptionResolver;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private UserDetails userDetails;

    private final String validToken = "Bearer valid.token.here";
    private final String username = "testUser";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext(); // Limpia el contexto antes de cada prueba
    }

    @Test
    public void testDoFilterInternal_NoToken_ContinuesFilterChain() throws Exception {
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);

        jwtFiltro.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void testDoFilterInternal_InvalidToken_DoesNotSetAuthentication() throws Exception {
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(validToken);
        when(jwtServicio.extarerNombreUsuario(anyString())).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtServicio.esValido(anyString(), any(UserDetails.class))).thenReturn(false);

        jwtFiltro.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}
