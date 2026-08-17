package com.victor.controle_gastos_port.admin.service;

public class StatusDesativado extends RuntimeException {
    public StatusDesativado(String mensagem) {
        super(mensagem);
    }
}
