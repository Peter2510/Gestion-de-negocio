package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.dto.AuthRespuesta;
import com.gestion.empresa.backend.gestion_empresa.dto.Login;
import com.gestion.empresa.backend.gestion_empresa.models.Genero;
import com.gestion.empresa.backend.gestion_empresa.models.Persona;
import com.gestion.empresa.backend.gestion_empresa.models.Rol;
import com.gestion.empresa.backend.gestion_empresa.models.Usuarios;
import com.gestion.empresa.backend.gestion_empresa.repositories.UsuarioRepository;
import com.gestion.empresa.backend.gestion_empresa.security.JwtServicio;
import com.gestion.empresa.backend.gestion_empresa.services.AutenticacionServicie;
import com.gestion.empresa.backend.gestion_empresa.services.UsuarioServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AutenticacionServiceImplTest {



    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AutenticacionServiceImpl autenticacionService;
      @Mock
    private JwtServicio jwtServicio;
    @Mock
    private UsuarioRepository usuarioRepository;


    private Login usuario;


    private Usuarios usuarios;




    @BeforeEach
    void setUp() {

        usuario = new Login();
        usuario.setNombreUsuario("MartinNnNnN");
        usuario.setPassword("simon1234");

        //valor de retorno
        usuarios = new Usuarios();
        usuarios.setNombreUsuario("MartinNnNnN");
        usuarios.setPassword("$2a$10$7Ckg3EUOiBGGEu1PYeROVuGgPm1krHYfY.qGq.hOJaslwOK7SIwwi");

    }



    @Test
    public void testLoginConUsuarioNoExiste() {

        when(usuarioRepository.findByNombreUsuario(usuario.getNombreUsuario())).thenReturn(Optional.empty());
        Optional<Usuarios> usuarioNuevo = autenticacionService.buscarNombre( usuario.getNombreUsuario());
        assertEquals(Optional.empty(), usuarioNuevo);
        verify(usuarioRepository, times(1)).findByNombreUsuario(usuario.getNombreUsuario());
    }

    @Test
    public void testLoginConCredencialesIncorrectas() {
        // Configura el mock para el usuario
        Optional<Usuarios> usuarioPrueba = Optional.of(new Usuarios());
        usuarioPrueba.get().setNombreUsuario("MartinNnNnN");
        usuarioPrueba.get().setPassword("$2a$10$7Ckg3EUOiBGGEu1PYeROVuGgPm1krHYfY.qGq.hOJaslwOK7SIwwi");
        String nuevaPassword = "$2a$10$7Ckg3EUER56Eu1PYeROVuGgPm1krHYfY.qGq.hOJaslwOK7SIwwi";

        // Simulamos
        when(usuarioRepository.findByNombreUsuario("MartinNnNnN")).thenReturn(Optional.of(usuarios));

        // Ejecuta la lógica del servicio
        Optional<Usuarios> usuarioNuevo = autenticacionService.buscarNombre(usuarios.getNombreUsuario());
        System.out.println(usuarioNuevo+"/*/*/*/");
        Optional<AuthRespuesta> respuestaOptional = autenticacionService.validarCredenciales(nuevaPassword, usuarioNuevo);

        // Verifica los resultados esperados
        assertFalse(respuestaOptional.isPresent());  // Como las credenciales son incorrectas, no debería haber respuesta
        verify(usuarioRepository, times(1)).findByNombreUsuario(usuarios.getNombreUsuario());
    }


    @Test
    public void testLoginConCredencialesCorrectas() {
        // Configura el mock para el usuario
        Usuarios usuarioPrueba = new Usuarios();
        usuarioPrueba.setId(1L);
        usuarioPrueba.setNombreUsuario("MartinNnNnN");
        usuarioPrueba.setPassword("$2a$10$7Ckg3EUOiBGGEu1PYeROVuGgPm1krHYfY.qGq.hOJaslwOK7SIwwi"); // Contraseña encriptada
        usuarioPrueba.setActivo(true);
        usuarioPrueba.setA2fActivo(true);

        // Configura los objetos relacionados como Rol y Persona
        Rol rol = new Rol();
        rol.setId(2L);
        rol.setNombre("Cliente");
        usuarioPrueba.setRol(rol);

        Persona persona = new Persona();
        persona.setNombre("Martin");
        persona.setNit("33434");
        persona.setNumero(2312323);
        persona.setCorreo("Martin@correo.com");
        persona.setDireccion("Calle Falsa 123");
        persona.setTelefono(3344);

        Genero genero = new Genero();
        genero.setId(2);
        genero.setGenero("Masculino");
        persona.setGenero(genero);

        usuarioPrueba.setPersona(persona);

        // Simula la búsqueda del usuario en el repositorio
        lenient().when(usuarioRepository.findByNombreUsuario("MartinNnNnN")).thenReturn(Optional.of(usuarioPrueba));

        // Simula la validación de la contraseña correcta
        String nuevaPassword = "$2a$10$7Ckg3EUOiBGGEu1PYeROVuGgPm1krHYfY.qGq.hOJaslwOK7SIwwi";
        lenient().when(passwordEncoder.matches(any(CharSequence.class), anyString())).thenReturn(true); // Contraseña correcta

        // Simula la generación del token
        String mockedToken = "mockedToken";
        lenient().when(jwtServicio.obtenerToken(any(Usuarios.class))).thenReturn(mockedToken);

        // Ejecuta la lógica del servicio
        Optional<Usuarios> usuarioNuevo = autenticacionService.buscarNombre(usuarioPrueba.getNombreUsuario());
        Optional<AuthRespuesta> respuestaOptional = autenticacionService.validarCredenciales(nuevaPassword, usuarioNuevo);

        // Verifica que el login sea exitoso
        assertTrue(respuestaOptional.isPresent());  // Las credenciales son correctas, debería haber respuesta
        assertEquals(mockedToken, respuestaOptional.get().getToken());  // Verifica que el token es el simulado
        verify(usuarioRepository, times(1)).findByNombreUsuario(usuarioPrueba.getNombreUsuario());
        verify(jwtServicio, times(1)).obtenerToken(usuarioPrueba);
    }


}