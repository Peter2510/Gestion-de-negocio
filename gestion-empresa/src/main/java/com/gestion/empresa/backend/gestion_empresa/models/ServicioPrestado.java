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
public class ServicioPrestado implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El precio del servicio especifico es obligatorio")
    @Column(name="precio", nullable = false)
    private Double precio;

    @NotBlank(message = "La nombre del servicio especifico es obligatorio")
    @Column(name="nombre", nullable = false, length = 200)
    //aca ver si es long
    private Long nombre;


    //aca ver para el foreing key
    @ManyToOne
    @JoinColumn(name = "idEstadoServicio", nullable = false)
    private EstadoServicio idEstadoServicio;


    //aca ver para el foreing key
    @ManyToOne
    @JoinColumn(name = "idDuracionServicioPrestado", nullable = false)
    private DuracionServicioPrestado idduracionServicioPrestado;

}
