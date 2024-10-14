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
 * Created on: 12/10/24
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Servicios  implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del servicio es obligatorio")
    @Column(name="nombre", nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "La descripcion del servicio es obligatoria")
    @Column(name="descripcion", nullable = false, length = 100)
    //aca ver si es long
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "idEstadoServicio", nullable = false)
    private EstadoServicio idEstadoServicio;

    @ManyToOne
    @JoinColumn(name = "idTipoServicio", nullable = false)
    private CategoriaServicio idTipoServicio;


}