package com.victor.controle_gastos_port.categoria.dto;

import com.victor.controle_gastos_port.categoria.model.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public record ListaCategoriaResponse(
        List<CategoriaResponse>  categoriaResponses,
        int paginaAtual,
        int totalPaginas,
        long totalItens

) {


}
