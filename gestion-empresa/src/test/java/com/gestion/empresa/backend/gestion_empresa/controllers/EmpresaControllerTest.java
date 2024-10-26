package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.dto.EmpresaDTO;
import com.gestion.empresa.backend.gestion_empresa.models.Empresa;
import com.gestion.empresa.backend.gestion_empresa.models.TipoAsignacionCita;
import com.gestion.empresa.backend.gestion_empresa.models.TipoServicio;
import com.gestion.empresa.backend.gestion_empresa.repositories.UsuarioRepository;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.EmpresaServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.S3ServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.TipoAsignacionCitaServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.TipoServicioServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.utils.GenerarNombreArchivo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpresaControllerTest {

    @Mock
    private EmpresaServiceImpl empresaService;

    @Mock
    private TipoServicioServiceImpl tipoServicioService;

    @Mock
    private TipoAsignacionCitaServiceImpl tipoAsignacionCitaService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private S3Client s3Client;

    @Mock
    private S3ServiceImpl s3Service;

    private String bucketName ="mock-bucket";

    @Mock
    private GenerarNombreArchivo generarNombreArchivo;

    @InjectMocks
    private EmpresaController empresaController;

    private Empresa empresa;
    private TipoServicio tipoServicio;
    private TipoAsignacionCita tipoAsignacionCita;
    private EmpresaDTO empresaDTO;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        tipoServicio = new TipoServicio();
        tipoAsignacionCita = new TipoAsignacionCita();
        empresa = new Empresa();

        tipoServicio.setId(1L);
        tipoServicio.setNombre("Canchas");

        tipoAsignacionCita.setId(1L);
        tipoAsignacionCita.setTipo("Manual");
        tipoAsignacionCita.setActivo(true);

        empresa.setId(1L);
        empresa.setNombre("Print");
        empresa.setDescripcion("De todo en impresiones");
        empresa.setEmail("empresa@correo.com");
        empresa.setTelefono("4268896");
        empresa.setLogo("logo_defecto.png");


        empresaDTO = new EmpresaDTO();
        empresaDTO.setNombre("Empresa Actualizada");
        empresaDTO.setDireccion("Dirección");
        empresaDTO.setTelefono("555123456");
        empresaDTO.setEmail("email@test.com");
        empresaDTO.setDescripcion("Descripción actualizada");
        empresaDTO.setIdTipoServicio(1L);
        empresaDTO.setIdTipoAsignacionCita(1L);

    }

    @Test
    void crearEmpresaConExitoLogo() {
        MockMultipartFile logoFile = new MockMultipartFile(
                "logo",
                "logo.png",
                MediaType.IMAGE_PNG_VALUE,
                new byte[0]
        );

        when(tipoServicioService.buscarPorId(tipoServicio.getId())).thenReturn(Optional.of(tipoServicio));
        when(tipoAsignacionCitaService.buscarPorId(tipoAsignacionCita.getId())).thenReturn(Optional.of(tipoAsignacionCita));

        ResponseEntity<Map<String, Object>> response = empresaController.crearEmpresa(empresa.getNombre(), empresa.getDireccion(), empresa.getTelefono(),
                empresa.getEmail(), empresa.getDescripcion(), 1L,1L, logoFile);

        assertNotNull(response.getBody(), "La respueta no debe ser nula");
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().containsKey("ok"));
        assertEquals(true,response.getBody().get("ok"));
        assertTrue(response.getBody().containsKey("mensaje"));
        assertEquals("Empresa creada correctamente", response.getBody().get("mensaje"));

        verify(tipoServicioService,times(1)).buscarPorId(tipoServicio.getId());
        verify(tipoAsignacionCitaService,times(1)).buscarPorId(tipoAsignacionCita.getId());

    }

    @Test
    void crearEmpresaConLogoDefecto() {
        // Preparar un MockMultipartFile vacío para el logo
        MockMultipartFile logoFile = new MockMultipartFile(
                "logo",          // Nombre del archivo
                "logo.png",      // Nombre del archivo como se muestra
                MediaType.IMAGE_PNG_VALUE, // Tipo de contenido
                new byte[0]      // Contenido vacío
        );

        // Simulaciones de servicio
        when(tipoServicioService.buscarPorId(tipoServicio.getId())).thenReturn(Optional.of(tipoServicio));
        when(tipoAsignacionCitaService.buscarPorId(tipoAsignacionCita.getId())).thenReturn(Optional.of(tipoAsignacionCita));

        // Llamada al método del controlador con el logo vacío
        ResponseEntity<Map<String, Object>> response = empresaController.crearEmpresa(
                empresa.getNombre(),
                empresa.getDireccion(),
                empresa.getTelefono(),
                empresa.getEmail(),
                empresa.getDescripcion(),
                tipoServicio.getId(),
                tipoAsignacionCita.getId(),
                logoFile // Pasando el logo vacío
        );

        // Verificaciones de la respuesta
        assertNotNull(response.getBody(), "La respuesta no debe ser nula");
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().containsKey("ok"));
        assertEquals(true, response.getBody().get("ok"));
        assertTrue(response.getBody().containsKey("mensaje"));
        assertEquals("Empresa creada correctamente", response.getBody().get("mensaje"));

        // Verificar que el logo se establece en el valor predeterminado
        verify(empresaService, times(1)).crearEmpresa(any(Empresa.class));

    }

    @Test
    void crearEmpresaConErrorImagen() {
        MockMultipartFile logoFile = new MockMultipartFile(
                "logo",
                "logo.png",
                MediaType.IMAGE_PNG_VALUE,
                "contenido".getBytes()
        );

        when(tipoServicioService.buscarPorId(tipoServicio.getId())).thenReturn(Optional.of(tipoServicio));
        when(tipoAsignacionCitaService.buscarPorId(tipoAsignacionCita.getId())).thenReturn(Optional.of(tipoAsignacionCita));

        ResponseEntity<Map<String, Object>> response = empresaController.crearEmpresa(empresa.getNombre(), empresa.getDireccion(), empresa.getTelefono(),
                empresa.getEmail(), empresa.getDescripcion(), 1L,1L, logoFile);

        assertNotNull(response.getBody(), "La respueta no debe ser nula");
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        assertTrue(response.getBody().containsKey("ok"));
        assertEquals(false,response.getBody().get("ok"));
        assertTrue(response.getBody().containsKey("ok"));
        assertTrue(response.getBody().containsKey("mensaje"));
        assertEquals("Error al subir el logo", response.getBody().get("mensaje"));

        verify(tipoServicioService,times(1)).buscarPorId(tipoServicio.getId());
        verify(tipoAsignacionCitaService,times(1)).buscarPorId(tipoAsignacionCita.getId());

    }

    @Test
    void crearEmpresaConErrorServidor() {
        MockMultipartFile logoFile = new MockMultipartFile(
                "logo",
                "logo.png",
                MediaType.IMAGE_PNG_VALUE,
                new byte[0]
        );

        when(tipoServicioService.buscarPorId(tipoServicio.getId())).thenReturn(Optional.of(tipoServicio));
        when(tipoAsignacionCitaService.buscarPorId(tipoAsignacionCita.getId())).thenReturn(Optional.of(tipoAsignacionCita));
        when(empresaService.crearEmpresa(any(Empresa.class))).thenThrow(new RuntimeException("Error al crear la empresa"));

        ResponseEntity<Map<String, Object>> response = empresaController.crearEmpresa(
                empresa.getNombre(),
                empresa.getDireccion(),
                empresa.getTelefono(),
                empresa.getEmail(),
                empresa.getDescripcion(),
                1L,
                1L,
                logoFile
        );

        assertNotNull(response.getBody(), "La respuesta no puede ser nula");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().containsKey("ok"));
        assertEquals(false, response.getBody().get("ok"));
        assertTrue(response.getBody().containsKey("mensaje"));
        assertEquals("Error al guardar la empresa", response.getBody().get("mensaje"));

        verify(tipoServicioService, times(1)).buscarPorId(tipoServicio.getId());
        verify(tipoAsignacionCitaService, times(1)).buscarPorId(tipoAsignacionCita.getId());
        verify(empresaService, times(1)).crearEmpresa(any(Empresa.class));
    }

    @Test
    void crearEmpresaConErrorTipoServicioNoExistente() {
        MockMultipartFile logoFile = new MockMultipartFile(
                "logo",
                "logo.png",
                MediaType.IMAGE_PNG_VALUE,
                new byte[0]
        );

        when(tipoServicioService.buscarPorId(tipoServicio.getId())).thenReturn(Optional.empty());

        ResponseEntity<Map<String, Object>> response = empresaController.crearEmpresa(
                empresa.getNombre(),
                empresa.getDireccion(),
                empresa.getTelefono(),
                empresa.getEmail(),
                empresa.getDescripcion(),
                1L,
                1L,
                logoFile
        );

        assertNotNull(response.getBody(), "La respuesta no puede ser nula");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().containsKey("ok"));
        assertEquals(false, response.getBody().get("ok"));
        assertTrue(response.getBody().containsKey("mensaje"));
        assertEquals("El tipo de servicio no esta registrado", response.getBody().get("mensaje"));

        verify(tipoServicioService, times(1)).buscarPorId(tipoServicio.getId());

    }

    @Test
    void crearEmpresaConErrorTipoAsignacionNoExistente() {
        MockMultipartFile logoFile = new MockMultipartFile(
                "logo",
                "logo.png",
                MediaType.IMAGE_PNG_VALUE,
                new byte[0]
        );

        when(tipoServicioService.buscarPorId(tipoServicio.getId())).thenReturn(Optional.of(tipoServicio));
        when(tipoAsignacionCitaService.buscarPorId(tipoAsignacionCita.getId())).thenReturn(Optional.empty());

        ResponseEntity<Map<String, Object>> response = empresaController.crearEmpresa(
                empresa.getNombre(),
                empresa.getDireccion(),
                empresa.getTelefono(),
                empresa.getEmail(),
                empresa.getDescripcion(),
                1L,
                1L,
                logoFile
        );

        assertNotNull(response.getBody(), "La respuesta no puede ser nula");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().containsKey("ok"));
        assertEquals(false, response.getBody().get("ok"));
        assertTrue(response.getBody().containsKey("mensaje"));
        assertEquals("El tipo de asignacion de la cita  no esta registrado", response.getBody().get("mensaje"));

        verify(tipoAsignacionCitaService, times(1)).buscarPorId(tipoAsignacionCita.getId());

    }




    @Test
    void obtenerEmpresaExistosa() {
        when(empresaService.findById(empresa.getId())).thenReturn(Optional.of(empresa));

        ResponseEntity<Map<String, Object>> response = empresaController.obtenerEmpresa(empresa.getId());

        assertNotNull(response.getBody(), "La respuesta no puede ser nula");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().containsKey("ok"));
        assertEquals(true, response.getBody().get("ok"));
        assertEquals(empresa, response.getBody().get("empresa"));
        verify(empresaService, times(1)).findById(empresa.getId());

    }

    @Test
    void obtenerEmpresaErrorExistencia() {
        when(empresaService.findById(empresa.getId())).thenReturn(Optional.empty());

        ResponseEntity<Map<String, Object>> response = empresaController.obtenerEmpresa(empresa.getId());

        assertNotNull(response.getBody(), "La respuesta no puede ser nula");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().containsKey("ok"));
        assertEquals(false, response.getBody().get("ok"));
        assertEquals("La empresa no esta registrada", response.getBody().get("mensaje"));

        verify(empresaService, times(1)).findById(empresa.getId());

    }

    @Test
    void testActualizarEmpresa_EmpresaNoEncontrada() {
        when(empresaService.findById(any(Long.class))).thenReturn(Optional.empty());

        ResponseEntity<Map<String, Object>> response = empresaController.actualizarEmpresa(1L, empresaDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("La empresa no está registrada.", response.getBody().get("mensaje"));
        verify(empresaService, times(1)).findById(any(Long.class));
    }



    @Test
    void testActualizarEmpresa_TipoServicioNoEncontrado() {
        when(empresaService.findById(any(Long.class))).thenReturn(Optional.of(empresa));
        when(tipoServicioService.buscarPorId(any(Long.class))).thenReturn(Optional.empty());

        ResponseEntity<Map<String, Object>> response = empresaController.actualizarEmpresa(1L, empresaDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("El tipo de servicio no está registrado.", response.getBody().get("mensaje"));
        verify(empresaService, times(1)).findById(any(Long.class));
        verify(tipoServicioService, times(1)).buscarPorId(any(Long.class));
    }

    @Test
    void testActualizarEmpresa_TipoAsignacionCitaNoEncontrado() {
        when(empresaService.findById(any(Long.class))).thenReturn(Optional.of(empresa));
        when(tipoServicioService.buscarPorId(any(Long.class))).thenReturn(Optional.of(tipoServicio));
        when(tipoAsignacionCitaService.buscarPorId(any(Long.class))).thenReturn(Optional.empty());

        ResponseEntity<Map<String, Object>> response = empresaController.actualizarEmpresa(1L, empresaDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("El tipo de asignación de la cita no está registrado.", response.getBody().get("mensaje"));
        verify(tipoAsignacionCitaService, times(1)).buscarPorId(any(Long.class));
    }

    @Test
    void actualizarEmpresaConErrorImagen() {
        MockMultipartFile logoFile = new MockMultipartFile(
                "logo",
                "logo.png",
                MediaType.IMAGE_PNG_VALUE,
                "contenido".getBytes()
        );

        empresaDTO.setLogoFile(logoFile);
        when(empresaService.findById(any(Long.class))).thenReturn(Optional.of(empresa));
        when(tipoServicioService.buscarPorId(tipoServicio.getId())).thenReturn(Optional.of(tipoServicio));
        when(tipoAsignacionCitaService.buscarPorId(tipoAsignacionCita.getId())).thenReturn(Optional.of(tipoAsignacionCita));

        ResponseEntity<Map<String, Object>> response = empresaController.actualizarEmpresa(1L, empresaDTO);

        assertNotNull(response.getBody(), "La respueta no debe ser nula");
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        assertTrue(response.getBody().containsKey("ok"));
        assertEquals(false,response.getBody().get("ok"));
        assertTrue(response.getBody().containsKey("ok"));
        assertTrue(response.getBody().containsKey("mensaje"));
        assertEquals("Error al subir el logo", response.getBody().get("mensaje"));

        verify(tipoServicioService,times(1)).buscarPorId(tipoServicio.getId());
        verify(tipoAsignacionCitaService,times(1)).buscarPorId(tipoAsignacionCita.getId());

    }

    @Test
    void testActualizarEmpresa_ActualizacionExitosaSinLogo() {
        when(empresaService.findById(any(Long.class))).thenReturn(Optional.of(empresa));
        when(tipoServicioService.buscarPorId(any(Long.class))).thenReturn(Optional.of(tipoServicio));
        when(tipoAsignacionCitaService.buscarPorId(any(Long.class))).thenReturn(Optional.of(tipoAsignacionCita));

        ResponseEntity<Map<String, Object>> response = empresaController.actualizarEmpresa(1L, empresaDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Datos actualizados correctamente.", response.getBody().get("mensaje"));
        verify(empresaService, times(1)).actualizarEmpresa(any(Empresa.class));
    }

    @Test
    void testActualizarEmpresa_ActualizacionExitosaConLogo() {
        MockMultipartFile logoFile = new MockMultipartFile(
                "logo",
                "logo.png",
                MediaType.IMAGE_PNG_VALUE,
                "contenido".getBytes()
        );

        empresaDTO.setLogoFile(logoFile);
        when(s3Service.uploadFile(any(MultipartFile.class), any(String.class))).thenReturn("nombreArchivoS3.jpg");
        when(empresaService.findById(any(Long.class))).thenReturn(Optional.of(empresa));
        when(tipoServicioService.buscarPorId(any(Long.class))).thenReturn(Optional.of(tipoServicio));
        when(tipoAsignacionCitaService.buscarPorId(any(Long.class))).thenReturn(Optional.of(tipoAsignacionCita));


        ResponseEntity<Map<String, Object>> response = empresaController.actualizarEmpresa(1L, empresaDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Datos actualizados correctamente.", response.getBody().get("mensaje"));
        verify(s3Service, times(1)).uploadFile(any(MultipartFile.class), any(String.class));
        verify(empresaService, times(1)).actualizarEmpresa(any(Empresa.class));
    }


}