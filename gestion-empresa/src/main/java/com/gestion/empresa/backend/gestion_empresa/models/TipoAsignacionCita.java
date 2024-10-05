package com.gestion.empresa.backend.gestion_empresa.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TipoAsignacionCita implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El tipo de asignacion de cita no puede estar vacio")
    @Column(name="tipo", nullable = false)
    private String tipo;

    @NotNull(message = "El campo activo no puede estar vacio")
    @Column(name="activo", nullable = false, columnDefinition = "boolean default true")
    private Boolean activo;

}
