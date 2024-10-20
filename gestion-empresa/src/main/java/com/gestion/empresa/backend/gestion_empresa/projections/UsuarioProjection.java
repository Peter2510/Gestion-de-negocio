package com.gestion.empresa.backend.gestion_empresa.projections;


import com.gestion.empresa.backend.gestion_empresa.models.Persona;
import com.gestion.empresa.backend.gestion_empresa.models.Rol;

/**
 * Author: gordillox
 * Created on: 19/10/24
 */

public interface UsuarioProjection {
    Long getId();
    Long getNombreUsuario();
    Rol getRol();
    Persona getPersona();
    boolean getActivo();
    boolean getA2fActivo();
}
