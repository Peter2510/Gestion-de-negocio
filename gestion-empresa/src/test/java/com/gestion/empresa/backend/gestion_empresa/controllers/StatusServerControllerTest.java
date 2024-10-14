package com.gestion.empresa.backend.gestion_empresa.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatusServerControllerTest {

    @InjectMocks
    private StatusServerController statusServerController;

    @Test
    void statusServer() {

        ResponseEntity<Map<String, Object>> response = statusServerController.statusServer();
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().containsKey("ok"));
        assertEquals(true,response.getBody().get("ok"));
        assertEquals("Server running ok",response.getBody().get("mensaje"));
    }
}