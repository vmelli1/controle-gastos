package com.victor.controle_gastos_port.meta.dto;

import java.math.BigDecimal;
import java.time.YearMonth;

public record MetaRequest(
        BigDecimal valorLimite,
        YearMonth mesReferencia,
        Long categoriaID,
        Boolean ativo
) {
}
