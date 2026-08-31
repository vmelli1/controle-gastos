package com.victor.controle_gastos_port.dashboard.dto;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record DashBoardResponse(
        BigDecimal totalGastos,
        BigDecimal mediaGastos,
        BigDecimal totalPeriodoAnterior,
        BigDecimal diferenca,
        List<ExibirGraficoResponse> exibirGraficoService,
        LocalDate inicio,
        LocalDate fim
) {
}
