package com.victor.controle_gastos_port.admin.service;

public class CredencialNaoEncontrado extends RuntimeException {
    public CredencialNaoEncontrado(String message) {
        super(message);
    }
}
