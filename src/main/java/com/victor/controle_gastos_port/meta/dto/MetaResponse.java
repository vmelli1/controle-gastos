package com.victor.controle_gastos_port.meta.dto;

import java.math.BigDecimal;
import java.time.YearMonth;

public record MetaResponse (
        Long id,
        BigDecimal valorLimite,
        YearMonth mesReferencia,
        Long CategoriaId,
        boolean ativo
) {
}
