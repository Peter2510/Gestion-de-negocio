package com.gestion.empresa.backend.gestion_empresa.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;


import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="rol")
public class Rol implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del rol es obligatorio")
    @Column(name="nombre", nullable = false, unique = true, length=100)
    private String nombre;

    @NotBlank(message = "La descripcion del rol es obligatoria")
    @Column(name="descripcion", nullable = false, length=200)
    private String descripcion;

}