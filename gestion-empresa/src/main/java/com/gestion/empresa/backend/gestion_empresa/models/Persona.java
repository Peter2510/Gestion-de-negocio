package com.gestion.empresa.backend.gestion_empresa.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;


@Entity
@Table(name = "Persona")
@Data //con lombook se genera getters, setters, toString, equals, hashCode, etc
@NoArgsConstructor //con esto se genera un constructor vacío ( quee es el que pide JPA y no tira clavo).
@AllArgsConstructor //genera un constructor con todos los campos y ya lo utilizamos para algun caso
public class Persona implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long cui;
    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

    @Column(name = "nit", nullable = false, length = 200, unique = true)
    private String nit;

    @Column(name = "numero", nullable = false, length = 200, unique = true)
    private  long numero;

    @Column(name = "correo", nullable = false, length = 200, unique = true)
    private  String correo;

    @Column(name = "direccion", nullable = false, length = 200)
    private  String direccion;


    @Column(name = "telefono", nullable = false, length = 200)
    private  int telefono;


    // aca para la foranea del genero
    @ManyToOne
    @JoinColumn(name = "idGenero", nullable = false)
    private Genero genero;

}
