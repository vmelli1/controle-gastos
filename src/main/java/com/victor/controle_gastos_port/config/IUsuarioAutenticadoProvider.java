package com.victor.controle_gastos_port.config;

import com.victor.controle_gastos_port.usuario.model.Usuario;

public interface IUsuarioAutenticadoProvider {
    Usuario getUsuarioLogado();
}
