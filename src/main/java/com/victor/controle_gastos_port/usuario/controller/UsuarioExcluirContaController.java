package com.victor.controle_gastos_port.usuario.controller;

import com.victor.controle_gastos_port.usuario.service.UsuarioExcluirContaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exclusao")
public class UsuarioExcluirContaController {
    private final UsuarioExcluirContaService usuarioExcluirContaService;

    public UsuarioExcluirContaController(UsuarioExcluirContaService usuarioExcluirContaService) {
        this.usuarioExcluirContaService = usuarioExcluirContaService;
    }

    @DeleteMapping
    public ResponseEntity<Void> excluirConta(){
        usuarioExcluirContaService.excluirConta();
        return ResponseEntity.ok().build();
    }
}
