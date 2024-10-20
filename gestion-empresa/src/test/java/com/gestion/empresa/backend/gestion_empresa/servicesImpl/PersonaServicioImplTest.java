package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.gestion.empresa.backend.gestion_empresa.models.Persona;
import com.gestion.empresa.backend.gestion_empresa.repositories.PersonaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
class PersonaServicioImplTest {

    @Mock
    private PersonaRepository personaRepositoryMock;

    @InjectMocks
    private PersonaServicioImpl personaServicio;

    private Persona persona;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        persona = new Persona();
        persona.setCorreo("correo@test.com");
        persona.setNit("123456789");
        persona.setTelefono("987654321");
    }

    @Test
    void testBuscarPorCorreo_Success() {
        when(personaRepositoryMock.findByCorreo(persona.getCorreo())).thenReturn(Optional.of(persona));

        Optional<Persona> result = personaServicio.buscarPorCorreo(persona.getCorreo());

        assertTrue(result.isPresent());
        assertEquals("correo@test.com", result.get().getCorreo());

        verify(personaRepositoryMock, times(1)).findByCorreo(persona.getCorreo());
    }

    @Test
    void testBuscarPorCorreo_NotFound() {
        when(personaRepositoryMock.findByCorreo("correo_inexistente@test.com")).thenReturn(Optional.empty());

        Optional<Persona> result = personaServicio.buscarPorCorreo("correo_inexistente@test.com");

        assertFalse(result.isPresent());

        verify(personaRepositoryMock, times(1)).findByCorreo("correo_inexistente@test.com");
    }

    @Test
    void testBuscarPorNit_Success() {
        when(personaRepositoryMock.findByNit(persona.getNit())).thenReturn(Optional.of(persona));

        Optional<Persona> result = personaServicio.buscarPorNit(persona.getNit());

        assertTrue(result.isPresent());
        assertEquals("123456789", result.get().getNit());

        verify(personaRepositoryMock, times(1)).findByNit(persona.getNit());
    }

    @Test
    void testBuscarPorNit_NotFound() {
        when(personaRepositoryMock.findByNit("999999999")).thenReturn(Optional.empty());

        Optional<Persona> result = personaServicio.buscarPorNit("999999999");

        assertFalse(result.isPresent());

        verify(personaRepositoryMock, times(1)).findByNit("999999999");
    }

    @Test
    void testBuscarPorTelefono_Success() {
        when(personaRepositoryMock.findByTelefono(persona.getTelefono())).thenReturn(Optional.of(persona));

        Optional<Persona> result = personaServicio.buscarPorTelefono(persona.getTelefono());

        assertTrue(result.isPresent());
        assertEquals("987654321", result.get().getTelefono());

        verify(personaRepositoryMock, times(1)).findByTelefono(persona.getTelefono());
    }

    @Test
    void testBuscarPorTelefono_NotFound() {
        when(personaRepositoryMock.findByTelefono("111111111")).thenReturn(Optional.empty());

        Optional<Persona> result = personaServicio.buscarPorTelefono("111111111");

        assertFalse(result.isPresent());

        verify(personaRepositoryMock, times(1)).findByTelefono("111111111");
    }

    @Test
    void testGuardarPersona() {
        when(personaRepositoryMock.save(persona)).thenReturn(persona);

        Persona result = personaServicio.guardarPersona(persona);

        assertNotNull(result);
        assertEquals(persona.getCorreo(), result.getCorreo());

        verify(personaRepositoryMock, times(1)).save(persona);
    }

    @Test
    void testListarPersonas() {
        List<Persona> personasList = new ArrayList<>();
        personasList.add(persona);

        when(personaRepositoryMock.findAll()).thenReturn(personasList);

        List<Persona> result = personaServicio.listarPersonas();

        assertEquals(1, result.size());

        verify(personaRepositoryMock, times(1)).findAll();
    }



}