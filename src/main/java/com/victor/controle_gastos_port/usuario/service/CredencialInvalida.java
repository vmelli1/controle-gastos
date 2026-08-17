package com.victor.controle_gastos_port.usuario.service;

public class CredencialInvalida extends RuntimeException {
    public CredencialInvalida(String message) {
        super(message);
    }
}
