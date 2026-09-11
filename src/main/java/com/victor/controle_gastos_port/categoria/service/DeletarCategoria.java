package com.victor.controle_gastos_port.categoria.service;

import com.victor.controle_gastos_port.categoria.dto.DeletarCategoriaRequest;
import com.victor.controle_gastos_port.categoria.model.Categoria;
import com.victor.controle_gastos_port.categoria.repository.CategoriaRepository;
import com.victor.controle_gastos_port.config.IUsuarioAutenticadoProvider;
import com.victor.controle_gastos_port.config.exception.CategoriaNaoEncontradaException;
import com.victor.controle_gastos_port.config.exception.CategoriaPossuiGastosException;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class DeletarCategoria {

    private final CategoriaRepository categoriaRepository;
    private final IUsuarioAutenticadoProvider usuarioProvider;

    public DeletarCategoria(CategoriaRepository categoriaRepository, IUsuarioAutenticadoProvider usuarioProvider) {
        this.categoriaRepository = categoriaRepository;
        this.usuarioProvider = usuarioProvider;
    }

    @Transactional
    public void deletCategoria(DeletarCategoriaRequest dto) {
        Usuario usuarioAutenticado = usuarioProvider.getUsuarioLogado();

        Categoria categoria = categoriaRepository.findById(dto.id()).orElseThrow(() -> new CategoriaNaoEncontradaException(dto.id()));
        if(!categoria.getUsuario().equals(usuarioAutenticado)) {
            throw new CategoriaPossuiGastosException("Você não tem permissão para excluir uma categoria que não é sua.");
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