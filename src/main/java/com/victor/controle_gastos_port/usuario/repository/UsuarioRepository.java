package com.victor.controle_gastos_port.usuario.repository;

import com.victor.controle_gastos_port.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.security.core.userdetails.UserDetails;



@RepositoryDefinition(domainClass = Usuario.class, idClass = long.class)
public interface UsuarioRepository  {
    Usuario existsById(Usuario usuario);

    boolean existsByEmail(String email);
    UserDetails findByEmail(String email);

    <S extends Usuario> S save(S entity);
}
