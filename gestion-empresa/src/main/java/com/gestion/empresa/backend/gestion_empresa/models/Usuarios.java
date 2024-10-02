package com.gestion.empresa.backend.gestion_empresa.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

//clase para los usuarios
@Entity
@Table(name = "usuarios")
@Data //con lombook se genera getters, setters, toString, equals, hashCode, etc
@NoArgsConstructor //con esto se genera un constructor vacío ( quee es el que pide JPA y no tira clavo).
@AllArgsConstructor //genera un constructor con todos los campos y ya lo utilizamos para algun caso
@Builder // para qye genere todo
public class Usuarios implements Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(nullable = false, updatable = true)
    private long id;
    @Column(name = "nombreUsuario", nullable = false, length = 200, unique = true)

    private String nombreUsuario;
    @Column(name = "password", nullable = false, length = 200)
    private String password;

    @ManyToOne
    @JoinColumn(name = "idRol", nullable = false)
    private Rol rol;
    @ManyToOne
    @JoinColumn(name = "idPersona", nullable = false)
    private Persona persona;

    @Column(name = "activo", nullable = false, length = 200)
    private boolean activo;

    @Column(name = "a2fActivo", nullable = false, length = 200)
    private boolean a2fActivo;


    public Usuarios(String nombreUsuario, String encode, Rol rol, Persona persona, boolean b, boolean b1) {
    }


    // para lo de userDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((rol.getTipo())));
    }

    @Override
    public String getUsername() {
        return "";
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}