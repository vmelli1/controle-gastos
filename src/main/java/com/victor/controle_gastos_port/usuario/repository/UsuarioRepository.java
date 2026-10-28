package com.victor.controle_gastos_port.usuario.repository;

import com.victor.controle_gastos_port.usuario.model.StatusConta;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import org.springframework.data.repository.RepositoryDefinition;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@RepositoryDefinition(domainClass = Usuario.class, idClass = long.class)
public interface UsuarioRepository  {


    boolean existsByEmail(String email);
    Optional<Usuario> findByEmailIgnoreCase(String email);

    <S extends Usuario> S save(S entity);
    void deleteAll(Iterable<? extends Usuario> entities);


    Optional<Usuario> existsById(Long id);

    List<Usuario> findByStatusContaAndCarenciaExclusaoLessThanEqual(StatusConta status, LocalDate data);


    List<Usuario> findByStatusConta(StatusConta statusConta, LocalDate dataAtualizacao);

    Usuario findByNome(String apelido);
}
