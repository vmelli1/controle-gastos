package com.victor.controle_gastos_port.categoria.service;


import com.victor.controle_gastos_port.categoria.dto.CategoriaResponse;
import com.victor.controle_gastos_port.categoria.dto.ListaCategoriaResponse;
import com.victor.controle_gastos_port.categoria.repository.CategoriaRepository;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;


@Service
public class ListaCategoriaService {
    private final CategoriaRepository categoriaRepository;

    public ListaCategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public ListaCategoriaResponse listarCategorias(int pagina) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null  ||  !authentication.isAuthenticated()) {
            throw  new RuntimeException("Acesso negado");
        }

        Usuario usuarioAutenticado = (Usuario) authentication.getPrincipal();

        var paginaCategorias = categoriaRepository.findByUsuario(usuarioAutenticado,PageRequest.of(pagina, 10));
        return new ListaCategoriaResponse(paginaCategorias.getContent()
                .stream().map(categoria -> new CategoriaResponse(categoria.getId(),categoria.getNome(),categoria.isAtivo()) ).toList()
                ,paginaCategorias.getNumber(), paginaCategorias.getTotalPages(),  paginaCategorias.getTotalElements());
    }
}
