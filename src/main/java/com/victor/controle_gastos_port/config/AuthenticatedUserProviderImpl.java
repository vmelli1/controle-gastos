package com.victor.controle_gastos_port.config;

import com.victor.controle_gastos_port.config.exception.AcessoNegadoException;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUserProviderImpl implements  IUsuarioAutenticadoProvider {
    @Override
    public Usuario getUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()){
            throw new AcessoNegadoException("Acesso negado. Usuário não autenticado.");
        }

        if(!(authentication.getPrincipal() instanceof Usuario)){
            throw new AcessoNegadoException("Acesso negado. Usuário não autenticado.");
        }

        return (Usuario) authentication.getPrincipal();
    }
}
