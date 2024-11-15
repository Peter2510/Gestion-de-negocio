package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.dto.RegistroCitasDTO;
import com.gestion.empresa.backend.gestion_empresa.models.CategoriaServicio;
import com.gestion.empresa.backend.gestion_empresa.models.Citas;
import com.gestion.empresa.backend.gestion_empresa.models.EstadoCita;
import com.gestion.empresa.backend.gestion_empresa.models.ServicioPrestado;
import com.gestion.empresa.backend.gestion_empresa.repositories.UsuarioRepository;
import com.gestion.empresa.backend.gestion_empresa.services.CitasService;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.CategoriaServicioServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.CitasServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.DetalleCitaServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Author: alexxus
 * Created on: 24/10/24
 */

@RestController
@Validated
@RequestMapping("/citas")
public class CitasController {


    @Autowired
    private CitasServiceImpl citasServiceImpl;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private DetalleCitaServiceImpl detalleCitaServiceImpl;

    // creacion de categorias para usuarios que trabajen ahi
    @PostMapping("/crearCita")
    public ResponseEntity<Map<String, Object>> crearCategoria(
            @Validated @RequestBody RegistroCitasDTO ingresoCitas
    ) {
        //ingreso de elementos
        ResponseBackend respuesta = citasServiceImpl.registrarCitas(ingresoCitas);
        System.out.println("respuesta = " + respuesta);
        return ResponseEntity.status(respuesta.getStatus()).body(Map.of("ok", respuesta.getOk(), "mensaje", respuesta.getMensaje()));

    }
    // PARA LISTAR A TODOS
    @GetMapping("/obtener-Citas")
    public ResponseEntity<Map<String, Object>> obtenerEstadosCitas() {
        List<Citas> todasCitas = this.citasServiceImpl.obtenerTodasCitas();
        //ENVIAR UNA NUEVA RESPUESTA
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("ok", true, "Citas" , todasCitas));
    }


    @GetMapping("/obtenerTodasCitasEspecificos/{id}")
    public ResponseEntity<Map<String, Object>> obtenerCitasEspecificas(@PathVariable Long id) {
        List<Citas> response = citasServiceImpl.obtenerCitasId(id);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("ok", true, "todoServiciosEspecificos", response));
    }


    @GetMapping("/obtenerDetalleCitasEspecificos/{idCita}")
    public ResponseEntity<Map<String, Object>> obtenerDuracionServiciosEspecificos(@PathVariable Long idCita) {
        Object response = detalleCitaServiceImpl.obtenerDetalleCitasId(idCita);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("ok", true, "detalle", response));
    }





}
