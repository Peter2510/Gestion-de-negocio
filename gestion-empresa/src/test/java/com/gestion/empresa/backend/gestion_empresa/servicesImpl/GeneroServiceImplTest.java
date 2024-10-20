package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.gestion.empresa.backend.gestion_empresa.models.Genero;
import com.gestion.empresa.backend.gestion_empresa.repositories.GeneroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
class GeneroServiceImplTest {

    @Mock
    private GeneroRepository generoRepositoryMock;

    @InjectMocks
    private GeneroServiceImpl generoService;

    private Genero genero;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        genero = new Genero();
        genero.setGenero("Masculino");
    }

    @Test
    void testFindAll_Success() {
        List<Genero> generosList = new ArrayList<>();
        generosList.add(genero);

        when(generoRepositoryMock.findAll()).thenReturn(generosList);

        List<Genero> result = generoService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Masculino", result.get(0).getGenero());

        verify(generoRepositoryMock, times(1)).findAll();
    }

    @Test
    void testFindAll_EmptyList() {
        when(generoRepositoryMock.findAll()).thenReturn(new ArrayList<>());

        List<Genero> result = generoService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(generoRepositoryMock, times(1)).findAll();
    }

}