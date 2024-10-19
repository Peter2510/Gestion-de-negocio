package com.gestion.empresa.backend.gestion_empresa.repositories;

import com.gestion.empresa.backend.gestion_empresa.models.Permiso;
import com.gestion.empresa.backend.gestion_empresa.models.ServicioPrestado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicioPrestadoRepository  extends JpaRepository<ServicioPrestado, Long> {
}
