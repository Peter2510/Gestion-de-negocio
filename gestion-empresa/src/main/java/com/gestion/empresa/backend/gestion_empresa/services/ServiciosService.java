package com.gestion.empresa.backend.gestion_empresa.services;


import com.gestion.empresa.backend.gestion_empresa.models.Permiso;
import com.gestion.empresa.backend.gestion_empresa.models.Servicios;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public interface ServiciosService {


    Servicios crearServicio(Servicios servicios);


    List<Servicios> obtenerTodosServicios();

}
