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

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Buzon implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuarios usuario;

    @ManyToOne
    @JoinColumn(name = "idNotificacion", nullable = false)
    private Notificacion notificacion;

}
