package com.gestion.empresa.backend.gestion_empresa.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalTime;

/**
 * Author: alexxus
 * Created on: 13/10/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class JornadaLaboral implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de jornada es obligatorio")
    @Column(name="nombre", nullable = false, length = 100)
    private String nombre;

    @NotNull(message = "La hora de inicio de jornada es obligatoria")
    @Column(name="horaInicio", nullable = false)
    private LocalTime horaInicio;

    @NotNull(message = "La hora de fin de jornada es obligatoria")
    @Column(name="horaFin", nullable = false)
    private LocalTime horaFin;
}
