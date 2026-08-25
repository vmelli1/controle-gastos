package com.victor.controle_gastos_port.gasto.dto.gasto_response;

import com.victor.controle_gastos_port.gasto.model.GastoTIpo;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GastoVariavelResponse(
        long id,
        BigDecimal valor,
        LocalDate data,
        String descricao,
        GastoTIpo tipo,
        Long categoriaId
) {
}
