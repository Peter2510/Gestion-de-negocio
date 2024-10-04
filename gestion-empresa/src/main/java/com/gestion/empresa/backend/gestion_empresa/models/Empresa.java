package com.gestion.empresa.backend.gestion_empresa.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Empresa implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la empresa es obligatorio")
    @Column(name="nombre", nullable = false, length=100)
    private String nombre;

    @NotBlank(message = "La direccion de la empresa es obligatoria")
    @Column(name="direccion", nullable = false, length=150)
    private String direccion;

    @NotBlank(message = "El telefono de la empresa es obligatorio")
    @Column(name="telefono", nullable = false, length=10)
    private String telefono;

    @Column(name="email", nullable = true, length=100)
    private String email;

    @Column(name="logo", nullable = false)
    private String logo;

    @NotBlank(message = "La descripcion de la empresa es obligatoria")
    @Column(name="descripcion", nullable = false, length=250)
    private String descripcion;


}
