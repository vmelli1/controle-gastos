package com.victor.controle_gastos_port.usuario.DTO;

import com.victor.controle_gastos_port.usuario.model.Role;

import java.time.LocalDate;

public record UsuarioResponse(
    Long id,
    String nome,
    String email,
    LocalDate dataNascimento,
    Role role,
    boolean ativo
) {


}
