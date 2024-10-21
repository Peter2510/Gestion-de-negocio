package com.gestion.empresa.backend.gestion_empresa.services;


import com.gestion.empresa.backend.gestion_empresa.models.Notificacion;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
    Author: peterg
    Created on: 21/10/24
*/
@Service
public interface NotificacionService {
    Notificacion crearNotificacion(Notificacion notificacion);
    Notificacion actualizarNotificacion(Notificacion notificacion);
    Optional<Notificacion> buscarNotificacionPorId(Long id);
}
