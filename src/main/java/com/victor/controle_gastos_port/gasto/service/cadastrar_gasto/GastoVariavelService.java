package com.victor.controle_gastos_port.gasto.service.cadastrar_gasto;

import com.victor.controle_gastos_port.categoria.model.Categoria;
import com.victor.controle_gastos_port.categoria.repository.CategoriaRepository;
import com.victor.controle_gastos_port.config.IUsuarioAutenticadoProvider;
import com.victor.controle_gastos_port.config.exception.CategoriaNaoExistenteException;
import com.victor.controle_gastos_port.gasto.dto.gasto_request.GastoVariavelRequest;
import com.victor.controle_gastos_port.gasto.dto.gasto_response.GastoVariavelResponse;
import com.victor.controle_gastos_port.gasto.model.Gasto;
import com.victor.controle_gastos_port.gasto.model.GastoTIpo;
import com.victor.controle_gastos_port.gasto.repository.GastoRepository;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import com.victor.controle_gastos_port.config.exception.AcessoNegadoException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class GastoVariavelService {
    private final GastoRepository gastoRepository;
    private final CategoriaRepository categoriaRepository;
    private final IUsuarioAutenticadoProvider usuarioProvider;

    public GastoVariavelService(GastoRepository gastoRepository, CategoriaRepository categoriaRepository, IUsuarioAutenticadoProvider usuarioProvider) {
        this.gastoRepository = gastoRepository;
        this.categoriaRepository = categoriaRepository;
        this.usuarioProvider = usuarioProvider;
    }

    @Transactional
    public GastoVariavelResponse cadastrarGastoVariavel(GastoVariavelRequest dto){
        Usuario usuarioAutenticado = usuarioProvider.getUsuarioLogado();

        Categoria categoria = categoriaRepository.findById(dto.categoriaId()).orElseThrow(() -> new CategoriaNaoExistenteException("Categoria inexistente"));

        if(!categoria.getUsuario().equals(usuarioAutenticado)){
            throw new AcessoNegadoException("acesso negado");
        }

        Gasto gasto = new Gasto();
        gasto.setValor(dto.valor());
        gasto.setData(dto.data());
        gasto.setDescricao(dto.descricao());
        gasto.setTipo(GastoTIpo.GASTO_VARIAVEL);
        gasto.setCategoria(categoria);
        gasto.setUsuario(usuarioAutenticado);
        gasto.setAtivo(true);

        var salvar = gastoRepository.save(gasto);

        return new GastoVariavelResponse(salvar.getId(),
                salvar.getValor(),
                salvar.getData(),
                salvar.getDescricao(),
                salvar.getTipo(),
                salvar.getCategoria().getId()
        );
    }

}
