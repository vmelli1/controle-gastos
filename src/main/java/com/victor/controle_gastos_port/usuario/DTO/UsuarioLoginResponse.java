package com.victor.controle_gastos_port.usuario.DTO;

import com.victor.controle_gastos_port.usuario.model.Role;

public record UsuarioLoginResponse(
        Long id,
        String email,
        Role role,
        String token
) {
}
