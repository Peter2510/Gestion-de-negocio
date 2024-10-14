package com.gestion.empresa.backend.gestion_empresa.models;

import jakarta.persistence.*;
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
public class ServiciosAsignado implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idServicio", nullable = false)
    private Servicios idServicio;

    @ManyToOne
    @JoinColumn(name = "idServicioPrestado", nullable = false)
    private ServicioPrestado idServicioPrestado;


}