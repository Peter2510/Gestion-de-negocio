package com.gestion.empresa.backend.gestion_empresa.controllers;

import com.gestion.empresa.backend.gestion_empresa.dto.ActualizarContraseniaDTO;
import com.gestion.empresa.backend.gestion_empresa.dto.RecuperarContraseniaDTO;
import com.gestion.empresa.backend.gestion_empresa.models.CorreoConfirmacion;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.CacheServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.CorreoMensajeServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.servicesImpl.UsuarioServiceImpl;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Author: alexxus
 * Created on: 8/10/24
 */

@RestController
@Validated
@RequestMapping("/correo")
public class CorreoController {

    @Autowired
    private CorreoMensajeServiceImpl emailService;

    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;

    @Autowired
    private CacheServiceImpl cacheService;


    //ruta para enviar un correo
    @PostMapping("/send-email")
    public String sendEmail(@RequestBody CorreoConfirmacion request) {
        ResponseBackend response = emailService.generarCorreo("wexaxin475@aleitar.com", "Prueba", "hola desde backend prueba");
        return response.getMensaje();
    }

    @PostMapping("/recuperar-contrasenia")
    public ResponseEntity<Map<String, Object>> recuperarContrasenia(@RequestBody RecuperarContraseniaDTO recuperarContraseniaDTO){

        ResponseBackend response = usuarioServiceImpl.recuperarContrasenia(recuperarContraseniaDTO.getCorreo());

        return ResponseEntity.status(response.getStatus()).body(
                Map.of("ok", response.getOk(), "mensaje", response.getMensaje())
        );
    }

    @PostMapping("/validar-codigo-recuperacion-contrasenia")
    public ResponseEntity<Map<String, Object>> validarCodigo(@RequestBody RecuperarContraseniaDTO recuperarContraseniaDTO){

        ResponseBackend response = usuarioServiceImpl.validarCodigoRecuperacion(recuperarContraseniaDTO.getCorreo(), recuperarContraseniaDTO.getCodigo());

        return ResponseEntity.status(response.getStatus()).body(
                response.getOk()
                ? Map.of("ok", true, "mensaje", response.getMensaje(), "idUsuario", response.getData())
                : Map.of("ok", false , "mensaje", response.getMensaje())
        );

    }

    @PostMapping("/cambio-contrasenia")
    public ResponseEntity<Map<String, Object>> cambioContrasenia(@RequestBody ActualizarContraseniaDTO cambioContrasenia){

        ResponseBackend response = usuarioServiceImpl.cambioContrasenia(cambioContrasenia);

        return ResponseEntity.status(response.getStatus()).body(
                response.getOk()
                        ? Map.of("ok", true, "mensaje", response.getMensaje())
                        : Map.of("ok", false , "mensaje", response.getMensaje())
        );

    }

}
