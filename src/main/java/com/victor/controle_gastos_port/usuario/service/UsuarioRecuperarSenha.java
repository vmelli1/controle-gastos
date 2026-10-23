package com.victor.controle_gastos_port.usuario.service;

import com.victor.controle_gastos_port.config.AuthenticatedUserProviderImpl;
import com.victor.controle_gastos_port.config.exception.CredencialInvalida;
import com.victor.controle_gastos_port.usuario.DTO.UsuarioAlterarSenhaRequest;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import com.victor.controle_gastos_port.usuario.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioRecuperarSenha {
    private final UsuarioRepository usuarioRepository;
    private final AuthenticatedUserProviderImpl authenticatedUserProvider;
    private final PasswordEncoder encoder;
    public UsuarioRecuperarSenha(UsuarioRepository usuarioRepository, AuthenticatedUserProviderImpl authenticatedUserProvider, PasswordEncoder encoder) {
        this.usuarioRepository = usuarioRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
        this.encoder = encoder;
    }
    public void alterarSenha(UsuarioAlterarSenhaRequest dados) {
    Usuario authenticationPrincipal = authenticatedUserProvider.getUsuarioLogado();

    if(!encoder.matches(dados.senhaAtual(), authenticationPrincipal.getPassword())) {
        throw new CredencialInvalida("Senha digitada não confere com a senha atual!");
    }
    if(!dados.novaSenha().equals(dados.confirmrSenha())) {
        throw new CredencialInvalida("Senha de confirmação invalida!");
    }

    String senhaNova = encoder.encode(dados.novaSenha());

    authenticationPrincipal.alterarSenha(senhaNova);
    usuarioRepository.save(authenticationPrincipal);

    }
}
