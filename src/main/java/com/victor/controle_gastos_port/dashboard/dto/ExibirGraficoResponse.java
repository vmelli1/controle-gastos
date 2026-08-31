package com.victor.controle_gastos_port.dashboard.dto;

import java.math.BigDecimal;

public record ExibirGraficoResponse(
        String categoria,
        BigDecimal totalCategoria

) {
}
