package com.victor.controle_gastos_port.categoria.dto;

public record DeletarCategoriaRequest(
        int id
) {
}

// deletar categoria, vai deletar apenas a categoria que gostaria, precisa validar a exclusao.
//
