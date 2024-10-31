package com.gestion.empresa.backend.gestion_empresa.repositories;

import com.gestion.empresa.backend.gestion_empresa.models.PermisoRol;
import com.gestion.empresa.backend.gestion_empresa.models.ServicioPorUsuarios;
import com.gestion.empresa.backend.gestion_empresa.models.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicioPorUsuariosRepository  extends JpaRepository<ServicioPorUsuarios, Long> {


    List<ServicioPorUsuarios> findAllByIdUsuario(Usuarios idUsuarios);

}
