package com.gestion.empresa.backend.gestion_empresa.servicesImpl;


import com.gestion.empresa.backend.gestion_empresa.models.Buzon;
import com.gestion.empresa.backend.gestion_empresa.repositories.BuzonRepository;
import com.gestion.empresa.backend.gestion_empresa.services.BuzonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
    Author: peterg
    Created on: 21/10/24
*/
@Service
public class BuzonServiceImpl implements BuzonService {

    @Autowired
    private BuzonRepository buzonRepository;

    @Override
    public Buzon crearBuzon(Buzon buzon) {
        return buzonRepository.save(buzon);
    }

    @Override
    public Buzon actualizarBuzon(Buzon buzon) {
        return buzonRepository.save(buzon);
    }

    @Override
    public Optional<Buzon> buscarBuzonPorId(Long id) {
        return buzonRepository.findById(id);
    }

    @Override
    public List<Buzon> buscarBuzonesPorUsuario(Long idUsuario) {
        return buzonRepository.findByUsuario_Id(idUsuario);
    }
}
