package com.victor.controle_gastos_port.config.exception;

public class CategoriaPossuiGastosException extends RuntimeException {
    public CategoriaPossuiGastosException(String excluisaoNegada) {
        super(excluisaoNegada);
    }
}
