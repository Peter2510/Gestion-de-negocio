package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.dto.RegistroCitasDTO;
import com.gestion.empresa.backend.gestion_empresa.models.*;
import com.gestion.empresa.backend.gestion_empresa.repositories.*;
import com.gestion.empresa.backend.gestion_empresa.services.CitasService;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Author: alexxus
 * Created on: 24/10/24
 */
@Service

public class CitasServiceImpl implements CitasService {


    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private EstadoCitaRepository estadoCitaRepository;
    @Autowired
    private DiasLaboralesRepository diasLaboralesRepository;
    @Autowired
    private ServiciosRepository serviciosRepository;

    @Autowired
    private CitasRepository citasRepository;

    @Override
    public List<Citas> obtenerTodasCitas() {
        return (citasRepository.findAll());
    }

    //metodo para ingresar nuevas citas
    @Override
    public ResponseBackend ingresarCitas(Citas citas) {
        try {
            System.out.println(citas);
            return new ResponseBackend(true, HttpStatus.CREATED, "Servicio registrado exitosamente");

        } catch (Exception e) {
            e.printStackTrace();
            // En caso de error, la transacción se revertirá automáticamente
            return new ResponseBackend(false, HttpStatus.INTERNAL_SERVER_ERROR, "Error al registrar servicio: " + e.getMessage());
        }
    }

    //funcion para registrar
    public ResponseBackend registrarCitas(RegistroCitasDTO registroCitasDTO) {


        try {
            //crear la estado
            Citas nuevaCitas = new Citas();

            Optional<Usuarios> optionalUsuario = usuarioRepository.findById(registroCitasDTO.getIdUsuario());
            if (optionalUsuario.isEmpty()) {
                return new ResponseBackend(false, HttpStatus.INTERNAL_SERVER_ERROR, "El usuario no se encuentra registrado");
            }
            nuevaCitas.setIdUsuario(optionalUsuario.get());

            nuevaCitas.setFechaHoraInicio(registroCitasDTO.getHoraInicio());
            nuevaCitas.setFechaHoraFin(registroCitasDTO.getHoraFin());
            nuevaCitas.setIdUsuario(usuarioRepository.findById(registroCitasDTO.getIdUsuario())
                    .orElseThrow(() -> new RuntimeException("El usuario no se encuentra registrado")));
            nuevaCitas.setIdEstadoCita(estadoCitaRepository.findById(registroCitasDTO.getIdEstadoCita())
                    .orElseThrow(() -> new RuntimeException("El estado no se encuentra registrado")));
            nuevaCitas.setIdDiaLaboral(diasLaboralesRepository.findById(registroCitasDTO.getIdDiaLaboral())
                    .orElseThrow(() -> new RuntimeException("El dia no se encuentra registrado")));
            nuevaCitas.setIdServicio(serviciosRepository.findById(registroCitasDTO.getIdServicio())
                    .orElseThrow(() -> new RuntimeException("El servicio no se encuentra registrado")));

            Citas ingresoCitas = citasRepository.save(nuevaCitas);
            return new ResponseBackend(true, HttpStatus.CREATED, "CITA registrada correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBackend(false, HttpStatus.INTERNAL_SERVER_ERROR, "Error al registrar la cita: " + e.getMessage());
        }

    }
}
