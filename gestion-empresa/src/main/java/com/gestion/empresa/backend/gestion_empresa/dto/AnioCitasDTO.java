package com.gestion.empresa.backend.gestion_empresa.dto;


/**
 * Author: gordillox
 * Created on: 29/10/24
 */

public class AnioCitasDTO {
    private int anio;
    private long total;

    // Constructor que acepta los valores esperados
    public AnioCitasDTO(int anio, long total) {
        this.anio = anio;
        this.total = total;
    }

    // Getters y Setters
    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}

