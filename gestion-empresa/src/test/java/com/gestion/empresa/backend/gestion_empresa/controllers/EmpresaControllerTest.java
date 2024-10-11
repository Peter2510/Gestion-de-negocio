package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.models.Empresa;
import com.gestion.empresa.backend.gestion_empresa.models.TipoAsignacionCita;
import com.gestion.empresa.backend.gestion_empresa.models.TipoServicio;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.EmpresaServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.S3ServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.TipoAsignacionCitaServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.TipoServicioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import software.amazon.awssdk.services.s3.S3Client;

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
    private S3Client s3Client;

    @Mock
    private S3ServiceImpl s3Service;

    private String bucketName ="mock-bucket";

    @InjectMocks
    private EmpresaController empresaController;

    private Empresa empresa;
    private TipoServicio tipoServicio;
    private TipoAsignacionCita tipoAsignacionCita;

    @BeforeEach
    void setUp() {
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



    }

    @Test
    void crearEmpresaConExito() {
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
    void obtenerEmpresa() {
    }

    @Test
    void actualizarEmpresa() {
    }
}