package com.gestion.empresa.backend.gestion_empresa.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: alexxus
 * Created on: 8/10/24
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CorreoConfirmacion {
    @Id
    private Long id;

    private String correo;
    private String codigo;

}
