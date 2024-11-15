package com.gestion.empresa.backend.gestion_empresa.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;


@Data
@NoArgsConstructor 
@AllArgsConstructor
@Entity
public class PermisoRol implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "idRol", nullable = false)
    private Rol rol;
   
    @ManyToOne
    @JoinColumn(name = "idPermiso", nullable = false)
    private Permiso permiso;

}
