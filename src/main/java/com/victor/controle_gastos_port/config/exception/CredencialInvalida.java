package com.victor.controle_gastos_port.config.exception;

public class CredencialInvalida extends RuntimeException {
    public CredencialInvalida(String message) {
        super(message);
    }
}
