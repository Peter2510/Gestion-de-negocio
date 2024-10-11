package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.dto.AuthRespuesta;
import com.gestion.empresa.backend.gestion_empresa.dto.Login;
import com.gestion.empresa.backend.gestion_empresa.dto.RegistroUsuarios;
import com.gestion.empresa.backend.gestion_empresa.models.Persona;
import com.gestion.empresa.backend.gestion_empresa.models.Rol;
import com.gestion.empresa.backend.gestion_empresa.models.Usuarios;
import com.gestion.empresa.backend.gestion_empresa.repositories.PersonaRepository;
import com.gestion.empresa.backend.gestion_empresa.repositories.RolRepository;
import com.gestion.empresa.backend.gestion_empresa.repositories.UsuarioRepository;
import com.gestion.empresa.backend.gestion_empresa.security.JwtServicio;
import com.gestion.empresa.backend.gestion_empresa.services.UsuarioServicio;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.AutenticacionServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.RolServiceImpl;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.InvalidCredentialsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Author: alexxus
 * Created on: 5/10/24
 */
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private PersonaRepository personaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioServicio usuarioServicio;


    @Mock
    private JwtServicio jwtServicio;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AutenticacionServiceImpl autenticacionService;

    @InjectMocks
    private AuthController authController;

    private Rol rol;

    // para valores que no existen
    private Login loginMock;


    // para valores correctos
    private Login loginMockCorrecto;
    private Login loginMockIncorrecto;


    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        loginMock = new Login();
        loginMock.setNombreUsuario("Simonn");
        loginMock.setPassword("hola");


        loginMockCorrecto = new Login();
        loginMockCorrecto.setNombreUsuario("MartinNnNnN");
        loginMockCorrecto.setPassword("adios");


        loginMockIncorrecto = new Login();
        loginMockIncorrecto.setNombreUsuario("MartinNnNnN");
        loginMockIncorrecto.setPassword("simon1234");
    }


//
//    @Test
//    public void testLoginUsuarioNoEncontrado() {
//        lenient().when(autenticacionService.login(loginMock)).thenReturn(Optional.empty());
//        ResponseEntity<Map<String, Object>> respuesta = authController.login(loginMock);
//
//        // Ejecutar el método login y verificar que lanza una excepción
//
//        assertEquals(HttpStatus.NOT_FOUND,respuesta.getStatusCode());
//        //verifica que venga
//        assertTrue(respuesta.getBody().containsKey("ok"));
//        assertTrue(respuesta.getBody().containsKey("mensaje"));
//        //ejecucuon de tiempo
//     //   verify(autenticacionService, times(1)).login(loginMock);
//
//    }



//    @Test
//    public void testLoginUsuarioCredencialesIncorrectas() {
//        // Simular que las credenciales no coinciden
//        Optional<AuthRespuesta> authRespuestaMock = Optional.ofNullable(AuthRespuesta.builder()
//                .token("no coincide")
//                .build());
//
//        lenient().when(autenticacionService.login(loginMockIncorrecto)).thenReturn(authRespuestaMock);
//
//        // Ejecutar el método login
//        ResponseEntity<Map<String, Object>> respuesta = authController.login(loginMockIncorrecto);
//
//        // Verificar que el código de estado sea 400 BAD_REQUEST
//        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
//
//        assertTrue(respuesta.getBody().containsKey("ok"));
//        assertTrue(respuesta.getBody().containsKey("mensaje"));
//
//        assertEquals(false, respuesta.getBody().get("ok"));
//        assertEquals("Credenciales no coinciden", respuesta.getBody().get("mensaje"));
//    }
//
//
//    @Test
//    public void testLoginConCredencialesCorrectas() {
//        //generacion de busqueda de servicio
//        Optional<Usuarios> user = usuarioServicio.buscarNombre(loginMockCorrecto.getNombreUsuario());
//        String token = jwtServicio.obtenerToken(user.get());
//
//        AuthRespuesta authRespuestaMock = AuthRespuesta.builder().token(token).build();
//
//        // Simular el comportamiento del repositorio para retornar el usuario simulado
//        lenient().when(autenticacionService.login(loginMockCorrecto)).thenReturn(Optional.ofNullable(authRespuestaMock));
//
//        ResponseEntity<Map<String, Object>> respuesta = authController.login(loginMockCorrecto);
//
//        // Ejecutar el método login y verificar que lanza una excepción
//        System.out.println(respuesta);
//
//        assertEquals(HttpStatus.OK,respuesta.getStatusCode());
//        //verifica que venga
//        assertTrue(respuesta.getBody().containsKey("ok"));
//        assertTrue(respuesta.getBody().containsKey("mensaje"));
//
//
//    }


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
//        AuthRespuesta respuesta = autenticacionService.registro(registroMock);
//
//        // Verificar que los repositorios y servicios externos se llamaron correctamente
//        verify(personaRepository, times(1)).save(any(Persona.class));
//        verify(usuarioRepository, times(1)).save(any(Usuarios.class));
//        verify(jwtServicio, times(1)).obtenerToken(any(Usuarios.class));
//
//        // Aserciones para verificar la respuesta esperada
//        assertNotNull(respuesta);
//        assertEquals("fakeToken", respuesta.getToken());
//    }
}
