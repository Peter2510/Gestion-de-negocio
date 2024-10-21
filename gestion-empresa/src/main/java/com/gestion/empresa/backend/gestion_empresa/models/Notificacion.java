package com.gestion.empresa.backend.gestion_empresa.models;


/*
    Author: peterg
    Created on: 21/10/24
*/

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notificacion implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="titulo", nullable = false)
    private String titulo;

    @Column(name="mensaje", nullable = false)
    private String mensaje;

    @Column(name="leido", nullable = false)
    private boolean leido;

    @Column(name="fecha", nullable = false)
    private Date fecha;

}
