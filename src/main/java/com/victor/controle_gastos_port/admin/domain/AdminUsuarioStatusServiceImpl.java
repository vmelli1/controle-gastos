package com.victor.controle_gastos_port.admin.domain;


import com.victor.controle_gastos_port.admin.dto.UsuarioAtivoAndDesativado;
import com.victor.controle_gastos_port.admin.dto.UsuarioAtivoAndDesativadoResponse;
import com.victor.controle_gastos_port.admin.service.CredencialNaoEncontrado;
import com.victor.controle_gastos_port.admin.service.StatusDesativado;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
class AdminUsuarioStatusServiceImpl implements AdminUsuarioStatusService {

    private final AdminRepository adminRepository;

     AdminUsuarioStatusServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }


    @Transactional
    @Override
    public UsuarioAtivoAndDesativadoResponse statusDesativar(UsuarioAtivoAndDesativado dto) {

        var usuario= validarUsuario(dto.id());

        if(!usuario.isAtivo()){
            throw new StatusDesativado("Status atual desativado");
        }

        usuario.setAtivo(false);

        return new UsuarioAtivoAndDesativadoResponse(usuario.getEmail(), usuario.isAtivo());
    }

    @Transactional
    @Override
    public UsuarioAtivoAndDesativadoResponse statusAtivar(UsuarioAtivoAndDesativado dto){
        var usuario= validarUsuario(dto.id());
        if(usuario.isAtivo()){
            throw new StatusDesativado("Status atual: ativo");
        }

        usuario.setAtivo(true);

        return new UsuarioAtivoAndDesativadoResponse(usuario.getEmail(), usuario.isAtivo());
    }

    @Override
    public List<Usuario> listar(){
         List<Usuario> usuarios;
         usuarios = adminRepository.findAll();
         return usuarios;
    }

    private Usuario validarUsuario(long id) {
       Usuario usuario;
       usuario = adminRepository.findById(id).orElseThrow(() -> new CredencialNaoEncontrado("Usuario não encontrado"));
       return  usuario;
    }




}

// desativar a conta

