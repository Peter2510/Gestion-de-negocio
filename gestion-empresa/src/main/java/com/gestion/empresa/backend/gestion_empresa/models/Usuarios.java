package com.gestion.empresa.backend.gestion_empresa.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

//clase para los usuarios
@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuarios implements Serializable, UserDetails {
   
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(nullable = false, updatable = true)
    private long id;
    @Column(name = "nombreUsuario", nullable = false, length = 200, unique = true)

    private String nombreUsuario;
    @Column(name = "password", nullable = false, length = 200)
    private String password;

    @OneToOne
    @JoinColumn(name = "idRol", nullable = false)

    private Rol rol;
    @ManyToOne
    @JoinColumn(name = "idPersona", nullable = false)
    private Persona persona;

    @Column(name = "activo", nullable = false, length = 200)
    private boolean activo;

    @Column(name = "a2fActivo", nullable = false, length = 200)
    private boolean a2fActivo;

    @Column(name = "verification_code")
    private String verificationCode;
    @Column(name = "verification_expiration")
    private LocalDateTime verificationCodeExpiresAt;

    public Usuarios(String nombreUsuario, String encode, Rol rol, Persona persona, boolean b, boolean b1) {
    }

    // para lo de userDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}