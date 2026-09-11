package com.victor.controle_gastos_port.logs.dto;

import java.time.LocalDateTime;

public record RegistrarLogsDto(
        String mensagem,
        String stackTrace,
        String endpoint,
        LocalDateTime dataHora,
        Integer httpStatus
) {
}
