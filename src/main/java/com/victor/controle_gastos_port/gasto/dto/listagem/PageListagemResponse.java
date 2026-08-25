package com.victor.controle_gastos_port.gasto.dto.listagem;

import java.math.BigDecimal;
import java.util.List;

public record PageListagemResponse(
        List<ListarGastoTotalResponse> gasto,
        ListarPeriododataResponse periodo,
        BigDecimal total,
        int paginaAtual,
        int totalPagina,
        long totalItem
) {

}
