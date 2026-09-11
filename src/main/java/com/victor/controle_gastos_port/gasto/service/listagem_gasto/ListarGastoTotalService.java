package com.victor.controle_gastos_port.gasto.service.listagem_gasto;



import com.victor.controle_gastos_port.config.IUsuarioAutenticadoProvider;
import com.victor.controle_gastos_port.config.exception.IntervaloDataInvalidoException;
import com.victor.controle_gastos_port.gasto.dto.listagem.ListarGastoTotalResponse;
import com.victor.controle_gastos_port.gasto.dto.listagem.ListarPeriododataResponse;
import com.victor.controle_gastos_port.gasto.dto.listagem.PageListagemResponse;
import com.victor.controle_gastos_port.gasto.model.Gasto;
import com.victor.controle_gastos_port.gasto.repository.GastoRepository;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ListarGastoTotalService {
    private final GastoRepository gastoRepository;
    private final IUsuarioAutenticadoProvider usuarioProvider;

    public ListarGastoTotalService(GastoRepository gastoRepository, IUsuarioAutenticadoProvider usuarioProvider) {
        this.gastoRepository = gastoRepository;
        this.usuarioProvider = usuarioProvider;
    }

    public PageListagemResponse listagem(LocalDate inicio, LocalDate fim,int pagina){

        Usuario usuarioAutenticado = usuarioProvider.getUsuarioLogado();

        if(inicio.isAfter(fim)){
            throw new IntervaloDataInvalidoException("Data inicial não pode ser maior que a data final");
        }

        var listagem = gastoRepository.findByUsuarioAndAtivoTrueAndDataBetween(usuarioAutenticado,inicio,fim, PageRequest.of(pagina,10));

        List<ListarGastoTotalResponse> gastos = new ArrayList<>();
        BigDecimal total = gastoRepository.somarTotalPorUsuarioEDatas(usuarioAutenticado,inicio,fim);

        for(Gasto gasto : listagem.getContent()){
                gastos.add(new ListarGastoTotalResponse(
                        gasto.getId(),
                        gasto.getValor(),
                        gasto.getData(),
                        gasto.getDescricao(),
                        gasto.getTipo(),
                        gasto.getDataVencimento(),
                        gasto.getCategoria().getId(),
                        gasto.isAtivo()
                ));

        }

        ListarPeriododataResponse periodos = new ListarPeriododataResponse(inicio,fim);



        return new PageListagemResponse(gastos,periodos, total,listagem.getNumber(),listagem.getTotalPages(),listagem.getTotalElements());

    }
}
