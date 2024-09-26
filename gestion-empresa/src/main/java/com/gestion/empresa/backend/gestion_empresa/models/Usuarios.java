package com.gestion.empresa.backend.gestion_empresa.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

//clase para los usuarios
@Entity
@Table(name = "usuarios")
@Data //con lombook se genera getters, setters, toString, equals, hashCode, etc
@NoArgsConstructor //con esto se genera un constructor vacío ( quee es el que pide JPA y no tira clavo).
@AllArgsConstructor //genera un constructor con todos los campos y ya lo utilizamos para algun caso
public class Usuarios implements Serializable {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(nullable = false, updatable = true)
    private long id;
    @Column(name = "nombreUsuario", nullable = false, length = 200, unique = true)

    private String nombreUsuario;
    @Column(name = "password", nullable = false, length = 200)

    private String password;
    @Column(name = "idTipoUsuario", nullable = false)

    private long idTipoUsuario;
    @Column(name = "idPersona", nullable = false)

    private  long idPersona;


}