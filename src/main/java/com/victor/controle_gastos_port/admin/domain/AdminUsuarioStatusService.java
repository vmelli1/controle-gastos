package com.victor.controle_gastos_port.admin.domain;

import com.victor.controle_gastos_port.admin.dto.UsuarioAtivoAndDesativado;
import com.victor.controle_gastos_port.admin.dto.UsuarioAtivoAndDesativadoResponse;
import com.victor.controle_gastos_port.usuario.model.Usuario;

import java.util.List;

public interface AdminUsuarioStatusService {
    UsuarioAtivoAndDesativadoResponse statusDesativar(UsuarioAtivoAndDesativado dto);
    UsuarioAtivoAndDesativadoResponse statusAtivar(UsuarioAtivoAndDesativado dto);

    List<Usuario> listar();
}
