package com.gestion.empresa.backend.gestion_empresa.repositories;

import com.gestion.empresa.backend.gestion_empresa.models.Rol;
import com.gestion.empresa.backend.gestion_empresa.models.Usuarios;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {

    Optional<Usuarios> findById(Long aLong);
    Optional<Usuarios> findByPersonaCui(Long cui);
    Optional<Usuarios> findByNombreUsuario(String nombreUsuario);
    List<Usuarios> findByRol(Rol idRol);
    List<Usuarios> findByRolIdNot(Long rolId);
    @Query("SELECT u.rol.nombre, COUNT(u) FROM Usuarios u GROUP BY u.rol.nombre")
    List<Object[]> countUsuariosByRol();
}
