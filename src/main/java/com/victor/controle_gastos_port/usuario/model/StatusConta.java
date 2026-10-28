package com.victor.controle_gastos_port.usuario.model;

public enum StatusConta {
    ATIVA,              // Conta funcionando normalmente
    BLOQUEADA,          // Conta suspensa temporariamente (por erro de senha, etc.)
    PENDENTE_EXCLUSAO,  // O usuário pediu para deletar, mas está no período de carência
    CONTA_REATIVADA     // O usuário desistiu de deletar dentro do prazo e voltou a usar
}
