package com.victor.controle_gastos_port.usuario.service;

import com.victor.controle_gastos_port.config.JwtService;
import com.victor.controle_gastos_port.config.exception.CredencialInvalida;
import com.victor.controle_gastos_port.usuario.DTO.UsuarioLoginRequest;
import com.victor.controle_gastos_port.usuario.DTO.UsuarioLoginResponse;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import com.victor.controle_gastos_port.usuario.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioLogin {
    private static final Logger log = LoggerFactory.getLogger(UsuarioLogin.class);
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public UsuarioLogin(UsuarioRepository usuarioRepository, PasswordEncoder encoder, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }


    public UsuarioLoginResponse login (UsuarioLoginRequest dto){
        Usuario usuario = (Usuario) usuarioRepository.findByEmail(dto.email());

        if(usuario == null){
            throw new CredencialInvalida("Usuario nao encontrado");
        }


        if(!usuario.isAtivo()){
            throw new CredencialInvalida("Usuario bloqueado");
        }

        if(!encoder.matches(dto.senha(),  usuario.getSenha())){
            log.info("Credencial invalida do: {}", dto.email());
            throw new CredencialInvalida("Credencial Invalida");
        }



        String token = jwtService.generateToken(usuario);

        return  new  UsuarioLoginResponse(usuario.getId(), usuario.getEmail(), usuario.getRole(), token);

    }
}
