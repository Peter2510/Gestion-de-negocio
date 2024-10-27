package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.dto.RegistroCitasDTO;
import com.gestion.empresa.backend.gestion_empresa.models.*;
import com.gestion.empresa.backend.gestion_empresa.repositories.*;
import com.gestion.empresa.backend.gestion_empresa.services.CitasService;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private DetalleCitaRepository detalleCitaRepository;
    @Autowired
    private ServicioPrestadoRepository servicioPrestadoRepository;

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
    @Transactional(rollbackOn = Throwable.class)
    public ResponseBackend registrarCitas(RegistroCitasDTO registroCitasDTO) {


        try{
            //crear la estado
            Citas nuevaCitas = new Citas();
            System.out.println(registroCitasDTO);
            nuevaCitas.setFechaHoraInicio(registroCitasDTO.getHoraInicio());
            nuevaCitas.setFechaHoraFin(registroCitasDTO.getHoraFin());
            nuevaCitas.setIdUsuario( usuarioRepository.findById(registroCitasDTO.getIdUsuario())
                    .orElseThrow(() -> new RuntimeException("El usuario no se encuentra registrado")));
            nuevaCitas.setIdEstadoCita( estadoCitaRepository.findById(1L)
                    .orElseThrow(() -> new RuntimeException("El estado no se encuentra registrado")));
            nuevaCitas.setIdDiaLaboral( diasLaboralesRepository.findById(2L)
                    .orElseThrow(() -> new RuntimeException("El dia no se encuentra registrado")));
            nuevaCitas.setIdServicio( serviciosRepository.findById(registroCitasDTO.getIdServicio())
                    .orElseThrow(() -> new RuntimeException("El servicio no se encuentra registrado")));

            Citas ingresoCitas = citasRepository.save(nuevaCitas);


            //luego de registrado entonces se hace el ciclo para las otras citas

            for (Long elementos : registroCitasDTO.getListadoServiciosEspecificos()) {
                DetalleCita detalleCita = new DetalleCita();
                detalleCita.setIdServicioPrestado( servicioPrestadoRepository.findById(elementos)
                        .orElseThrow(() -> new RuntimeException("El servicio no se encuentra registrado")));
                detalleCita.setIdCita(ingresoCitas);
                System.out.println(detalleCita);
                DetalleCita detalleCitaFinal = detalleCitaRepository.save(detalleCita);

            }

            return new ResponseBackend(true, HttpStatus.CREATED, "CITA registrada correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            // En caso de error, la transacción se revertirá automáticamente
            return new ResponseBackend(false, HttpStatus.INTERNAL_SERVER_ERROR, "Error al registrar la cita: " + e.getMessage());

        }


    }

    public List<Citas> obtenerCitasId(Long id){
        Usuarios determinadoUsuarios = this.usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El estado no se encuentra registrado"));

        List<Citas> todasCitas = this.citasRepository.findAllByIdUsuario(determinadoUsuarios);
        return todasCitas;
    }


}
