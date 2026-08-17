package com.victor.controle_gastos_port.usuario.controller;

import com.victor.controle_gastos_port.usuario.model.Usuario;
import com.victor.controle_gastos_port.usuario.service.UsuarioLogin;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/perfil")
public class Usuario1Controller {

    private final UsuarioLogin usuarioLoginService;

    public Usuario1Controller(UsuarioLogin usuarioLoginService) {
        this.usuarioLoginService = usuarioLoginService;
    }

    @GetMapping
    public String perfil(Authentication authentication) {
        return "Você está autenticado como: " + authentication.getName();
    }

   // @GetMapping
    //public List<Usuario> listarTodos() {
    //    return usuarioLoginService.listarTodos();
    //}
}
