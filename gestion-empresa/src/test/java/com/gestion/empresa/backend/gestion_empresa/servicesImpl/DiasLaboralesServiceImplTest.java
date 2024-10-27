package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.models.DiasLaborales;
import com.gestion.empresa.backend.gestion_empresa.repositories.DiasLaboralesRepository;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.DiasLaboralesServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiasLaboralesServiceImplTest {

    @InjectMocks
    private DiasLaboralesServiceImpl diasLaboralesService;

    @Mock
    private DiasLaboralesRepository diasLaboralesRepository;

    private DiasLaborales diasLaborales;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        diasLaborales = new DiasLaborales();
        diasLaborales.setId(1L);
        diasLaborales.setNombre("Lunes");
    }

    @Test
    public void testObtenerTodosDias() {
        List<DiasLaborales> diasList = new ArrayList<>();
        diasList.add(diasLaborales);
        when(diasLaboralesRepository.findAll()).thenReturn(diasList);

        List<DiasLaborales> result = diasLaboralesService.obtenerTodosDias();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Lunes", result.get(0).getNombre());
    }

    @Test
    public void testIngresarDias() {
        when(diasLaboralesRepository.save(any(DiasLaborales.class))).thenReturn(diasLaborales);

        DiasLaborales result = diasLaboralesService.ingresarDias(diasLaborales);

        assertNotNull(result);
        assertEquals("Lunes", result.getNombre());
    }

}