package com.victor.controle_gastos_port.usuario.DTO;

import com.victor.controle_gastos_port.usuario.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UsuarioRequest(
        @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres")
        String nome,
        @Email
        @Size(min = 3, max = 50,message = "O nome deve ter entre 3 e 50 caracteres")
        String email,
        String senha,
        LocalDate dataNascimento
) {
}
