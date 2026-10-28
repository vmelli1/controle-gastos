package com.victor.controle_gastos_port.usuario.service;

import com.victor.controle_gastos_port.config.JwtService;
import com.victor.controle_gastos_port.config.exception.CredencialInvalida;
import com.victor.controle_gastos_port.usuario.DTO.UsuarioLoginRequest;
import com.victor.controle_gastos_port.usuario.DTO.UsuarioLoginResponse;
import com.victor.controle_gastos_port.usuario.model.StatusConta;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import com.victor.controle_gastos_port.usuario.repository.UsuarioRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioLogin  {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;

    private final JwtService jwtService;

    public UsuarioLogin(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository,
                        JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
    }
    public UsuarioLoginResponse login (UsuarioLoginRequest dto){
          var authenticationToken =   new UsernamePasswordAuthenticationToken(dto.email(),dto.senha());
          Authentication authentication = authenticationManager.authenticate(authenticationToken);
          SecurityContextHolder.getContext().setAuthentication(authentication);
          Object principa = authentication.getPrincipal();

          if((!(principa instanceof Usuario usuario))){
              throw new CredencialInvalida("Erro ao processar autenticação: tipo de usuário inválido.");
          }

        String token = jwtService.generateToken(usuario);

        return  new  UsuarioLoginResponse(usuario.getId(), usuario.getEmail(), usuario.getRole(), token);

    }

    public UsuarioLoginResponse reativarCarenciaExclusao(UsuarioLoginRequest dto){
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(dto.email()).orElseThrow(() -> new CredencialInvalida("E-mail ou senha inválidos."));

        if(!passwordEncoder.matches(dto.senha(), usuario.getSenha())){
            throw new CredencialInvalida("E-mail ou senha inválidos.");
        }

        reativarConta(usuario);

        String token = jwtService.generateToken(usuario);

        return new  UsuarioLoginResponse(usuario.getId(), usuario.getEmail(), usuario.getRole(), token);

    }

    private void reativarConta(Usuario usuario){


        if(usuario.getStatusConta() == StatusConta.PENDENTE_EXCLUSAO){
            usuario.setStatusConta(StatusConta.CONTA_REATIVADA);
            usuario.setCarenciaExclusao(null);
            usuario.setAtivo(true);
            usuarioRepository.save(usuario);
        }

    }

}
