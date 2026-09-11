package com.victor.controle_gastos_port.gasto.service.cadastrar_gasto;

import com.victor.controle_gastos_port.categoria.model.Categoria;
import com.victor.controle_gastos_port.categoria.repository.CategoriaRepository;
import com.victor.controle_gastos_port.config.IUsuarioAutenticadoProvider;
import com.victor.controle_gastos_port.config.exception.CategoriaNaoExistenteException;
import com.victor.controle_gastos_port.config.exception.DataVencimentoInvalidaException;
import com.victor.controle_gastos_port.gasto.dto.gasto_request.GastoFixoRequest;
import com.victor.controle_gastos_port.gasto.dto.gasto_response.GastoFixoResponse;
import com.victor.controle_gastos_port.gasto.model.Gasto;
import com.victor.controle_gastos_port.gasto.model.GastoTIpo;
import com.victor.controle_gastos_port.gasto.repository.GastoRepository;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import com.victor.controle_gastos_port.config.exception.AcessoNegadoException;
import org.springframework.stereotype.Service;

@Service
public class GastoFixoService {

    private final GastoRepository gastoRepository;
    private final CategoriaRepository categoriaRepository;
    private final IUsuarioAutenticadoProvider usuarioProvider;

    public GastoFixoService(GastoRepository gastoRepository, CategoriaRepository categoriaRepository, IUsuarioAutenticadoProvider usuarioProvider) {
        this.gastoRepository = gastoRepository;
        this.categoriaRepository = categoriaRepository;
        this.usuarioProvider = usuarioProvider;
    }

    public GastoFixoResponse cadastrarGasto (GastoFixoRequest dto){
        Usuario usuarioAutenticado = usuarioProvider.getUsuarioLogado();



        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() ->  new CategoriaNaoExistenteException("Categoria nao existente"));

        if(!categoria.getUsuario().equals(usuarioAutenticado)){
            throw new AcessoNegadoException("Acesso negado.");
        }

        Integer data = validarDataVencimento(dto);

        Gasto gasto = new Gasto();
        gasto.setValor(dto.valor());
        gasto.setData(dto.data());
        gasto.setDescricao(dto.descricao());
        gasto.setTipo(GastoTIpo.GASTO_FIXO);
        gasto.setDataVencimento(data);
        gasto.setCategoria(categoria);
        gasto.setUsuario(usuarioAutenticado);
        gasto.setAtivo(true);


       var salvar = gastoRepository.save(gasto);

       return new GastoFixoResponse(salvar.getId(),salvar.getValor(),salvar.getData(),salvar.getDescricao(),salvar.getTipo(), salvar.getDataVencimento(),salvar.getCategoria().getId());

    }


    private Integer validarDataVencimento(GastoFixoRequest  dto){
            Integer  dataVencimento = dto.dataVencimento();
            if (dataVencimento < 1 || dataVencimento > 31){
                throw new DataVencimentoInvalidaException("Data de vencimento inválida. Não é permitido cadastrar vencimentos no passado.");
            }

        return dataVencimento;
    }
}
