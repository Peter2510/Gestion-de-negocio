package com.gestion.empresa.backend.gestion_empresa.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class StatusServerController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> statusServer() {

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("ok", true, "mensaje", "Server running ok"));
    }
}
