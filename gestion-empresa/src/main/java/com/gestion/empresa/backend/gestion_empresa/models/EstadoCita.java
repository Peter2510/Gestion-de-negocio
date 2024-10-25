package com.gestion.empresa.backend.gestion_empresa.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * Author: alexxus
 * Created on: 24/10/24
 */
public class EstadoCita implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de dia es obligatorio")
    @Column(name="nombre", nullable = false, unique = true, length=100)
    private String nombre;
}
