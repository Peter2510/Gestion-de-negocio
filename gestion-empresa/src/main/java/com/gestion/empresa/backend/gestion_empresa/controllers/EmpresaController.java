package com.gestion.empresa.backend.gestion_empresa.controllers;


import com.gestion.empresa.backend.gestion_empresa.dto.EmpresaDTO;
import com.gestion.empresa.backend.gestion_empresa.models.Empresa;
import com.gestion.empresa.backend.gestion_empresa.models.TipoAsignacionCita;
import com.gestion.empresa.backend.gestion_empresa.models.TipoServicio;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.EmpresaServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.S3ServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.TipoAsignacionCitaServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.TipoServicioServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.utils.GenerarNombreArchivo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
            @RequestPart("logo") MultipartFile logoFile
    ) {

        try {
            Empresa empresa = new Empresa();

            empresa.setNombre(nombre);
            empresa.setDireccion(direccion);
            empresa.setTelefono(telefono);
            empresa.setEmail(email);
            empresa.setDescripcion(descripcion);
            empresa.setCantidadServicios(0);
            empresa.setCantidadEmpleados(0);

            Optional<TipoServicio> busquedaTipoServicio = obtenerTipoServicio(idTipoServicio);

            if (busquedaTipoServicio.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("ok", false, "mensaje", "El tipo de servicio no esta registrado"));

            Optional<TipoAsignacionCita> busquedaTipoAsignacionServicio = obtenerTipoAsignacionCita(idTipoAsignacionCita);

            if (busquedaTipoAsignacionServicio.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("ok", false, "mensaje", "El tipo de asignacion de la cita  no esta registrado"));

            empresa.setTipoServicio(busquedaTipoServicio.get());
            empresa.setTipoAsignacionCita(busquedaTipoAsignacionServicio.get());


            if (!logoFile.isEmpty()) {
                String nombreArchivo = generarNombreArchivo.generarNombreUnico(logoFile);
                String respuesta = s3Service.uploadFile(logoFile, nombreArchivo);
                if (respuesta == null) {
                    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                            .body(Map.of("ok", false, "mensaje", "Error al subir el logo"));
                }
                empresa.setLogo(nombreArchivo);
            } else {
                empresa.setLogo("logo_defecto.png");
            }

            empresaService.crearEmpresa(empresa);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("ok", true, "mensaje", "Empresa creada correctamente"));

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("ok", false, "mensaje", "Error al guardar la empresa", "error", e.getMessage()));
        }

    }

    @GetMapping("/obtenerEmpresa/{id}")
    public ResponseEntity<Map<String, Object>> obtenerEmpresa(@PathVariable Long id) {
        Optional<Empresa> empresa = empresaService.findById(id);
        if (empresa.isEmpty()) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("ok", false, "mensaje", "La empresa no esta registrada"));
        }

        empresa.get().setLogo(s3Service.createPresignedGetUrl(empresa.get().getLogo()));

        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("ok", true, "empresa", empresa.get()));
    }

    @PostMapping("/actualizarEmpresa/{id}")
    public ResponseEntity<Map<String, Object>> actualizarEmpresa(
            @PathVariable Long id,
            @Valid @ModelAttribute EmpresaDTO empresaDTO) {


        try{
            Optional<Empresa> empresaBusqueda = empresaService.findById(id);
            if (empresaBusqueda.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("ok", false, "mensaje", "La empresa no está registrada."));
            }

            Optional<TipoServicio> busquedaTipoServicio = obtenerTipoServicio(empresaDTO.getIdTipoServicio());
            if (busquedaTipoServicio.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("ok", false, "mensaje", "El tipo de servicio no está registrado."));
            }

            Optional<TipoAsignacionCita> busquedaTipoAsignacionServicio = obtenerTipoAsignacionCita(empresaDTO.getIdTipoAsignacionCita());
            if (busquedaTipoAsignacionServicio.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("ok", false, "mensaje", "El tipo de asignación de la cita no está registrado."));
            }

            Empresa nuevosDatosEmpresa = empresaBusqueda.get();
            nuevosDatosEmpresa.setNombre(empresaDTO.getNombre());
            nuevosDatosEmpresa.setDireccion(empresaDTO.getDireccion());
            nuevosDatosEmpresa.setTelefono(empresaDTO.getTelefono());
            nuevosDatosEmpresa.setEmail(empresaDTO.getEmail());
            nuevosDatosEmpresa.setDescripcion(empresaDTO.getDescripcion());

            //subida del logo
            if (empresaDTO.getLogoFile() != null && !empresaDTO.getLogoFile().isEmpty()) {
                String nombreArchivo = generarNombreArchivo.generarNombreUnico(empresaDTO.getLogoFile());
                String respuesta = s3Service.uploadFile(empresaDTO.getLogoFile(), nombreArchivo);
                if (respuesta == null) {
                    throw new RuntimeException("Error al subir el logo");
                }
                nuevosDatosEmpresa.setLogo(nombreArchivo);
            }

            empresaService.actualizarEmpresa(nuevosDatosEmpresa);

            return ResponseEntity.ok(Map.of("ok", true, "mensaje", "Datos actualizados correctamente."));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(Map.of("ok", false, "mensaje", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("ok", false, "mensaje", "Error al actualizar los datos.", "error", e.getMessage()));
        }
    }

    private Optional<TipoServicio> obtenerTipoServicio(Long idTipoServicio) {
        return tipoServicioService.buscarPorId(idTipoServicio);
    }

    private Optional<TipoAsignacionCita> obtenerTipoAsignacionCita(Long idTipoAsignacionCita) {
        return tipoAsignacionCitaService.buscarPorId(idTipoAsignacionCita);
    }

}
