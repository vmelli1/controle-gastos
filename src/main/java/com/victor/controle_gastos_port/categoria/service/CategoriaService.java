package com.victor.controle_gastos_port.categoria.service;

import com.victor.controle_gastos_port.categoria.dto.CategoriaRequest;
import com.victor.controle_gastos_port.categoria.dto.CategoriaResponse;
import com.victor.controle_gastos_port.categoria.model.Categoria;
import com.victor.controle_gastos_port.categoria.repository.CategoriaRepository;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService
{
    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public CategoriaResponse criarCategoria(CategoriaRequest dto){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Usuário não autenticado");
        }

        Usuario usuarioAutenticado = (Usuario) auth.getPrincipal();

        if(categoriaRepository.existsByNomeAndUsuario(dto.nome(), usuarioAutenticado)){
            throw new RuntimeException("Categoria já existente");
        }

        Categoria categoria = new Categoria();
        categoria.setNome(dto.nome());
        categoria.setAtivo(true);
        categoria.setPadrao(false);
        categoria.setUsuario(usuarioAutenticado);


        var salvar = categoriaRepository.save(categoria);

        return  new CategoriaResponse(salvar.getId(),salvar.getNome(), salvar.isAtivo());

    }
}
