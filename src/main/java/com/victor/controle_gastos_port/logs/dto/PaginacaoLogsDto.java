package com.victor.controle_gastos_port.logs.dto;

import java.util.List;

public record PaginacaoLogsDto(
        List<RegistrarLogsDto> listRegistroLogs,
        int paginaAtual,
        int totalPaginas,
        long totalItens
) {
}
