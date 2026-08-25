package com.victor.controle_gastos_port.categoria.repository;

import com.victor.controle_gastos_port.categoria.dto.ListaCategoriaResponse;
import com.victor.controle_gastos_port.categoria.model.Categoria;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import java.util.Optional;


public interface CategoriaRepository  extends JpaRepository<Categoria, Long> {

    boolean existsByNomeAndUsuario(String nome, Usuario usuarioAutenticado);


    Page<Categoria>  findByUsuario  ( Usuario usuario,   Pageable pageable);


    void deleteByUsuarioAndId( Usuario usuarioAutenticado, long id);
}
