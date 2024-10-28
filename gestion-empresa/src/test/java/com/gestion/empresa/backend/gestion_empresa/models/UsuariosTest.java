package com.gestion.empresa.backend.gestion_empresa.models;

import com.gestion.empresa.backend.gestion_empresa.models.Persona;
import com.gestion.empresa.backend.gestion_empresa.models.Rol;
import com.gestion.empresa.backend.gestion_empresa.models.Usuarios;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class UsuariosTest {

    private Usuarios usuario;
    private Rol rol;
    private Persona persona;

    @BeforeEach
    public void setUp() {
        rol = new Rol();
        rol.setNombre("ROLE_USER");

        persona = new Persona();
        persona.setNombre("John Doe");

        usuario = new Usuarios();
        usuario.setId(1L);
        usuario.setNombreUsuario("johndoe");
        usuario.setPassword("password123");
        usuario.setRol(rol);
        usuario.setPersona(persona);
        usuario.setActivo(true);
        usuario.setA2fActivo(false);
    }

    @Test
    public void testGetAuthorities() {
        Collection<? extends GrantedAuthority> authorities = usuario.getAuthorities();

        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertEquals("ROLE_USER", authorities.iterator().next().getAuthority());
    }

    @Test
    public void testGetUsername() {
        assertEquals("johndoe", usuario.getUsername());
    }

    @Test
    public void testIsAccountNonExpired() {
        assertTrue(usuario.isAccountNonExpired());
    }

    @Test
    public void testIsAccountNonLocked() {
        assertTrue(usuario.isAccountNonLocked());
    }

    @Test
    public void testIsCredentialsNonExpired() {
        assertTrue(usuario.isCredentialsNonExpired());
    }

    @Test
    public void testIsEnabled() {
        assertTrue(usuario.isEnabled());
    }

    @Test
    public void testObtenerPersona() {
        assertEquals(persona, usuario.obtenerPersona());
    }

    @Test
    public void testObtenerRol() {
        assertEquals(rol, usuario.obtenerRol());
    }

    @Test
    public void testObtenerActivo() {
        assertTrue(usuario.obtenerActivo());
    }
}
