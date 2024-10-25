package com.gestion.empresa.backend.gestion_empresa.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Author: alexxus
 * Created on: 24/10/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EstadoCita implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de estado de cita es obligatorio")
    @Column(name="nombre", nullable = false, unique = true, length=100)
    private String nombre;
}
