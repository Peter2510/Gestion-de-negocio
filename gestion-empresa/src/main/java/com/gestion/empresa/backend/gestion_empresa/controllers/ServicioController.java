package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.dto.DevolverTodoServiciosDTO;
import com.gestion.empresa.backend.gestion_empresa.dto.NuevoServicioDTO;
import com.gestion.empresa.backend.gestion_empresa.models.JornadaPorDia;
import com.gestion.empresa.backend.gestion_empresa.models.Servicios;
import com.gestion.empresa.backend.gestion_empresa.services.DiasLaboralesService;
import com.gestion.empresa.backend.gestion_empresa.services.JornadaLaboralService;
import com.gestion.empresa.backend.gestion_empresa.services.JornadaPorDiaService;
import com.gestion.empresa.backend.gestion_empresa.services.ServiciosService;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.ServiciosServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Author: alexxus
 * Created on: 13/10/24
 */

@RestController
@Validated
@RequestMapping("/servicios")
public class ServicioController {

    @Autowired
    private ServiciosService serviciosService;

    @Autowired
    private ServiciosServiceImpl serviciosServiceImpl;


    @Autowired
    private JornadaLaboralService jornadaLaboralService;

    @Autowired
    private DiasLaboralesService diasLaboralesService;


    // creacion de categorias para usuarios que trabajen ahi
    @PostMapping("/creacionServicio")
    public ResponseEntity<Map<String, Object>> creacionServicio(
            @RequestBody Servicios servicios
    ) {
        try {
            System.out.println(servicios);
            //crea la categoria
            Servicios nuevoServicio = new Servicios();
            nuevoServicio.setNombre(servicios.getNombre());
            nuevoServicio.setDescripcion(servicios.getDescripcion());
            nuevoServicio.setIdEstadoServicio(servicios.getIdEstadoServicio());
            nuevoServicio.setIdTipoServicio(servicios.getIdTipoServicio());

            Servicios respuesta = this.serviciosService.crearServicio(nuevoServicio);

            if(respuesta!= null) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(Map.of("ok", true, "mensaje", "jornada laboral creada correctamente"));
            }
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("ok", false, "mensaje", "error en su creacions"));

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("ok", false, "mensaje", "Error al guardar la jornada laboral", "error", e.getMessage()));
        }

    }


    // para hacer la idea de generar todda la transaccion
    // Crear o actualizar un servicio con jornadas laborales y días laborales
    @PostMapping("/creacionNuevosServicios")
    public ResponseEntity<String> createOrUpdateServicio(
            @RequestBody NuevoServicioDTO servicioRequest) {
        System.out.println(servicioRequest);
            ResponseBackend response = serviciosServiceImpl.registroServicio(servicioRequest);
            return ResponseEntity.status(response.getStatus()).body(Map.of("ok", response.getOk(), "mensaje", response.getMensaje()).toString());


    }


    // para obtener todos los servicios con la info desglozada

    @GetMapping("/obtenerServiciosGenerales")
    public ResponseEntity<Map<String, Object>> obtenerServicios() {

        DevolverTodoServiciosDTO response = serviciosServiceImpl.obtenerServicios();
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("ok", true, "todoServicios" , response));



    }
}
