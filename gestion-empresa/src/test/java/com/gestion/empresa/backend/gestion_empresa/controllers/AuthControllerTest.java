package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.dto.Login;
import com.gestion.empresa.backend.gestion_empresa.dto.RegistroUsuarios;
import com.gestion.empresa.backend.gestion_empresa.models.Persona;
import com.gestion.empresa.backend.gestion_empresa.models.Rol;
import com.gestion.empresa.backend.gestion_empresa.repositories.PersonaRepository;
import com.gestion.empresa.backend.gestion_empresa.repositories.RolRepository;
import com.gestion.empresa.backend.gestion_empresa.repositories.UsuarioRepository;
import com.gestion.empresa.backend.gestion_empresa.security.JwtServicio;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.AutenticacionServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.RolServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * Author: alexxus
 * Created on: 5/10/24
 */

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private PersonaRepository personaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;


    @Mock
    private JwtServicio jwtServicio;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    AutenticacionServiceImpl autenticacionService;

    @InjectMocks
    private RolController rolController;

    private Rol rol;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    public void testLoginUsuarioNoEncontrado() {
        // Simular que el usuario no es encontrado
        when(usuarioRepository.findByNombreUsuario("Simonn")).thenReturn(Optional.empty());

        Login loginMock = new Login();
        loginMock.setNombreUsuario("Simonn");
        loginMock.setPassword("hola");

        // Ejecutar el método login y verificar que lanza una excepción
        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            autenticacionService.login(loginMock);
        });

        assertEquals("Usuario no encontrado: testUser", exception.getMessage());
    }

//
//    @Test
//    public void testRegistro() {
//        // Datos simulados
//        RegistroUsuarios registroMock = new RegistroUsuarios();
//        //valo
//        registroMock.setPassword("password123");
//        registroMock.setNombreUsuario("testUser");
//        Persona personaMock = new Persona();
//        personaMock.setNombre("Test Nombre");
//        registroMock.setPersona(personaMock);
//
//        // Simular el comportamiento de passwordEncoder y jwtServicio
//        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword123");
//        when(jwtServicio.obtenerToken(any(Usuarios.class))).thenReturn("fakeToken");
//
//        // Simular el guardado de Persona y Usuario
//        when(personaRepository.save(any(Persona.class))).thenAnswer(invocation -> {
//            Persona persona = invocation.getArgument(0);
//            persona.setCui(1L); // Simula que la base de datos genera un ID
//            return persona;
//        });
//
//        // Ejecutar el método de registro
//        AuthRespuesta respuesta = registroUsuariosService.registro(registroMock);
//
//        // Verificar que los repositorios y servicios externos se llamaron correctamente
//        verify(personaRepository, times(1)).save(any(Persona.class));
//        verify(userRepository, times(1)).save(any(Usuarios.class));
//        verify(jwtServicio, times(1)).obtenerToken(any(Usuarios.class));
//
//        // Aserciones para verificar la respuesta esperada
//        assertNotNull(respuesta);
//        assertEquals("fakeToken", respuesta.getToken());
//    }
}
