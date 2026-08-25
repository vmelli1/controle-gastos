package com.victor.controle_gastos_port.gasto.dto.listagem;

import com.victor.controle_gastos_port.gasto.model.GastoTIpo;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ListarGastoTotalResponse(
        long id,
        BigDecimal valor,
        LocalDate data,
        String descricao,
        GastoTIpo tipo,
        Integer dataVencimento,
        long categoriaId,
        Boolean ativo
) {
}
