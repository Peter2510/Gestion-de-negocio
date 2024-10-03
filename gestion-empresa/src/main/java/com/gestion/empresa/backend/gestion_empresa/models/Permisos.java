package com.gestion.empresa.backend.gestion_empresa.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;

@Entity
@Table(name = "Permiso")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permisos {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nombre", nullable = false, length = 200, unique = true)
    private String nombre;
}
