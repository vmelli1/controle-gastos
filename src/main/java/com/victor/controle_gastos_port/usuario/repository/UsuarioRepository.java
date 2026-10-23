package com.victor.controle_gastos_port.usuario.repository;

import com.victor.controle_gastos_port.usuario.model.Usuario;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.Optional;


@RepositoryDefinition(domainClass = Usuario.class, idClass = long.class)
public interface UsuarioRepository  {


    boolean existsByEmail(String email);
    Optional<Usuario> findByEmailIgnoreCase(String email);

    <S extends Usuario> S save(S entity);
    <D extends Usuario> void delete(D entity);

    Optional<Usuario> existsById(Long id);
}
