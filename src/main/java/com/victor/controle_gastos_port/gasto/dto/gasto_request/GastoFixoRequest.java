package com.victor.controle_gastos_port.gasto.dto.gasto_request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GastoFixoRequest(
    @NotNull
    @Positive
    BigDecimal valor,
    @NotNull
    LocalDate data,
    @NotBlank @Size(min = 1, max = 100, message = "O campo deve ter no máximo 100 caracteres ")
    String descricao,
    @NotNull
    Integer dataVencimento,
    Long categoriaId

) {
}
