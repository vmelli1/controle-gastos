package com.victor.controle_gastos_port.usuario.controller;

import com.victor.controle_gastos_port.usuario.DTO.UsuarioAlterarSenhaRequest;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import com.victor.controle_gastos_port.usuario.service.UsuarioRecuperarSenha;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alterar-senha")
public class UsuarioAlterarSenhaController {
    private final UsuarioRecuperarSenha usuarioRecuperarSenhaService;

    public UsuarioAlterarSenhaController(UsuarioRecuperarSenha usuarioRecuperarSenhaService) {
        this.usuarioRecuperarSenhaService = usuarioRecuperarSenhaService;

    }

    @PostMapping
    public ResponseEntity<Void> alterarSenha(@Valid @RequestBody UsuarioAlterarSenhaRequest dto) {
       usuarioRecuperarSenhaService.alterarSenha(dto);
       return ResponseEntity.noContent().build();
    }
}
