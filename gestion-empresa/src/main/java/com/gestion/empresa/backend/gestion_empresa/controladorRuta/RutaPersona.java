package com.gestion.empresa.backend.gestion_empresa.controladorRuta;


import com.gestion.empresa.backend.gestion_empresa.models.Persona;
import com.gestion.empresa.backend.gestion_empresa.servicio.PersonaServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Persona")
public class RutaPersona {
    private final PersonaServicio servicio;

    public RutaPersona(PersonaServicio servicio) {
        this.servicio = servicio;
    }


    // rutas
    // PARA LISTAR A TODOS
    @GetMapping("/obtenerTodasPersonas")
    public ResponseEntity<List<Persona>> obtenerPersonas() {
     List<Persona>   todasPersonas  = servicio.listarPersonas();
    //ENVIAR UNA NUEVA RESPUESTA
     return new ResponseEntity<>(todasPersonas, HttpStatus.OK);
    }



    // para crear nuevas personas

    @PostMapping("/ingresarPersona")
    public ResponseEntity<Persona> ingresarPersona(@RequestBody  Persona persona) {
        Persona nuevaPersona = servicio.agregarPersonas(persona);
        return  new ResponseEntity<>(nuevaPersona, HttpStatus.CREATED);
    }
}
