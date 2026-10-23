package com.victor.controle_gastos_port.usuario.service;

import com.victor.controle_gastos_port.config.AuthenticatedUserProviderImpl;
import com.victor.controle_gastos_port.config.JwtService;
import com.victor.controle_gastos_port.config.exception.CredencialInvalida;
import com.victor.controle_gastos_port.usuario.DTO.UsuarioLoginRequest;
import com.victor.controle_gastos_port.usuario.DTO.UsuarioLoginResponse;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import com.victor.controle_gastos_port.usuario.repository.UsuarioRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioLogin  {
    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public UsuarioLogin(AuthenticationManager authenticationManager,
                        JwtService jwtService) {
        this.authenticationManager = authenticationManager;
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
}
