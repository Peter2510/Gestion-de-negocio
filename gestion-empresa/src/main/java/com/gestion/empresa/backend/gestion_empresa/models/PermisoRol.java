package com.gestion.empresa.backend.gestion_empresa.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;

@Entity
@Table(name = "PermisoRol")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class PermisoRol {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "idRol", nullable = false)
    private Rol rol;
   
    @ManyToOne
    @JoinColumn(name = "idPermisos", nullable = false)
    private Permisos permisos;

}
