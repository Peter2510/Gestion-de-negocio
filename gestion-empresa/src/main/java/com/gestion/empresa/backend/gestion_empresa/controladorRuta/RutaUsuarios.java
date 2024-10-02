package com.gestion.empresa.backend.gestion_empresa.controladorRuta;


import com.gestion.empresa.backend.gestion_empresa.Dto.Login;
import com.gestion.empresa.backend.gestion_empresa.models.Usuarios;
import com.gestion.empresa.backend.gestion_empresa.seguridad.JwtServicio;
import com.gestion.empresa.backend.gestion_empresa.servicio.AutenticacionServicio;
import com.gestion.empresa.backend.gestion_empresa.validacion.RespuestaLogin;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class RutaUsuarios {
    private final JwtServicio jwtService;

    private final AutenticacionServicio authenticationService;

    public RutaUsuarios(JwtServicio jwtService, AutenticacionServicio authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }
    @PostMapping(value = "/vista")
    public ResponseEntity<Object> ingreso(){


        return ResponseEntity.ok("desde usuario");
    }


}
