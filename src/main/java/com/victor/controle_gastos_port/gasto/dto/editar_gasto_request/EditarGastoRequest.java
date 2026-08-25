package com.victor.controle_gastos_port.gasto.dto.editar_gasto_request;

import com.victor.controle_gastos_port.gasto.model.GastoTIpo;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EditarGastoRequest (
     BigDecimal valor,
     LocalDate data,
     String descricao,
     GastoTIpo tipo,
     Integer dataVencimento,
     long categoriaId


){
}
