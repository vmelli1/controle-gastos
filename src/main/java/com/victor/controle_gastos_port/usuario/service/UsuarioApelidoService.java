package com.victor.controle_gastos_port.usuario.service;

import com.victor.controle_gastos_port.config.AuthenticatedUserProviderImpl;
import com.victor.controle_gastos_port.usuario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioApelidoService {
    private final AuthenticatedUserProviderImpl authenticatedUserProvider;


    public UsuarioApelidoService(AuthenticatedUserProviderImpl authenticatedUserProvider) {
        this.authenticatedUserProvider = authenticatedUserProvider;
    }
    public String apelidoUsuario() {
        return formatacaoNome();
    }

    private String formatacaoNome(){
        var usuario = authenticatedUserProvider.getUsuarioLogado();
        String[] partesNome = usuario.getNome().trim().split(" ");

        if(partesNome.length == 1){
            return partesNome[0];
        }

        String primeiroNome = partesNome[0];
        String segundoNome = partesNome[partesNome.length - 1];

        return primeiroNome + " " + segundoNome;
    }
}
