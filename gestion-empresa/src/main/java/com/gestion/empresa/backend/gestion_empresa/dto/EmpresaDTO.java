package com.gestion.empresa.backend.gestion_empresa.dto;


/*
    Author: peterg
    Created on: 7/10/24
*/

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpresaDTO {
    @NotBlank(message = "El nombre es obligatorio.")
    private String nombre;

    @NotBlank(message = "La dirección es obligatoria.")
    private String direccion;

    @NotBlank(message = "El teléfono es obligatorio.")
    private String telefono;

    @Email(message = "El email debe ser válido.")
    @NotBlank(message = "El email es obligatorio.")
    private String email;

    @NotBlank(message = "La descripción es obligatoria.")
    private String descripcion;

    private Long idTipoServicio;
    private Long idTipoAsignacionCita;
    private MultipartFile logoFile;

}