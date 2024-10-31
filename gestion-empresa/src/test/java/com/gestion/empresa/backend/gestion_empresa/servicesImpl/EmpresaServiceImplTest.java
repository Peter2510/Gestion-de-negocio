package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.models.Empresa;
import com.gestion.empresa.backend.gestion_empresa.repositories.EmpresaRepository;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpresaServiceImplTest {

    @Mock
    private EmpresaRepository empresaRepository;

    @InjectMocks
    private EmpresaServiceImpl empresaService;

    @Mock
    private S3ServiceImpl s3Service;

    private Empresa empresa;

    @BeforeEach
    void setUp() {
        empresa = new Empresa();
        empresa.setId(1L);
        empresa.setNombre("Optica 1");
        empresa.setDescripcion("Confianos el cuidado de tus ojos");
    }

    @Test
    void findByIdExistente() {
        when(empresaRepository.findById(empresa.getId())).thenReturn(Optional.of(empresa));

        Optional<Empresa> busqueda = empresaService.findById(empresa.getId());

        assertTrue(busqueda.isPresent());
        assertEquals(empresa.getId(), busqueda.get().getId());
        assertEquals(empresa.getNombre(), busqueda.get().getNombre());
        assertEquals(empresa.getDescripcion(), busqueda.get().getDescripcion());

        verify(empresaRepository,times(1)).findById(empresa.getId());
    }

    @Test
    void findByIdNoExistente() {
        when(empresaRepository.findById(empresa.getId())).thenReturn(Optional.empty());
        Optional<Empresa> busqueda = empresaService.findById(empresa.getId());
        assertTrue(busqueda.isEmpty());
        verify(empresaRepository,times(1)).findById(empresa.getId());
    }

    @Test
    void crearEmpresaExitosa() {
        when(empresaRepository.save(empresa)).thenReturn(empresa);
        Empresa creacion = empresaService.crearEmpresa(empresa);
        assertNotNull(creacion);
        assertEquals(empresa.getId(), creacion.getId());
        assertEquals(empresa.getNombre(), creacion.getNombre());
        verify(empresaRepository,times(1)).save(empresa);
    }

    @Test
    void crearEmpresaError() {
        when(empresaRepository.save(empresa)).thenReturn(null);
        Empresa creacion = empresaService.crearEmpresa(empresa);
        assertNull(creacion);
        verify(empresaRepository,times(1)).save(empresa);
    }


    @Test
    void actualizarEmpresa() {
        when(empresaRepository.findById(empresa.getId())).thenReturn(Optional.of(empresa));
        empresa.setNombre("Optica 2");
        empresaService.actualizarEmpresa(empresa);
        assertEquals("Optica 2", empresa.getNombre());
        verify(empresaRepository,times(1)).findById(empresa.getId());
    }


    @Test
    public void testObtenerLogoBase64_EmpresaNoExistente() {
        when(empresaRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseBackend response = empresaService.obtenerLogoBase64(1L);

        assertFalse(response.getOk());
        assertEquals("La empresa no existe", response.getMensaje());
    }

    @Test
    public void testObtenerLogoBase64_SinLogo() {
        empresa.setLogo(null);
        when(empresaRepository.findById(1L)).thenReturn(Optional.of(empresa));

        ResponseBackend response = empresaService.obtenerLogoBase64(1L);

        assertFalse(response.getOk());
        assertEquals("La empresa no existe", response.getMensaje());
    }


}