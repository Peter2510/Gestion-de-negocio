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
 * Created on: 13/10/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EstadoServicio implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El estado del servicio es obligatorio")
    @Column(name="estado", nullable = false, length = 200)
    private String estado;


}
