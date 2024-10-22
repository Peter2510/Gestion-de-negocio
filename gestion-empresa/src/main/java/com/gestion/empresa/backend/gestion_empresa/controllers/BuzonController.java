package com.gestion.empresa.backend.gestion_empresa.controllers;


import com.gestion.empresa.backend.gestion_empresa.models.Buzon;
import com.gestion.empresa.backend.gestion_empresa.models.Notificacion;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.BuzonServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.NotificacionServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/*
    Author: peterg
    Created on: 21/10/24
*/
@RestController
@RequestMapping("/buzon")
public class BuzonController {

    @Autowired
    private BuzonServiceImpl buzonService;

    @Autowired
    private NotificacionServiceImpl notificacionService;

    @GetMapping("/obtener-buzon-usuario/{idUsuario}")
    public ResponseEntity<Map<String, Object>> obtenerBuzonUsuario(@PathVariable Long idUsuario) {

        List<Notificacion> notificaciones = buzonService.buscarBuzonesPorUsuario(idUsuario);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("ok", true, "notificaciones", notificaciones));
    }

    @PutMapping("/actualizar-notificacion/{idNotificacion}")
    public ResponseEntity<Map<String, Object>> actualizarMensaje(@PathVariable Long idNotificacion) {

        Optional<Notificacion> notificacion = notificacionService.buscarNotificacionPorId(idNotificacion);

        if(notificacion.isPresent()){
            notificacion.get().setLeido(true);
            notificacionService.actualizarNotificacion(notificacion.get());
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("ok", true, "mensaje", "Notificación actualizada correctamente"));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("ok", false, "mensaje", "Notificación no encontrada"));

    }

}
