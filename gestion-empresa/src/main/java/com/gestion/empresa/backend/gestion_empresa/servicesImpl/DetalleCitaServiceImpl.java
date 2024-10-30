package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.models.*;
import com.gestion.empresa.backend.gestion_empresa.repositories.*;
import com.gestion.empresa.backend.gestion_empresa.services.DetalleCitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: alexxus
 * Created on: 24/10/24
 */
@Service
public class DetalleCitaServiceImpl implements DetalleCitaService {


    @Autowired
    private DetalleCitaRepository detalleCitaRepository;

    @Autowired
    private CitasRepository citaRepository;

    @Autowired
    private DuracionServicioPrestadoRepository duracionServicioPrestadoRepository;

    public Object obtenerDetalleCitasId(Long id){
        Citas determinadaCita = this.citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El estado no se encuentra registrado"));

        DetalleCita detalleCita = this.detalleCitaRepository.findAllByIdCita(determinadaCita);


        DuracionServicioPrestado duracionServicioPrestado = this.duracionServicioPrestadoRepository.findAllByIdServicioPrestado(detalleCita.getIdServicioPrestado());
        List<Object> valores = new ArrayList<>();
        valores.add(detalleCita);
        valores.add(duracionServicioPrestado);
        return valores;


    }
}
