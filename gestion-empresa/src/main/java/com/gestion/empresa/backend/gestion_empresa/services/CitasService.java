package com.gestion.empresa.backend.gestion_empresa.services;

import com.gestion.empresa.backend.gestion_empresa.models.Citas;
import com.gestion.empresa.backend.gestion_empresa.models.DiasLaborales;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CitasService {

    List<Citas> obtenerTodasCitas();
}
