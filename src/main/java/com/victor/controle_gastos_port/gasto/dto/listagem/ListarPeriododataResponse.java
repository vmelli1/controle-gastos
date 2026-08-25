package com.victor.controle_gastos_port.gasto.dto.listagem;

import java.time.LocalDate;

public record ListarPeriododataResponse(
        LocalDate inicio,
        LocalDate fim
) {
}
