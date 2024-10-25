package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.dto.ActualizacionUsuarioAdminDTO;
import com.gestion.empresa.backend.gestion_empresa.dto.ActualizarContraseniaDTO;
import com.gestion.empresa.backend.gestion_empresa.models.*;
import com.gestion.empresa.backend.gestion_empresa.repositories.GeneroRepository;
import com.gestion.empresa.backend.gestion_empresa.repositories.PersonaRepository;
import com.gestion.empresa.backend.gestion_empresa.repositories.RolRepository;
import com.gestion.empresa.backend.gestion_empresa.repositories.UsuarioRepository;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private GeneroRepository generoRepository;

    @Mock
    private PersonaRepository personaRepository;

    @Mock
    private NotificacionServiceImpl notificacionService;

    @Mock
    private BuzonServiceImpl buzonService;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Mock
    private BuzonServiceImpl buzonServiceImpl;

    private Usuarios usuario;
    private ActualizacionUsuarioAdminDTO dto;
    private Persona persona;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuarios();
        usuario.setId(1L);
        usuario.setNombreUsuario("user");

        persona = new Persona();
        persona.setCui(12345L);
        persona.setNit("12345678");
        persona.setTelefono("987654321");
    }

    @Test
    void buscarPorCui_UsuarioExistente() {
        when(usuarioRepository.findByPersonaCui(12345678L)).thenReturn(Optional.of(usuario));
        Optional<Usuarios> resultado = usuarioService.buscarPorCui(12345678L);
        assertTrue(resultado.isPresent());
        assertEquals(usuario, resultado.get());
    }

    @Test
    void buscarPorCui_UsuarioNoExistente() {
        when(usuarioRepository.findByPersonaCui(12345678L)).thenReturn(Optional.empty());
        Optional<Usuarios> resultado = usuarioService.buscarPorCui(12345678L);
        assertFalse(resultado.isPresent());
    }

    @Test
    void buscarPorId_UsuarioExistente() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        ResponseBackend response = usuarioService.buscarPorId(1L);
        assertTrue(response.getOk());
        assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    void buscarPorId_UsuarioNoExistente() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseBackend response = usuarioService.buscarPorId(1L);
        assertFalse(response.getOk());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
    }

    @Test
    void actualizarUsuario_UsuarioExistente() {
        Rol rol = new Rol();
        rol.setId(1L);
        rol.setNombre("Usuario");

        Genero genero = new Genero();
        genero.setId(1L);
        genero.setGenero("Masculino");

        Persona persona = new Persona();
        persona.setNit("12345678");
        persona.setNombre("John Doe");
        persona.setDireccion("123 Main St");
        persona.setTelefono("123456789");
        persona.setCorreo("john.doe@example.com");

        Usuarios usuario = new Usuarios();
        usuario.setId(1L);
        usuario.setNombreUsuario("oldUser");
        usuario.setRol(rol);
        usuario.setPersona(persona);

        ActualizacionUsuarioAdminDTO dto = new ActualizacionUsuarioAdminDTO();
        dto.setIdUsuario(1L);
        dto.setNombreUsuario("newUser");
        dto.setIdRol(1L);
        dto.setCorreo("john.doe@example.com");
        dto.setIdGenero(1L);
        dto.setPersona(persona);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(personaRepository.save(any())).thenReturn(persona);
        when(rolRepository.findById(1L)).thenReturn(Optional.of(rol));
        when(generoRepository.findById(1L)).thenReturn(Optional.of(genero));

        ResponseBackend response = usuarioService.actualizarUsuario(dto);

        assertTrue(response.getOk());
        assertEquals(HttpStatus.OK, response.getStatus());
        verify(usuarioRepository, times(1)).findById(1L);
        verify(usuarioRepository, times(1)).save(any(Usuarios.class));
    }

    @Test
    void actualizarUsuario_UsuarioNoExistente() {
        ActualizacionUsuarioAdminDTO dto = new ActualizacionUsuarioAdminDTO();
        dto.setIdUsuario(1L);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseBackend response = usuarioService.actualizarUsuario(dto);
        assertFalse(response.getOk());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
    }

    @Test
    void actualizarUsuario_RolNoExistente() {
        ActualizacionUsuarioAdminDTO dto = new ActualizacionUsuarioAdminDTO();
        dto.setIdUsuario(1L);
        dto.setIdRol(1L);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(rolRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseBackend response = usuarioService.actualizarUsuario(dto);
        assertFalse(response.getOk());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
    }

    @Test
    void actualizarContrasenia_UsuarioExistente() {
        ActualizarContraseniaDTO dto = new ActualizarContraseniaDTO();
        dto.setIdUsuario(1L);
        dto.setContraseniaActual("oldPassword");
        dto.setContraseniaNueva("newPassword");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("oldPassword", usuario.getPassword())).thenReturn(true);
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");

        ResponseBackend response = usuarioService.actualizarContrasenia(dto);
        assertTrue(response.getOk());
        assertEquals(HttpStatus.OK, response.getStatus());
        verify(usuarioRepository, times(1)).save(any(Usuarios.class));
    }

    @Test
    void actualizarContrasenia_UsuarioNoExistente() {
        ActualizarContraseniaDTO dto = new ActualizarContraseniaDTO();
        dto.setIdUsuario(1L);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseBackend response = usuarioService.actualizarContrasenia(dto);
        assertFalse(response.getOk());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
    }

    @Test
    void actualizarContrasenia_ContraseniaIncorrecta() {
        ActualizarContraseniaDTO dto = new ActualizarContraseniaDTO();
        dto.setIdUsuario(1L);
        dto.setContraseniaActual("wrongPassword");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("wrongPassword", usuario.getPassword())).thenReturn(false);
        ResponseBackend response = usuarioService.actualizarContrasenia(dto);
        assertFalse(response.getOk());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
    }

    @Test
    void listarUsuariosPorRol_RolExistente() {
        Rol rol = new Rol();
        rol.setId(1L);
        when(rolRepository.findById(1L)).thenReturn(Optional.of(rol));
        when(usuarioRepository.findByRol(rol)).thenReturn(List.of(usuario));
        ResponseBackend response = usuarioService.listarUsuariosPorRol(1L);
        assertTrue(response.getOk());
        assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    void listarUsuariosPorRol_RolNoExistente() {
        when(rolRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseBackend response = usuarioService.listarUsuariosPorRol(1L);
        assertFalse(response.getOk());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
    }

    @Test
    void listarEmpleados_RolExistente() {
        Rol rol = new Rol();
        rol.setId(1L);
        when(rolRepository.findById(1L)).thenReturn(Optional.of(rol));
        when(usuarioRepository.findByRolIdNot(1L)).thenReturn(List.of(usuario));
        ResponseBackend response = usuarioService.listarEmpleados(1L);
        assertTrue(response.getOk());
        assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    void listarEmpleados_RolNoExistente() {
        when(rolRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseBackend response = usuarioService.listarEmpleados(1L);
        assertFalse(response.getOk());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
    }

    @Test
    void validarActualizacion_NombreUsuarioEnUso() {
        dto = new ActualizacionUsuarioAdminDTO();
        dto.setNombreUsuario("newUser");
        dto.setIdUsuario(1L);
        dto.setCorreo("email@example.com");
        dto.setPersona(persona);

        Usuarios otroUsuario = new Usuarios();
        otroUsuario.setId(2L);
        otroUsuario.setNombreUsuario("newUser");

        when(usuarioRepository.findByNombreUsuario("newUser")).thenReturn(Optional.of(otroUsuario));

        ResponseBackend response = usuarioService.validarActualizacion(1L, dto);

        assertFalse(response.getOk());
        assertEquals(HttpStatus.CONFLICT, response.getStatus());
        assertEquals("El nombre de usuario ya está en uso", response.getMensaje());
    }

    @Test
    void validarActualizacion_CorreoEnUso() {
        dto = new ActualizacionUsuarioAdminDTO();
        dto.setNombreUsuario("user");
        dto.setIdUsuario(1L);
        dto.setCorreo("email@example.com");
        dto.setPersona(persona);

        Persona otraPersona = new Persona();
        otraPersona.setCui(54321L);
        otraPersona.setCorreo("email@example.com");

        when(personaRepository.findByCorreo("email@example.com")).thenReturn(Optional.of(otraPersona));

        ResponseBackend response = usuarioService.validarActualizacion(1L, dto);

        assertFalse(response.getOk());
        assertEquals(HttpStatus.CONFLICT, response.getStatus());
        assertEquals("El correo electrónico ya está registrado", response.getMensaje());
    }

    @Test
    void validarActualizacion_NITEnUso() {
        dto = new ActualizacionUsuarioAdminDTO();
        dto.setNombreUsuario("user");
        dto.setIdUsuario(1L);
        dto.setCorreo("user@example.com");
        dto.setPersona(persona);

        Persona otraPersona = new Persona();
        otraPersona.setCui(54321L);
        otraPersona.setNit("12345678");

        when(personaRepository.findByNit("12345678")).thenReturn(Optional.of(otraPersona));

        ResponseBackend response = usuarioService.validarActualizacion(1L, dto);

        assertFalse(response.getOk());
        assertEquals(HttpStatus.CONFLICT, response.getStatus());
        assertEquals("El NIT ya está registrado", response.getMensaje());
    }

    @Test
    void validarActualizacion_TelefonoEnUso() {
        dto = new ActualizacionUsuarioAdminDTO();
        dto.setNombreUsuario("user");
        dto.setIdUsuario(1L);
        dto.setCorreo("user@example.com");
        dto.setPersona(persona);
        persona.setTelefono("987654321");

        Persona otraPersona = new Persona();
        otraPersona.setCui(54321L);
        otraPersona.setTelefono("987654321");

        when(personaRepository.findByTelefono("987654321")).thenReturn(Optional.of(otraPersona));

        ResponseBackend response = usuarioService.validarActualizacion(1L, dto);

        assertFalse(response.getOk());
        assertEquals(HttpStatus.CONFLICT, response.getStatus());
        assertEquals("El teléfono ya está registrado", response.getMensaje());
    }

    @Test
    void validarActualizacion_SinErrores() {
        dto = new ActualizacionUsuarioAdminDTO();
        dto.setNombreUsuario("user");
        dto.setIdUsuario(1L);
        dto.setCorreo("user@example.com");
        dto.setPersona(persona);


        when(usuarioRepository.findByNombreUsuario("user")).thenReturn(Optional.empty());
        when(personaRepository.findByCorreo("user@example.com")).thenReturn(Optional.empty());
        when(personaRepository.findByNit("12345")).thenReturn(Optional.empty());
        when(personaRepository.findByTelefono("987654321")).thenReturn(Optional.empty());

        ResponseBackend response = usuarioService.validarActualizacion(1L, dto);

        assertNull(response);
    }

    @Test
    void cambiarRol_YSeCreaNotificacion_CuandoRolEsDiferente() {
                Rol rolActual = new Rol();
        rolActual.setId(1L);
        rolActual.setNombre("Usuario");

        Rol nuevoRol = new Rol();
        nuevoRol.setId(2L);
        nuevoRol.setNombre("Administrador");


        Genero genero = new Genero();
        genero.setId(1L);
        genero.setGenero("Masculino");


        Persona persona = new Persona();
        persona.setCui(54321L);
        persona.setTelefono("987654321");
        persona.setNit("654658");

        Usuarios usuarioExistente = new Usuarios();
        usuarioExistente.setId(1L);
        usuarioExistente.setNombreUsuario("user");
        usuarioExistente.setRol(rolActual);
        usuarioExistente.setPersona(persona);

        Notificacion notificacion = new Notificacion();
        notificacion.setTitulo("Cambio de rol");
        notificacion.setMensaje("Su rol ha cambiado a " + nuevoRol.getNombre());

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioExistente));
        when(generoRepository.findById(1L)).thenReturn(Optional.of(genero));
        when(rolRepository.findById(2L)).thenReturn(Optional.of(nuevoRol));
        when(notificacionService.crearNotificacion(any(Notificacion.class))).thenReturn(notificacion);

        ActualizacionUsuarioAdminDTO dto = new ActualizacionUsuarioAdminDTO();
        dto.setIdUsuario(1L);
        dto.setIdRol(2L);
        dto.setNombreUsuario("user");
        dto.setIdGenero(1L);
        dto.setPersona(persona);

        ResponseBackend response = usuarioService.actualizarUsuario(dto);

        verify(notificacionService, times(1)).crearNotificacion(any(Notificacion.class));
        verify(buzonServiceImpl, times(1)).crearBuzon(any(Buzon.class));


        assertTrue(response.getOk());
        assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    void buscarNombreUsuario_UsuarioExistente() {

        String nombreUsuario = "usuarioPrueba";
        Usuarios usuarioEsperado = new Usuarios();
        usuarioEsperado.setId(1L);
        usuarioEsperado.setNombreUsuario(nombreUsuario);

        when(usuarioRepository.findByNombreUsuario(nombreUsuario)).thenReturn(Optional.of(usuarioEsperado));


        Optional<Usuarios> resultado = usuarioService.buscarNombreUsuario(nombreUsuario);

        assertTrue(resultado.isPresent());
        assertEquals(usuarioEsperado, resultado.get());
        verify(usuarioRepository, times(1)).findByNombreUsuario(nombreUsuario);
    }

    @Test
    void buscarNombreUsuario_UsuarioNoExistente() {
        String nombreUsuario = "usuarioInexistente";

        when(usuarioRepository.findByNombreUsuario(nombreUsuario)).thenReturn(Optional.empty());

        Optional<Usuarios> resultado = usuarioService.buscarNombreUsuario(nombreUsuario);

        assertFalse(resultado.isPresent());
        verify(usuarioRepository, times(1)).findByNombreUsuario(nombreUsuario);
    }



}
