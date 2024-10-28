package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.models.JornadaLaboral;
import com.gestion.empresa.backend.gestion_empresa.repositories.JornadaLaboralRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JornadaLaboralServiceImplTest {

    @InjectMocks
    private JornadaLaboralServiceImpl jornadaLaboralService;

    @Mock
    private JornadaLaboralRepository jornadaLaboralRepository;

    private JornadaLaboral jornadaLaboral;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jornadaLaboral = new JornadaLaboral();
        jornadaLaboral.setId(1L);
        jornadaLaboral.setNombre("Jornada 1");
        jornadaLaboral.setHoraInicio(LocalTime.of(9, 0));
        jornadaLaboral.setHoraFin(LocalTime.of(17, 0));
    }

    @Test
    void testIngresarJornada() {
        when(jornadaLaboralRepository.save(any(JornadaLaboral.class))).thenReturn(jornadaLaboral);

        JornadaLaboral resultado = jornadaLaboralService.ingresarJornada(jornadaLaboral);

        assertNotNull(resultado);
        assertEquals(jornadaLaboral.getId(), resultado.getId());
        assertEquals(jornadaLaboral.getNombre(), resultado.getNombre());
    }
}
