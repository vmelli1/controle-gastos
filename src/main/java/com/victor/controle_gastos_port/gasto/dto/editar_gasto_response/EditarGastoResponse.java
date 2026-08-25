package com.victor.controle_gastos_port.gasto.dto.editar_gasto_response;

import com.victor.controle_gastos_port.gasto.model.GastoTIpo;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EditarGastoResponse(
        Long id,
        BigDecimal valor,
        LocalDate data,
        String descricao,
        GastoTIpo tIpo,
        Integer dataVencimento,
        Long categoriaId
){
}
