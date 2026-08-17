package com.victor.controle_gastos_port.usuario.controller;


import com.victor.controle_gastos_port.usuario.DTO.UsuarioLoginRequest;
import com.victor.controle_gastos_port.usuario.DTO.UsuarioLoginResponse;
import com.victor.controle_gastos_port.usuario.service.UsuarioLogin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.List;

@RequestMapping("/login")
@RestController
public class UsuarioLoginController {

    private final UsuarioLogin usuarioLogin;

    public UsuarioLoginController(UsuarioLogin usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    @PostMapping
    public ResponseEntity<UsuarioLoginResponse> login(@RequestBody UsuarioLoginRequest dto) {
        UsuarioLoginResponse response = usuarioLogin.login(dto);
        return new  ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/perfil")
    public String perfil(Authentication authentication) {
        return "Você está autenticado como: " + authentication.getName();
    }
}
