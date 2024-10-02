package com.gestion.empresa.backend.gestion_empresa.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusServerController {

    @GetMapping("/health")
    public ResponseEntity<String> statusServer() {
        return ResponseEntity.ok("Server is running ok");
    }

}
