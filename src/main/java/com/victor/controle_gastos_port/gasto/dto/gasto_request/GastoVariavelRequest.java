package com.victor.controle_gastos_port.gasto.dto.gasto_request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GastoVariavelRequest(
        @Positive
        @NotNull
        BigDecimal valor,
        @NotNull
        LocalDate data,
        @NotBlank @Size(min = 1, max = 100)
        String descricao,
        @NotNull
        Long categoriaId
) {
}
