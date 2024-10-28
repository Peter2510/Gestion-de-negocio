package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.dto.DevolverTodoServiciosDTO;
import com.gestion.empresa.backend.gestion_empresa.dto.NuevoServicioDTO;
import com.gestion.empresa.backend.gestion_empresa.models.*;
import com.gestion.empresa.backend.gestion_empresa.repositories.*;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ServiciosServiceImplTest {

    @InjectMocks
    private ServiciosServiceImpl serviciosServiceImpl;

    @Mock
    private ServiciosRepository serviciosRepository;

    @Mock
    private JornadaLaboralRepository jornadaLaboralRepository;

    @Mock
    private DiasLaboralesRepository diasLaboralesRepository;

    @Mock
    private JornadaPorDiaRepository jornadaPorDiaRepository;

    @Mock
    private EstadoServicioRepository estadoServicioRepository;

    @Mock
    private CategoriaServicioRepository categoriaServicioRepository;

    @Mock
    private JornadaServicioRepository jornadaServicioRepository;

    @Mock
    private ServicioPrestadoRepository servicioPrestadoRepository;

    @Mock
    private DuracionServicioPrestadoRepository duracionServicioPrestadoRepository;

    @Mock
    private ServiciosAsignadoRepository serviciosAsignadoRepository;

    @Mock
    private ImagenServicioPrestadoRepository imagenServicioPrestadoRepository;

    @Mock
    private ServicioPorUsuariosRepository servicioPorUsuariosRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    private Servicios servicio;
    private NuevoServicioDTO nuevoServicioDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        servicio = new Servicios();
        servicio.setId(1L);
        servicio.setDescripcion("Servicio de prueba");
        servicio.setNombre("Servicio de prueba");

        EstadoServicio estadoServicio = new EstadoServicio();
        estadoServicio.setId(1L);
        estadoServicio.setEstado("Activo");
        servicio.setIdEstadoServicio(estadoServicio);

        nuevoServicioDTO = new NuevoServicioDTO();
        nuevoServicioDTO.setServicios(servicio);
        nuevoServicioDTO.setIdUsuario(1L);
        nuevoServicioDTO.setJornadaLaboral(new ArrayList<>());
        nuevoServicioDTO.setDiasLaborales(new ArrayList<>());
        nuevoServicioDTO.setServicioPrestados(new ArrayList<>());
        nuevoServicioDTO.setDuracionServicioPrestados(new ArrayList<>());
    }

    @Test
    public void testCrearServicio() {
        when(serviciosRepository.save(servicio)).thenReturn(servicio);

        Servicios resultado = serviciosServiceImpl.crearServicio(servicio);

        assertEquals(servicio, resultado);
        verify(serviciosRepository).save(servicio);
    }

    @Test
    public void testRegistroServicio() {
        EstadoServicio estadoServicio = new EstadoServicio();
        estadoServicio.setId(1L);
        CategoriaServicio categoriaServicio = new CategoriaServicio();
        categoriaServicio.setId(1L);

        Usuarios usuario = new Usuarios();
        usuario.setId(1L);

        when(estadoServicioRepository.findById(anyLong())).thenReturn(Optional.of(estadoServicio));
        when(categoriaServicioRepository.findById(anyLong())).thenReturn(Optional.of(categoriaServicio));
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));

        servicio.setIdTipoServicio(categoriaServicio);
        servicio.setIdEstadoServicio(estadoServicio);

        when(serviciosRepository.save(any(Servicios.class))).thenReturn(servicio);
        when(servicioPorUsuariosRepository.save(any(ServicioPorUsuarios.class))).thenReturn(new ServicioPorUsuarios());

        ResponseBackend response = serviciosServiceImpl.registroServicio(nuevoServicioDTO);

        assertNotNull(response);
        assertTrue(response.getOk());
        assertEquals(HttpStatus.CREATED, response.getStatus());
        assertEquals("Servicio registrado exitosamente", response.getMensaje());

        verify(estadoServicioRepository).findById(anyLong());
        verify(categoriaServicioRepository).findById(anyLong());
        verify(usuarioRepository).findById(anyLong());
        verify(serviciosRepository).save(any(Servicios.class));
        verify(servicioPorUsuariosRepository).save(any(ServicioPorUsuarios.class));
    }

    @Test
    public void testObtenerServicios() {
        when(serviciosRepository.findAll()).thenReturn(List.of(servicio));
        when(jornadaServicioRepository.findAll()).thenReturn(new ArrayList<>());

        DevolverTodoServiciosDTO resultado = serviciosServiceImpl.obtenerServicios();

        assertNotNull(resultado);
        assertNotNull(resultado.getJornadaServicio());
        assertTrue(resultado.getJornadaServicio().isEmpty());
    }

    @Test
    public void testObtenerSeriviosPrestadosId() {
        Long idServicio = 1L;
        Servicios servicioEncontrado = new Servicios();
        when(serviciosRepository.findById(idServicio)).thenReturn(Optional.of(servicioEncontrado));

        List<ServiciosAsignado> serviciosAsignados = new ArrayList<>();
        serviciosAsignados.add(new ServiciosAsignado());
        when(serviciosAsignadoRepository.findAllByIdServicio(servicioEncontrado)).thenReturn(serviciosAsignados);

        List<ServicioPrestado> resultado = serviciosServiceImpl.obtenerSeriviosPrestadosId(idServicio);

        assertEquals(1, resultado.size());
        verify(serviciosRepository).findById(idServicio);
    }
}
