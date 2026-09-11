package com.victor.controle_gastos_port.gasto.service.editar_gasto;

import com.victor.controle_gastos_port.categoria.model.Categoria;
import com.victor.controle_gastos_port.categoria.repository.CategoriaRepository;
import com.victor.controle_gastos_port.config.IUsuarioAutenticadoProvider;
import com.victor.controle_gastos_port.config.exception.CategoriaNaoExistenteException;
import com.victor.controle_gastos_port.config.exception.GastoNaoEncontradoException;
import com.victor.controle_gastos_port.gasto.dto.editar_gasto_request.EditarGastoRequest;
import com.victor.controle_gastos_port.gasto.dto.editar_gasto_response.EditarGastoResponse;
import com.victor.controle_gastos_port.gasto.repository.GastoRepository;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import com.victor.controle_gastos_port.config.exception.AcessoNegadoException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class EditarGastoService {
    private final GastoRepository gastoRepository;
    private final CategoriaRepository categoriaRepository;
    private final IUsuarioAutenticadoProvider usuarioProvider;

    public EditarGastoService(GastoRepository gastoRepository, CategoriaRepository categoriaRepository, IUsuarioAutenticadoProvider usuarioProvider) {
        this.gastoRepository = gastoRepository;
        this.categoriaRepository = categoriaRepository;
        this.usuarioProvider = usuarioProvider;
    }

    @Transactional
    public EditarGastoResponse editarGasto(EditarGastoRequest dto, Long id) {
        Usuario usuarioAutenticado = usuarioProvider.getUsuarioLogado();

        var editarGasto = gastoRepository.findById(id).orElseThrow(() -> new GastoNaoEncontradoException(id));
        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() ->  new CategoriaNaoExistenteException("Categoria nao existente"));

        if(!editarGasto.getUsuario().equals(usuarioAutenticado)){
            throw new AcessoNegadoException("Acesso negado");
        }

        editarGasto.setValor(dto.valor());
        editarGasto.setData(dto.data());
        editarGasto.setDescricao(dto.descricao());
        editarGasto.setTipo(dto.tipo());
        editarGasto.setDataVencimento(dto.dataVencimento());
        editarGasto.setCategoria(categoria);
        editarGasto.setUsuario(usuarioAutenticado);


        return new EditarGastoResponse(editarGasto.getId(),
                editarGasto.getValor(),
                editarGasto.getData(),
                editarGasto.getDescricao(),
                editarGasto.getTipo(),
                editarGasto.getDataVencimento(),
                editarGasto.getCategoria().getId());

    }
}
