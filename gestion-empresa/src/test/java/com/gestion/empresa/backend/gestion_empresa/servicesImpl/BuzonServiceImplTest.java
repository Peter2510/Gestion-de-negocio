package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.models.Buzon;
import com.gestion.empresa.backend.gestion_empresa.models.Notificacion;
import com.gestion.empresa.backend.gestion_empresa.repositories.BuzonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.mockito.MockitoAnnotations;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuzonServiceImplTest {

    @Mock
    private BuzonRepository buzonRepository;

    @InjectMocks
    private BuzonServiceImpl buzonService;

    private Buzon buzon;
    private Notificacion notificacion;

    @BeforeEach
    void setUp() {
        notificacion = new Notificacion();
        notificacion.setId(1L);
        notificacion.setTitulo("Prueba");
        notificacion.setMensaje("Mensaje de prueba");

        LocalDateTime fechaHoraActual = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String fechaHoraFormateada = fechaHoraActual.format(formato);

        notificacion.setFecha(fechaHoraFormateada);

        buzon = new Buzon();
        buzon.setId(1L);
        buzon.setNotificacion(notificacion);
        // Simular un usuario si es necesario
    }

    @Test
    void crearBuzon() {
        when(buzonRepository.save(buzon)).thenReturn(buzon);

        Buzon resultado = buzonService.crearBuzon(buzon);

        assertNotNull(resultado);
        assertEquals(buzon.getId(), resultado.getId());
        verify(buzonRepository, times(1)).save(buzon);
    }

    @Test
    void actualizarBuzon() {
        when(buzonRepository.save(buzon)).thenReturn(buzon);

        Buzon resultado = buzonService.actualizarBuzon(buzon);

        assertNotNull(resultado);
        assertEquals(buzon.getId(), resultado.getId());
        verify(buzonRepository, times(1)).save(buzon);
    }

    @Test
    void buscarBuzonPorId_Existente() {
        when(buzonRepository.findById(1L)).thenReturn(Optional.of(buzon));

        Optional<Buzon> resultado = buzonService.buscarBuzonPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(buzon, resultado.get());
        verify(buzonRepository, times(1)).findById(1L);
    }

    @Test
    void buscarBuzonPorId_NoExistente() {
        when(buzonRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Buzon> resultado = buzonService.buscarBuzonPorId(1L);

        assertFalse(resultado.isPresent());
        verify(buzonRepository, times(1)).findById(1L);
    }

    @Test
    void buscarBuzonesPorUsuario() {
        List<Buzon> buzonList = new ArrayList<>();
        buzonList.add(buzon);
        buzonList.add(buzon);
        when(buzonRepository.findByUsuario_Id(1L)).thenReturn(buzonList);

        List<Notificacion> resultado = buzonService.buscarBuzonesPorUsuario(1L);

        assertEquals(2, resultado.size());
        assertEquals(notificacion, resultado.get(0));
        verify(buzonRepository, times(1)).findByUsuario_Id(1L);
    }

}