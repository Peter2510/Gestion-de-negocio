package com.gestion.empresa.backend.gestion_empresa.servicesImpl;


/*
    Author: peterg
    Created on: 21/10/24
*/

import com.gestion.empresa.backend.gestion_empresa.models.Notificacion;
import com.gestion.empresa.backend.gestion_empresa.repositories.NotificacionRepository;
import com.gestion.empresa.backend.gestion_empresa.services.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificacionServiceImpl implements NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Override
    public Notificacion crearNotificacion(Notificacion notificacion) {
        return notificacionRepository.save(notificacion);
    }

    @Override
    public Notificacion actualizarNotificacion(Notificacion notificacion) {
        return notificacionRepository.save(notificacion);
    }

    @Override
    public Optional<Notificacion> buscarNotificacionPorId(Long id) {
        return notificacionRepository.findById(id);
    }

}
