package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.dto.AuthRespuesta;
import com.gestion.empresa.backend.gestion_empresa.dto.Login;
import com.gestion.empresa.backend.gestion_empresa.dto.RegistroUsuariosDTO;
import com.gestion.empresa.backend.gestion_empresa.models.Genero;
import com.gestion.empresa.backend.gestion_empresa.models.Persona;
import com.gestion.empresa.backend.gestion_empresa.models.Rol;
import com.gestion.empresa.backend.gestion_empresa.models.Usuarios;
import com.gestion.empresa.backend.gestion_empresa.repositories.GeneroRepository;
import com.gestion.empresa.backend.gestion_empresa.repositories.PersonaRepository;
import com.gestion.empresa.backend.gestion_empresa.repositories.RolRepository;
import com.gestion.empresa.backend.gestion_empresa.repositories.UsuarioRepository;
import com.gestion.empresa.backend.gestion_empresa.security.JwtServicio;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AutenticacionServiceImplTest {

    @InjectMocks
    private AutenticacionServiceImpl autenticacionService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PersonaRepository personaRepository;

    @Mock
    private JwtServicio jwtServicio;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private GeneroRepository generoRepository;

    @Mock
    private RolRepository rolRepository;

    private Usuarios usuario;
    private Login login;
    private RegistroUsuariosDTO registro;
    private Rol rol;
    private Genero genero;
    private Persona persona;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        rol = new Rol(1L, "USER", "Rol de usuario");
        genero = new Genero(1L, "Masculino");
        persona = new Persona(123456789L, "Juan Perez", "NIT123456", "correo@example.com", "Direccion 123", "1234567890", genero);
        usuario = new Usuarios();
        usuario.setNombreUsuario("testUser");
        usuario.setPassword("encodedPassword");
        usuario.setRol(rol);
        usuario.setPersona(persona);
        usuario.setActivo(true);
        usuario.setA2fActivo(false);
        login = new Login("testUser", "plainPassword");
        registro = new RegistroUsuariosDTO();
        registro.setNombreUsuario("testUser");
        registro.setPassword("plainPassword");
        registro.setCorreo("correo@example.com");
        registro.setPersona(persona);
        registro.setIdGenero(1L);
        registro.setIdRol(1L);
    }

    @Test
    public void testLogin_UserNotFound() {
        lenient().when(usuarioRepository.findByNombreUsuario("testUser")).thenReturn(Optional.empty());
        Optional<AuthRespuesta> response = autenticacionService.login(login);
        assertTrue(response.isEmpty());
    }

    @Test
    public void testLogin_PasswordMismatch() {
        lenient().when(usuarioRepository.findByNombreUsuario("testUser")).thenReturn(Optional.of(usuario));
        lenient().when(passwordEncoder.matches("plainPassword", "encodedPassword")).thenReturn(false);
        Optional<AuthRespuesta> response = autenticacionService.login(login);
        assertTrue(response.isEmpty());
    }

    @Test
    public void testLogin_Success() {
        lenient().when(usuarioRepository.findByNombreUsuario("testUser")).thenReturn(Optional.of(usuario));
        lenient().when(passwordEncoder.matches("plainPassword", "encodedPassword")).thenReturn(true);
        lenient().when(jwtServicio.obtenerToken(usuario)).thenReturn("token123");
        Optional<AuthRespuesta> response = autenticacionService.login(login);
        assertFalse(response.isPresent());
    }

    @Test
    public void testRegistrarUsuario_Success() {
        lenient().when(usuarioRepository.findByNombreUsuario(anyString())).thenReturn(Optional.empty());
        lenient().when(personaRepository.findByCorreo(anyString())).thenReturn(Optional.empty());
        lenient().when(personaRepository.findByCui(anyLong())).thenReturn(Optional.empty());
        lenient().when(personaRepository.findByNit(anyString())).thenReturn(Optional.empty());
        lenient().when(personaRepository.findByTelefono(anyString())).thenReturn(Optional.empty());
        lenient().when(generoRepository.findById(any())).thenReturn(Optional.of(genero));
        lenient().when(rolRepository.findById(any())).thenReturn(Optional.of(rol));
        lenient().when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");
        ResponseBackend response = autenticacionService.registrarUsuario(registro);
        assertTrue(response.getOk());
        assertEquals(HttpStatus.CREATED, response.getStatus());
        assertEquals("Usuario creado exitosamente", response.getMensaje());
    }
}
