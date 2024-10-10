package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.models.TipoAsignacionCita;
import com.gestion.empresa.backend.gestion_empresa.models.TipoServicio;
import com.gestion.empresa.backend.gestion_empresa.services.TipoAsignacionCitaService;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.TipoAsignacionCitaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TipoAsignacionCitaControllerTest {

    @Mock
    private TipoAsignacionCitaServiceImpl tipoAsignacionCitaService;

    @InjectMocks
    private TipoAsignacionCitaController tipoAsignacionCitaController;

    private TipoAsignacionCita tipoAsignacionCita;

    @BeforeEach
    void setUp() {
        tipoAsignacionCita = new TipoAsignacionCita();
        tipoAsignacionCita.setId(1L);
        tipoAsignacionCita.setTipo("Aleatorio");
        tipoAsignacionCita.setActivo(true);
    }

    @Test
    void obtenerTiposAsignacionCita() {
        when(tipoAsignacionCitaService.obtenerTipoAsignacionCitaRegistrados()).thenReturn(List.of(tipoAsignacionCita));

        ResponseEntity<Map<String, Object>> registros = tipoAsignacionCitaController.obtenerTiposAsignacionCitaRegistrados();

        assertEquals(HttpStatus.OK, registros.getStatusCode());
        assertEquals(true, registros.getBody().get("ok"));
        assertEquals(List.of(tipoAsignacionCita), registros.getBody().get("tiposAsignacionCita"));

        verify(tipoAsignacionCitaService, times(1)).obtenerTipoAsignacionCitaRegistrados();

    }



}