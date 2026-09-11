package com.victor.controle_gastos_port.config.exception;

public class CategoriaNaoExistenteException extends RuntimeException {
    public CategoriaNaoExistenteException(String message) {
        super(message);
    }
}
