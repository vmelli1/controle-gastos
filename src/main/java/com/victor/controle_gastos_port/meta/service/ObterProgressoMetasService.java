package com.victor.controle_gastos_port.meta.service;


import com.victor.controle_gastos_port.categoria.repository.CategoriaRepository;
import com.victor.controle_gastos_port.gasto.repository.GastoRepository;
import com.victor.controle_gastos_port.meta.dto.ObterProgressoMetasResponse;
import com.victor.controle_gastos_port.meta.model.Meta;
import com.victor.controle_gastos_port.meta.repository.Metarepository;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import com.victor.controle_gastos_port.config.exception.AcessoNegadoException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class ObterProgressoMetasService {

    private final GastoRepository gastoRepository;
    private final CategoriaRepository categoriaRepository;
    private final Metarepository metarepository;

    public ObterProgressoMetasService(GastoRepository gastoRepository, CategoriaRepository categoriaRepository, Metarepository metarepository) {
        this.gastoRepository = gastoRepository;
        this.categoriaRepository = categoriaRepository;
        this.metarepository = metarepository;
    }

    public List<ObterProgressoMetasResponse> progressoMetas (YearMonth yearMonth) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AcessoNegadoException("Acesso negado. Usuário não autenticado.");
        }
        Usuario usuario = (Usuario) authentication.getPrincipal();

        LocalDate inicio = yearMonth.atDay(1);
        LocalDate fim = yearMonth.atEndOfMonth();


        List<Meta> metas = metarepository.metaAtivaPorUsuarioPorMes(usuario, yearMonth);

        return metas.stream().map(meta -> {

            // Busca o total gasto na categoria da meta no período
            BigDecimal totalGasto = gastoRepository.somarPorCategoriaEDatas(
                    usuario,
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
