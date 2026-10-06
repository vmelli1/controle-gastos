package com.victor.controle_gastos_port.meta.service;


import com.victor.controle_gastos_port.config.IUsuarioAutenticadoProvider;
import com.victor.controle_gastos_port.gasto.repository.GastoRepository;
import com.victor.controle_gastos_port.meta.dto.ObterProgressoMetasResponse;
import com.victor.controle_gastos_port.meta.model.Meta;
import com.victor.controle_gastos_port.meta.repository.Metarepository;
import com.victor.controle_gastos_port.usuario.model.Usuario;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class ObterProgressoMetasService {

    private final GastoRepository gastoRepository;
    private final Metarepository metarepository;
    private final IUsuarioAutenticadoProvider usuarioProvider;

    public ObterProgressoMetasService(GastoRepository gastoRepository, Metarepository metarepository, IUsuarioAutenticadoProvider usuarioProvider) {
        this.gastoRepository = gastoRepository;
        this.metarepository = metarepository;
        this.usuarioProvider = usuarioProvider;
    }

    public List<ObterProgressoMetasResponse> progressoMetas (YearMonth yearMonth) {

        Usuario usuarioAutenticado = usuarioProvider.getUsuarioLogado();

        LocalDate inicio = yearMonth.atDay(1);
        LocalDate fim = yearMonth.atEndOfMonth();


        List<Meta> metas = metarepository.metaAtivaPorUsuarioPorMes(usuarioAutenticado, yearMonth);

        return metas.stream().map(meta -> {

            // Busca o total gasto na categoria da meta no período
            BigDecimal totalGasto = gastoRepository.somarPorCategoriaEDatas(
                    usuarioAutenticado,
                    meta.getCategoria(),
                    inicio,
                    fim
            );

            if (totalGasto == null) {
                totalGasto = BigDecimal.ZERO;
            }

            boolean estouro = totalGasto.compareTo(meta.getValorLimite()) > 0;
            BigDecimal percentual = BigDecimal.ZERO;
            
            if(meta.getValorLimite().compareTo(BigDecimal.ZERO) > 0) {
                percentual = totalGasto.multiply(new BigDecimal("100"))
                        .divide(meta.getValorLimite(), 2, RoundingMode.HALF_UP);
            }

            return new ObterProgressoMetasResponse(
                    meta.getId(),
                    meta.getCategoria().getNome(),
                    meta.getValorLimite(),
                    totalGasto,
                    percentual,
                    estouro
            );

        }).toList();

    }

}
