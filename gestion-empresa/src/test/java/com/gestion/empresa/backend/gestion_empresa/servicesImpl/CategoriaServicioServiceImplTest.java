package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.models.CategoriaServicio;
import com.gestion.empresa.backend.gestion_empresa.repositories.CategoriaServicioRepository;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;


@ExtendWith(MockitoExtension.class)
class CategoriaServicioServiceImplTest {

    @Mock
    private CategoriaServicioRepository categoriaServicioRepository;

    @InjectMocks
    private CategoriaServicioServiceImpl categoriaServicioService;

    private CategoriaServicio categoriaServicio;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoriaServicio = new CategoriaServicio();
        categoriaServicio.setId(1L);
        categoriaServicio.setTipo("Tipo1");
    }

    @Test
    void testObtenerTodo() {

        List<CategoriaServicio> listaCategorias = List.of(categoriaServicio, new CategoriaServicio());
        when(categoriaServicioRepository.findAll()).thenReturn(listaCategorias);

        List<CategoriaServicio> resultado = categoriaServicioService.obtenerTodo();

        assertNotNull(resultado, "La lista de categorías no debe ser nula");

        assertEquals(2, resultado.size(), "La lista debe tener dos elementos");
        verify(categoriaServicioRepository, times(1)).findAll();
    }

    @Test
    void testIngresarCategoria() {
        when(categoriaServicioRepository.save(categoriaServicio)).thenReturn(categoriaServicio);

        CategoriaServicio resultado = categoriaServicioService.ingresarCategoria(categoriaServicio);

        assertNotNull(resultado, "La categoría ingresada no debe ser nula");
        assertEquals(categoriaServicio, resultado, "La categoría devuelta debe coincidir con el mock");

        verify(categoriaServicioRepository, times(1)).save(categoriaServicio);
    }

    @Test
    void testBuscarPorTipo_CategoriaEncontrada() {
        when(categoriaServicioRepository.findByTipo("Tipo1")).thenReturn(Optional.of(categoriaServicio));

        Optional<CategoriaServicio> resultado = categoriaServicioService.buscarPorTipo("Tipo1");

        assertTrue(resultado.isPresent(), "La categoría debe estar presente");
        assertEquals(categoriaServicio, resultado.get(), "La categoría devuelta debe coincidir con el mock");

        verify(categoriaServicioRepository, times(1)).findByTipo("Tipo1");
    }

    @Test
    void testBuscarPorTipo_CategoriaNoEncontrada() {
        when(categoriaServicioRepository.findByTipo("Tipo2")).thenReturn(Optional.empty());

        Optional<CategoriaServicio> resultado = categoriaServicioService.buscarPorTipo("Tipo2");

        assertFalse(resultado.isPresent(), "La categoría no debe estar presente");
        verify(categoriaServicioRepository, times(1)).findByTipo("Tipo2");
    }

    @Test
    void testBuscarPorId_CategoriaEncontrada() {
        when(categoriaServicioRepository.findById(1L)).thenReturn(Optional.of(categoriaServicio));

        ResponseBackend resultado = categoriaServicioService.buscarPorId(1L);

        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertTrue(resultado.getOk(), "La respuesta debe ser OK");
        assertEquals(HttpStatus.OK, resultado.getStatus(), "El estado debe ser OK");
        assertEquals(categoriaServicio, resultado.getData(), "La categoría devuelta debe coincidir con el mock");

        verify(categoriaServicioRepository, times(1)).findById(1L);
    }

    @Test
    void testBuscarPorId_CategoriaNoEncontrada() {
        when(categoriaServicioRepository.findById(2L)).thenReturn(Optional.empty());

        ResponseBackend resultado = categoriaServicioService.buscarPorId(2L);

        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertFalse(resultado.getOk(), "La respuesta debe ser false");
        assertEquals(HttpStatus.NOT_FOUND, resultado.getStatus(), "El estado debe ser NOT_FOUND");
        assertEquals("La categoria no existe", resultado.getMensaje(), "El mensaje debe coincidir");

        verify(categoriaServicioRepository, times(1)).findById(2L);
    }

    @Test
    void testRegistrarCategoria_CategoriaExistente() {
        when(categoriaServicioRepository.findByTipo("Tipo1")).thenReturn(Optional.of(categoriaServicio));

        ResponseBackend resultado = categoriaServicioService.registrarCategoria(categoriaServicio);

        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertFalse(resultado.getOk(), "La respuesta debe ser false");
        assertEquals(HttpStatus.CONFLICT, resultado.getStatus(), "El estado debe ser CONFLICT");
        assertEquals("La categoria Tipo1 ya esta registrada", resultado.getMensaje(), "El mensaje debe coincidir");
        verify(categoriaServicioRepository, times(1)).findByTipo("Tipo1");
    }

    @Test
    void testRegistrarCategoria_CategoriaNueva() {
        when(categoriaServicioRepository.findByTipo("TipoNuevo")).thenReturn(Optional.empty());
        when(categoriaServicioRepository.save(categoriaServicio)).thenReturn(categoriaServicio);

        categoriaServicio.setTipo("TipoNuevo");

        ResponseBackend resultado = categoriaServicioService.registrarCategoria(categoriaServicio);

        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertTrue(resultado.getOk(), "La respuesta debe ser true");
        assertEquals(HttpStatus.CREATED, resultado.getStatus(), "El estado debe ser CREATED");
        assertEquals("Categoria registrada correctamente", resultado.getMensaje(), "El mensaje debe coincidir");

        verify(categoriaServicioRepository, times(1)).findByTipo("TipoNuevo");
        verify(categoriaServicioRepository, times(1)).save(categoriaServicio);
    }

    @Test
    void testRegistrarCategoria_CategoriaNuevaError() {
        when(categoriaServicioRepository.findByTipo("TipoNuevo")).thenReturn(Optional.empty());
        when(categoriaServicioRepository.save(categoriaServicio)).thenReturn(null);

        categoriaServicio.setTipo("TipoNuevo");

        ResponseBackend resultado = categoriaServicioService.registrarCategoria(categoriaServicio);

        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertFalse(resultado.getOk(), "La respuesta debe ser false");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resultado.getStatus(), "El estado debe ser Internal server error");
        assertEquals("Error al crear la categoria", resultado.getMensaje(), "El mensaje debe coincidir");

        verify(categoriaServicioRepository, times(1)).findByTipo("TipoNuevo");
        verify(categoriaServicioRepository, times(1)).save(categoriaServicio);
    }

    @Test
    void testActualizarCategoria_CategoriaNoEncontrada() {
        when(categoriaServicioRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseBackend resultado = categoriaServicioService.actualizarCategoria(categoriaServicio);


        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertFalse(resultado.getOk(), "La respuesta debe ser false");
        assertEquals(HttpStatus.NOT_FOUND, resultado.getStatus(), "El estado debe ser NOT_FOUND");
        assertEquals("La categoria no esta registrada", resultado.getMensaje(), "El mensaje debe coincidir");

        verify(categoriaServicioRepository, times(1)).findById(1L);
    }

    @Test
    void testActualizarCategoria_CategoriaActualizada() {
        when(categoriaServicioRepository.findById(1L)).thenReturn(Optional.of(categoriaServicio));
        when(categoriaServicioRepository.save(categoriaServicio)).thenReturn(categoriaServicio);

        ResponseBackend resultado = categoriaServicioService.actualizarCategoria(categoriaServicio);

        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertTrue(resultado.getOk(), "La respuesta debe ser true");
        assertEquals(HttpStatus.OK, resultado.getStatus(), "El estado debe ser OK");
        assertEquals("Categoria actualizada correctamente", resultado.getMensaje(), "El mensaje debe coincidir");

        verify(categoriaServicioRepository, times(1)).findById(1L);
        verify(categoriaServicioRepository, times(1)).save(categoriaServicio);
    }

}