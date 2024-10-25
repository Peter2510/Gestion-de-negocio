package com.gestion.empresa.backend.gestion_empresa.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * Author: alexxus
 * Created on: 24/10/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Citas implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La hora de inicio de cita es obligatoria")
    @Column(name="horaInicio", nullable = false)
    private LocalDateTime fechaHoraInicio;

    @NotNull(message = "La hora de fin de cita es obligatoria")
    @Column(name="horaFin", nullable = false)
    private LocalDateTime fechaHoraFin;

    //aca ver para el foreing key
    @ManyToOne
    @JoinColumn(name = "idEstadoCita", nullable = false)
    private EstadoCita idEstadoCita;
    @ManyToOne
    @JoinColumn(name = "idServicio", nullable = false)
    private Servicios idServicio;
    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuarios idUsuario;


    // solo para validar
    @ManyToOne
    @JoinColumn(name = "idDiaLaboral", nullable = false)
    private DiasLaborales idDiaLaboral;
}
