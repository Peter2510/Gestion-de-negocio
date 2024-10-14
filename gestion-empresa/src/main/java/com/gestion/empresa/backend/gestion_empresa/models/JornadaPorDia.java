package com.gestion.empresa.backend.gestion_empresa.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalTime;

/**
 * Author: alexxus
 * Created on: 13/10/24
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class JornadaPorDia implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idDiaLaboral", nullable = false)
    private DiasLaborales idDiaLaboral;

    @ManyToOne
    @JoinColumn(name = "idJornadaLaboral", nullable = false)
    private JornadaLaboral idJornadaLaboral;


}