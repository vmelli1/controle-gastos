package com.victor.controle_gastos_port.meta.dto;

import java.math.BigDecimal;

public record ObterProgressoMetasResponse(
        Long metaId,
        String nomeCategoria,
        BigDecimal valorLimite,
        BigDecimal totalGasto,
        BigDecimal percentualAtingido,
        boolean estourou

) {
}
