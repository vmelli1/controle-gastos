package com.victor.controle_gastos_port.usuario.DTO;

public record UsuarioLoginRequest(
        String email,
        String senha
) {
}
