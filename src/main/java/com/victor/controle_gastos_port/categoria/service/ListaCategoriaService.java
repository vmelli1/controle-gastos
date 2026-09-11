package com.victor.controle_gastos_port.categoria.service;


import com.victor.controle_gastos_port.categoria.dto.CategoriaResponse;
import com.victor.controle_gastos_port.categoria.dto.ListaCategoriaResponse;
import com.victor.controle_gastos_port.categoria.repository.CategoriaRepository;
import com.victor.controle_gastos_port.config.IUsuarioAutenticadoProvider;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
public class ListaCategoriaService {
    private final CategoriaRepository categoriaRepository;
    private final IUsuarioAutenticadoProvider usuarioProvider;

    public ListaCategoriaService(CategoriaRepository categoriaRepository, IUsuarioAutenticadoProvider usuarioProvider) {
        this.categoriaRepository = categoriaRepository;
        this.usuarioProvider = usuarioProvider;
    }

    public ListaCategoriaResponse listarCategorias(int pagina) {
        Usuario usuarioAutenticado = usuarioProvider.getUsuarioLogado();

        var paginaCategorias = categoriaRepository.findByUsuario(usuarioAutenticado,PageRequest.of(pagina, 10));
        return new ListaCategoriaResponse(paginaCategorias.getContent()
                .stream().map(categoria -> new CategoriaResponse(categoria.getId(),categoria.getNome(),categoria.isAtivo()) ).toList()
                ,paginaCategorias.getNumber(), paginaCategorias.getTotalPages(),  paginaCategorias.getTotalElements());
    }
}
