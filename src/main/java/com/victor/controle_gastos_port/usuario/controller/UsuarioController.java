package com.victor.controle_gastos_port.usuario.controller;

import com.victor.controle_gastos_port.usuario.DTO.UsuarioRequest;
import com.victor.controle_gastos_port.usuario.DTO.UsuarioResponse;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import com.victor.controle_gastos_port.usuario.service.EmailJaCadastroException;
import com.victor.controle_gastos_port.usuario.service.UsuarioCadastro;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cadastro")
@RestController
public class UsuarioController {
    private final UsuarioCadastro usuarioCadastro;


    public UsuarioController(UsuarioCadastro usuarioCadastro) {
        this.usuarioCadastro = usuarioCadastro;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> cadastrarUsuario(@RequestBody @Valid UsuarioRequest usuario) {
            UsuarioResponse usuarioCadastrado = usuarioCadastro.cadastrarUsuario(usuario);
            return new ResponseEntity<>(usuarioCadastrado, HttpStatus.CREATED);

    }


}
