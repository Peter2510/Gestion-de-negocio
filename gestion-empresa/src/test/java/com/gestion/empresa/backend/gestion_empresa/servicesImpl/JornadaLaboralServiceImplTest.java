package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.models.JornadaPorDia;
import com.gestion.empresa.backend.gestion_empresa.repositories.JornadaPorDiaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JornadaPorDiaServiceImplTest {

    @InjectMocks
    private JornadaPorDiaServiceImpl jornadaPorDiaService;

    @Mock
    private JornadaPorDiaRepository jornadaPorDiaRepository;

    private JornadaPorDia jornadaPorDia;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        jornadaPorDia = new JornadaPorDia();
        jornadaPorDia.setId(1L);
    }

    @Test
    public void testIngresarJornadaDia() {
        when(jornadaPorDiaRepository.save(jornadaPorDia)).thenReturn(jornadaPorDia);

        JornadaPorDia result = jornadaPorDiaService.ingresarJornadaDia(jornadaPorDia);

        assertNotNull(result);
    }
}
