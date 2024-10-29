package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.dto.RegistroCitasDTO;
import com.gestion.empresa.backend.gestion_empresa.models.*;
import com.gestion.empresa.backend.gestion_empresa.repositories.CitasRepository;
import com.gestion.empresa.backend.gestion_empresa.repositories.DiasLaboralesRepository;
import com.gestion.empresa.backend.gestion_empresa.repositories.EstadoCitaRepository;
import com.gestion.empresa.backend.gestion_empresa.repositories.ServiciosRepository;
import com.gestion.empresa.backend.gestion_empresa.repositories.UsuarioRepository;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CitasServiceImplTest {

    @InjectMocks
    private CitasServiceImpl citasService;

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private EstadoCitaRepository estadoCitaRepository;
    @Mock
    private DiasLaboralesRepository diasLaboralesRepository;
    @Mock
    private ServiciosRepository serviciosRepository;
    @Mock
    private CitasRepository citasRepository;

    private RegistroCitasDTO registroCitasDTO;
    private Usuarios usuario;
    private EstadoCita estadoCita;
    private DiasLaborales diaLaboral;
    private Servicios servicio;
    private List<Citas> citasList;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        registroCitasDTO = new RegistroCitasDTO();
        registroCitasDTO.setHoraInicio(LocalDateTime.now());
        registroCitasDTO.setHoraFin(LocalDateTime.now().plusHours(1));
        registroCitasDTO.setIdUsuario(1L);
        registroCitasDTO.setIdEstadoCita(1L);
        registroCitasDTO.setIdDiaLaboral(1L);
        registroCitasDTO.setIdServicio(1L);

        usuario = new Usuarios();
        usuario.setId(1L);
        estadoCita = new EstadoCita();
        estadoCita.setId(1L);
        diaLaboral = new DiasLaborales();
        diaLaboral.setId(1L);
        servicio = new Servicios();
        servicio.setId(1L);

        registroCitasDTO = new RegistroCitasDTO();
        registroCitasDTO.setIdUsuario(1L);
        registroCitasDTO.setIdEstadoCita(1L);
        registroCitasDTO.setIdDiaLaboral(1L);
        registroCitasDTO.setIdServicio(1L);
        registroCitasDTO.setHoraInicio(LocalDateTime.parse("2023-10-25T10:00:00"));
        registroCitasDTO.setHoraFin(LocalDateTime.parse("2023-10-25T11:00:00"));

        citasList = new ArrayList<>();
        Citas cita1 = new Citas();
        Citas cita2 = new Citas();
        citasList.add(cita1);
        citasList.add(cita2);
    }

    @Test
    void testRegistrarCitas_UserNotFound() {
        when(usuarioRepository.findById(registroCitasDTO.getIdUsuario())).thenReturn(Optional.empty());

        ResponseBackend response = citasService.registrarCitas(registroCitasDTO);

        assertFalse(response.getOk());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        assertTrue(response.getMensaje().contains("El usuario no se encuentra registrado"));
    }

    @Test
    void testRegistrarCitas_UsuarioNoEncontrado() {
        when(usuarioRepository.findById(registroCitasDTO.getIdUsuario())).thenReturn(Optional.empty());

        ResponseBackend response = citasService.registrarCitas(registroCitasDTO);

        assertEquals(false, response.getOk());
        assertEquals("El usuario no se encuentra registrado", response.getMensaje());
    }

//    @Test
//    void testRegistrarCitas_EstadoNoEncontrado() {
//        Usuarios usuario = new Usuarios();
//        when(usuarioRepository.findById(registroCitasDTO.getIdUsuario())).thenReturn(Optional.of(usuario));
//        when(estadoCitaRepository.findById(registroCitasDTO.getIdEstadoCita())).thenReturn(Optional.empty());
//
//        ResponseBackend response = citasService.registrarCitas(registroCitasDTO);
//
//        assertEquals(false, response.getOk());
//        assertEquals("Error al registrar la cita: El estado no se encuentra registrado", response.getMensaje());
//    }

//    @Test
//    void testRegistrarCitas_DiaNoEncontrado() {
//        Usuarios usuario = new Usuarios();
//        EstadoCita estadoCita = new EstadoCita();
//        when(usuarioRepository.findById(registroCitasDTO.getIdUsuario())).thenReturn(Optional.of(usuario));
//        when(estadoCitaRepository.findById(registroCitasDTO.getIdEstadoCita())).thenReturn(Optional.of(estadoCita));
//        when(diasLaboralesRepository.findById(registroCitasDTO.getIdDiaLaboral())).thenReturn(Optional.empty());
//
//        ResponseBackend response = citasService.registrarCitas(registroCitasDTO);
//
//        assertEquals(false, response.getOk());
//        assertEquals("Error al registrar la cita: El dia no se encuentra registrado", response.getMensaje());
//    }

//    @Test
//    void testRegistrarCitas_ServicioNoEncontrado() {
//        Usuarios usuario = new Usuarios();
//        EstadoCita estadoCita = new EstadoCita();
//        DiasLaborales diaLaboral = new DiasLaborales();
//        when(usuarioRepository.findById(registroCitasDTO.getIdUsuario())).thenReturn(Optional.of(usuario));
//        when(estadoCitaRepository.findById(registroCitasDTO.getIdEstadoCita())).thenReturn(Optional.of(estadoCita));
//        when(diasLaboralesRepository.findById(registroCitasDTO.getIdDiaLaboral())).thenReturn(Optional.of(diaLaboral));
//        when(serviciosRepository.findById(registroCitasDTO.getIdServicio())).thenReturn(Optional.empty());
//
//        ResponseBackend response = citasService.registrarCitas(registroCitasDTO);
//
//        assertEquals(false, response.getOk());
//        assertEquals("Error al registrar la cita: El servicio no se encuentra registrado", response.getMensaje());
//    }

    // @Test
    // void testRegistrarCitas_Success() {
    //     Usuarios usuario = new Usuarios();
    //     EstadoCita estadoCita = new EstadoCita();
    //     DiasLaborales diaLaboral = new DiasLaborales();
    //     Servicios servicio = new Servicios();
    //     Citas nuevaCita = new Citas();

    //     when(usuarioRepository.findById(registroCitasDTO.getIdUsuario())).thenReturn(Optional.of(usuario));
    //     when(estadoCitaRepository.findById(registroCitasDTO.getIdEstadoCita())).thenReturn(Optional.of(estadoCita));
    //     when(diasLaboralesRepository.findById(registroCitasDTO.getIdDiaLaboral())).thenReturn(Optional.of(diaLaboral));
    //     when(serviciosRepository.findById(registroCitasDTO.getIdServicio())).thenReturn(Optional.of(servicio));
    //     when(citasRepository.save(any(Citas.class))).thenReturn(nuevaCita);

    //     ResponseBackend response = citasService.registrarCitas(registroCitasDTO);

    //     assertEquals(true, response.getOk());
    //     assertEquals(HttpStatus.CREATED, response.getStatus());
    //     assertEquals("CITA registrada correctamente", response.getMensaje());
    // }

    @Test
    void testObtenerTodasCitas() {
        when(citasRepository.findAll()).thenReturn(citasList);

        List<Citas> result = citasService.obtenerTodasCitas();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(citasList, result);
    }

}
