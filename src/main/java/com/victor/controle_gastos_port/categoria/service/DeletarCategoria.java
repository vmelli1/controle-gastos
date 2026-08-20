package com.victor.controle_gastos_port.categoria.service;

import com.victor.controle_gastos_port.categoria.dto.DeletarCategoriaRequest;
import com.victor.controle_gastos_port.categoria.model.Categoria;
import com.victor.controle_gastos_port.categoria.repository.CategoriaRepository;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class DeletarCategoria {

    private final CategoriaRepository categoriaRepository;

    public DeletarCategoria(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public void deletCategoria(DeletarCategoriaRequest dto) {


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth == null || !auth.isAuthenticated() ) {
            throw new RuntimeException("Acesso negado");
        }


        Usuario usuarioAutenticado = (Usuario) auth.getPrincipal();

        Categoria categoria = categoriaRepository.findById(dto.id()).orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        if(!categoria.getUsuario().equals(usuarioAutenticado)) {
            throw new RuntimeException("Excluisao negada ");
        }
        categoriaRepository.delete(categoria);




    }
}
// primeira tentativa = return nao esta indo com response
//  if (categoriaRepository.existsById(dto.id())){
//            throw new RuntimeException("Categoria com o id inexistente");
//        }

//problema validar se a categoria é do x usuario ou não e lancar uma execptio
// problema chamar antes da autenticacao ?
//