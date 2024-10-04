package com.gestion.empresa.backend.gestion_empresa.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Empresa implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la empresa es obligatorio")
    @Column(name="nombre", nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "La dirección de la empresa es obligatoria")
    @Column(name="direccion", nullable = false, length = 150)
    private String direccion;

    @NotBlank(message = "El teléfono de la empresa es obligatorio")
    @Column(name="telefono", nullable = false, length = 10)
    private String telefono;

    @Email(message = "El email debe ser válido")
    @Column(name="email", nullable = true, length = 100)
    private String email;

    @NotBlank(message = "El logo es obligatorio")
    @Column(name="logo", nullable = false)
    private String logo;

    @NotBlank(message = "La descripción de la empresa es obligatoria")
    @Column(name="descripcion", nullable = false, length = 250)
    private String descripcion;

    @Min(value = 1, message = "La cantidad de servicios debe ser al menos 1")
    @Column(name="cantidadServicios", nullable = false)
    private int cantidadServicios;

    @Min(value = 1, message = "La cantidad de empleados debe ser al menos 1")
    @Column(name="cantidadEmpleados", nullable = false)
    private int cantidadEmpleados;

    @NotNull(message = "El tipo de servicio es obligatorio")
    @OneToOne
    @JoinColumn(name = "idTipoServicio", nullable = false)
    private TipoServicio tipoServicio;

    @NotNull(message = "El tipo de asignación de citas es obligatorio")
    @OneToOne
    @JoinColumn(name = "idTipoAsignacionCita", nullable = false)
    private TipoAsignacionCita tipoAsignacionCita;
}
