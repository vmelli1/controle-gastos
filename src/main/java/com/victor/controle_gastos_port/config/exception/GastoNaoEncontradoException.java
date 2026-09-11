package com.victor.controle_gastos_port.config.exception;

public class GastoNaoEncontradoException extends RuntimeException {
    public GastoNaoEncontradoException(Long id) {
        super("Gasto com ID " + id + " não encontrado.");
    }
}
