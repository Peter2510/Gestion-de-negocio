package com.gestion.empresa.backend.gestion_empresa.controllers;


import com.gestion.empresa.backend.gestion_empresa.models.Empresa;
import com.gestion.empresa.backend.gestion_empresa.models.TipoAsignacionCita;
import com.gestion.empresa.backend.gestion_empresa.models.TipoServicio;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.EmpresaServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.S3ServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.TipoAsignacionCitaServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.TipoServicioServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.utils.GenerarNombreArchivo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Optional;

/**
 * Author: gordillox
 * Created on: 5/10/24
 */
@RestController
@Validated
@RequestMapping("/empresa")
public class EmpresaController {

    @Autowired
    private EmpresaServiceImpl empresaService;

    @Autowired
    private TipoServicioServiceImpl tipoServicioService;

    @Autowired
    private TipoAsignacionCitaServiceImpl tipoAsignacionCitaService;

    @Autowired
    private S3ServiceImpl s3Service;

    private final GenerarNombreArchivo generarNombreArchivo = new GenerarNombreArchivo();

    @PostMapping("/crearEmpresa")
    public ResponseEntity<Map<String, Object>> crearEmpresa(
            @RequestParam("nombre") String nombre,
            @RequestParam("direccion") String direccion,
            @RequestParam("telefono") String telefono,
            @RequestParam("email") String email,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("idTipoServicio") Long idTipoServicio,
            @RequestParam("idTipoAsignacionCita") Long idTipoAsignacionCita,
            @RequestPart("logo") MultipartFile logoFile  // Usamos @RequestPart para el archivo
    ) {

        Empresa empresa = new Empresa();

        if (!logoFile.isEmpty()) {
            String nombreArchivo = generarNombreArchivo.generarNombreUnico(logoFile);
            String respuesta = s3Service.uploadFile(logoFile,nombreArchivo);
            if(respuesta == null){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("ok", false, "mensaje", "Error al subir el archivo"));
            }
            empresa.setLogo(nombreArchivo);
        }else{
            empresa.setLogo("logo_defecto.png");
        }

        empresa.setNombre(nombre);
        empresa.setDireccion(direccion);
        empresa.setTelefono(telefono);
        empresa.setEmail(email);
        empresa.setDescripcion(descripcion);
        empresa.setCantidadServicios(0);
        empresa.setCantidadEmpleados(0);

        Optional<TipoServicio> tipoServicio = tipoServicioService.buscarPorId(idTipoServicio);
        if(tipoServicio.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("ok", false, "mensaje", "El tipo de de servicio no esta registrado"));
        }

        Optional<TipoAsignacionCita> tipoAsignacionCita = tipoAsignacionCitaService.buscarPorId(idTipoAsignacionCita);

        if(tipoAsignacionCita.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("ok", false, "mensaje", "El tipo de asignacion de la cita  no esta registrado"));
        }

        empresa.setTipoServicio(tipoServicio.get());
        empresa.setTipoAsignacionCita(tipoAsignacionCita.get());
        Empresa guardarEmpresa = empresaService.crearEmpresa(empresa);

        System.out.println("logo "+s3Service.createPresignedGetUrl(empresa.getLogo()));

        if(guardarEmpresa == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("ok", false, "mensaje", "Error al guardar la empresa"));
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("ok", true, "mensaje", "Empresa creada correctamente"));
    }

    @GetMapping("/obtenerEmpresa/{id}")
    public ResponseEntity<Map<String, Object>> obtenerEmpresa(@PathVariable Long id) {
        Optional<Empresa> empresa = empresaService.findById(id);
        if(empresa.isEmpty()){

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("ok", false, "mensaje", "La empresa no esta registrada"));
        }

        empresa.get().setLogo(s3Service.createPresignedGetUrl(empresa.get().getLogo()));

        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("ok", true, "empresa", empresa.get()));
    }

}
