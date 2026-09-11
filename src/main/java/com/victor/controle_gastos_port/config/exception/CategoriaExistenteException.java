package com.victor.controle_gastos_port.config.exception;

public class CategoriaExistenteException extends RuntimeException {
    public CategoriaExistenteException(String message) {
        super(message);
    }
}
