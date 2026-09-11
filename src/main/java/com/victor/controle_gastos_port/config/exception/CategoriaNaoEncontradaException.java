package com.victor.controle_gastos_port.config.exception;

public class CategoriaNaoEncontradaException extends RuntimeException {
    public CategoriaNaoEncontradaException(long id) {
        super("Categoria com id: " + id + " nao encontrada");
    }
}
