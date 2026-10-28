package com.victor.controle_gastos_port.usuario.service;

import com.victor.controle_gastos_port.config.AuthenticatedUserProviderImpl;
import com.victor.controle_gastos_port.config.exception.CredencialInvalida;
import com.victor.controle_gastos_port.usuario.model.Role;
import com.victor.controle_gastos_port.usuario.model.StatusConta;
import com.victor.controle_gastos_port.usuario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UsuarioExcluirContaService {
    private final AuthenticatedUserProviderImpl authenticatedUserProvider;
    private final UsuarioRepository usuarioRepository;

    public UsuarioExcluirContaService(AuthenticatedUserProviderImpl authenticatedUserProvider, UsuarioRepository usuarioRepository) {
        this.authenticatedUserProvider = authenticatedUserProvider;
        this.usuarioRepository = usuarioRepository;
    }

    public void excluirConta(){
       var usuario =  authenticatedUserProvider.getUsuarioLogado();
        if(usuario.getRole() == Role.ADMIN){
            throw new CredencialInvalida("Usuario nao pode ser exluido.");
        }

       if(usuario.getStatusConta() == StatusConta.BLOQUEADA){
         throw new CredencialInvalida("Esta conta já está inativa/desativada.");
       }

       if(usuario.getStatusConta() == StatusConta.PENDENTE_EXCLUSAO){
           throw new CredencialInvalida("Esta conta já possui uma solicitação de exclusão em andamento.");
       }

       usuario.setStatusConta(StatusConta.PENDENTE_EXCLUSAO);
       usuario.setCarenciaExclusao(carenciaExclusao());
       usuario.setAtivo(false);

       usuarioRepository.save(usuario);
    }

    private LocalDate carenciaExclusao(){
        return LocalDate.now().plusDays(30);
    }



}
