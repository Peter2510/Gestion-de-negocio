package com.gestion.empresa.backend.gestion_empresa.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * Author: alexxus
 * Created on: 13/10/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DuracionServicioPrestado implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la duracion es obligatoria")
    @Column(name="nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name="duracion", nullable = false)
    //aca ver si es long
    private Long duracion;

    //aca ver para el foreing key
    @ManyToOne
    @JoinColumn(name = "idServicioPrestado", nullable = false)
    private ServicioPrestado idServicioPrestado;


}
