package com.gestion.empresa.backend.gestion_empresa.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * Author: alexxus
 * Created on: 24/10/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DetalleCita implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //aca ver para el foreing key

    @Column(name="idCita", nullable = false)
    private Citas idCita;
    @ManyToOne
    @JoinColumn(name = "idServicioPrestado")
    private ServicioPrestado idServicioPrestado;
}
