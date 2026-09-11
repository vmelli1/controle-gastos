package com.victor.controle_gastos_port.categoria.service;

import com.victor.controle_gastos_port.categoria.dto.CategoriaRequest;
import com.victor.controle_gastos_port.categoria.dto.CategoriaResponse;
import com.victor.controle_gastos_port.categoria.model.Categoria;
import com.victor.controle_gastos_port.categoria.repository.CategoriaRepository;
import com.victor.controle_gastos_port.config.IUsuarioAutenticadoProvider;
import com.victor.controle_gastos_port.config.exception.CategoriaExistenteException;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService
{
    private final CategoriaRepository categoriaRepository;
    private final IUsuarioAutenticadoProvider usuarioProvider;

    public CategoriaService(CategoriaRepository categoriaRepository, IUsuarioAutenticadoProvider usuarioProvider) {
        this.categoriaRepository = categoriaRepository;
        this.usuarioProvider = usuarioProvider;
    }

    public CategoriaResponse criarCategoria(CategoriaRequest dto){
        Usuario usuarioAutenticado = usuarioProvider.getUsuarioLogado();

        if(categoriaRepository.existsByNomeAndUsuario(dto.nome(), usuarioAutenticado)){
            throw new CategoriaExistenteException("Categoria já existente");
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
