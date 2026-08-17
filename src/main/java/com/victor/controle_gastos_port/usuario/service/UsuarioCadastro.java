package com.victor.controle_gastos_port.usuario.service;

import com.victor.controle_gastos_port.config.SecurityConfig;
import com.victor.controle_gastos_port.usuario.DTO.UsuarioRequest;
import com.victor.controle_gastos_port.usuario.DTO.UsuarioResponse;
import com.victor.controle_gastos_port.usuario.model.Role;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import com.victor.controle_gastos_port.usuario.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UsuarioCadastro {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioCadastro(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder1) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder1;

    }


    @Transactional
    public UsuarioResponse cadastrarUsuario(UsuarioRequest dto) {
       if(usuarioRepository.existsByEmail(dto.email())){
           throw new EmailJaCadastroException("E-mail já possui um cadastro");
       }
        String password = passwordEncoder.encode(dto.senha());


       Usuario cadastro = new Usuario();
       cadastro.setNome(dto.nome());
       cadastro.setEmail(dto.email());
       cadastro.setSenha(password);
       cadastro.setDataNascimento(dto.dataNascimento());
       cadastro.setRole(Role.USER);
       cadastro.setAtivo(true);

       var savedCadastro = usuarioRepository.save(cadastro);

       return new UsuarioResponse(savedCadastro.getId(), savedCadastro.getNome(), savedCadastro.getEmail(),
               savedCadastro.getDataNascimento(), savedCadastro.getRole(), savedCadastro.isAtivo());

    }

}
