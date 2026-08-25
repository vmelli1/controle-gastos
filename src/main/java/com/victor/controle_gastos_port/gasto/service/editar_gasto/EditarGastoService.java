package com.victor.controle_gastos_port.gasto.service.editar_gasto;

import com.victor.controle_gastos_port.categoria.model.Categoria;
import com.victor.controle_gastos_port.categoria.repository.CategoriaRepository;
import com.victor.controle_gastos_port.gasto.dto.editar_gasto_request.EditarGastoRequest;
import com.victor.controle_gastos_port.gasto.dto.editar_gasto_response.EditarGastoResponse;
import com.victor.controle_gastos_port.gasto.repository.GastoRepository;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class EditarGastoService {
    private final GastoRepository gastoRepository;
    private final CategoriaRepository categoriaRepository;

    public EditarGastoService(GastoRepository gastoRepository, CategoriaRepository categoriaRepository) {
        this.gastoRepository = gastoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public EditarGastoResponse editarGasto(EditarGastoRequest dto, Long id) {
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();

        if(auth == null || !auth.isAuthenticated()) {
            throw new InsufficientAuthenticationException("Acesso negado");
        }

        Usuario usuario = (Usuario) auth.getPrincipal();

        var editarGasto = gastoRepository.findById(id).orElseThrow(() -> new RuntimeException("Gasto não encontrado"));
        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() ->  new RuntimeException("Categoria nao existente"));

        if(!editarGasto.getUsuario().equals(usuario)){
            throw new InsufficientAuthenticationException("Acesso negado");
        }

        editarGasto.setValor(dto.valor());
        editarGasto.setData(dto.data());
        editarGasto.setDescricao(dto.descricao());
        editarGasto.setTipo(dto.tipo());
        editarGasto.setDataVencimento(dto.dataVencimento());
        editarGasto.setCategoria(categoria);
        editarGasto.setUsuario(usuario);


        return new EditarGastoResponse(editarGasto.getId(),
                editarGasto.getValor(),
                editarGasto.getData(),
                editarGasto.getDescricao(),
                editarGasto.getTipo(),
                editarGasto.getDataVencimento(),
                editarGasto.getCategoria().getId());

    }
}
