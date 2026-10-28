package com.victor.controle_gastos_port.usuario.controller;

import com.victor.controle_gastos_port.usuario.DTO.UsuarioAlterarSenhaRequest;
import com.victor.controle_gastos_port.usuario.service.UsuarioApelidoService;
import com.victor.controle_gastos_port.usuario.service.UsuarioExcluirContaService;
import com.victor.controle_gastos_port.usuario.service.UsuarioRecuperarSenha;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/perfil")
public class UsuariosPerfilController {
    private final UsuarioRecuperarSenha usuarioRecuperarSenhaService;
    private final UsuarioExcluirContaService usuarioExcluirContaService;
    private final UsuarioApelidoService usuarioApelidoService;

    public UsuariosPerfilController(UsuarioRecuperarSenha usuarioRecuperarSenhaService, UsuarioExcluirContaService usuarioExcluirContaService, UsuarioApelidoService usuarioApelidoService) {
        this.usuarioRecuperarSenhaService = usuarioRecuperarSenhaService;
        this.usuarioExcluirContaService = usuarioExcluirContaService;
        this.usuarioApelidoService = usuarioApelidoService;
    }

    @GetMapping
    public ResponseEntity<String> apelidoUsuario() {
        var usuarioApelido = usuarioApelidoService.apelidoUsuario();
        return  ResponseEntity.ok(usuarioApelido);
    }

    @PostMapping("/alterar-senha")
    public ResponseEntity<Void> alterarSenha(@Valid @RequestBody UsuarioAlterarSenhaRequest dto) {
       usuarioRecuperarSenhaService.alterarSenha(dto);
       return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/excluir-cadastro")
    public ResponseEntity<Void> excluirConta(){
        usuarioExcluirContaService.excluirConta();
        return ResponseEntity.ok().build();
    }
}
