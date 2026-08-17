package com.victor.controle_gastos_port.admin.dto;

import org.aspectj.bridge.Message;

public record UsuarioAtivoAndDesativadoResponse(
        String email,
        boolean ativo
) {

}
