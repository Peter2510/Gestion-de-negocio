package com.gestion.empresa.backend.gestion_empresa.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;

@Entity
@Table(name = "PermisoRol")
@Data //con lombook se genera getters, setters, toString, equals, hashCode, etc
@NoArgsConstructor //con esto se genera un constructor vacío ( quee es el que pide JPA y no tira clavo).
@AllArgsConstructor //genera un constructor con todos los campos y ya lo utilizamos para algun caso
public class PermisoRol {

    //esto es para validar como que la version del serializable
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
