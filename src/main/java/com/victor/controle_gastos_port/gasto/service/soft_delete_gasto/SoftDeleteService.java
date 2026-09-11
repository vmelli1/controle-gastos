package com.victor.controle_gastos_port.gasto.service.soft_delete_gasto;


import com.victor.controle_gastos_port.admin.service.StatusDesativado;
import com.victor.controle_gastos_port.config.exception.GastoNaoEncontradoException;
import com.victor.controle_gastos_port.gasto.repository.GastoRepository;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import com.victor.controle_gastos_port.config.exception.AcessoNegadoException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SoftDeleteService {
    private final GastoRepository gastoRepository;


    public SoftDeleteService(GastoRepository gastoRepository) {
        this.gastoRepository = gastoRepository;

    }

    @Transactional
    public void softDelete(Long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || !auth.isAuthenticated()){
            throw new AcessoNegadoException("Acesso negado. Usuário não autenticado.");
        }
        Usuario usuario = (Usuario) auth.getPrincipal();
        var gasto = gastoRepository.findById(id).orElseThrow(() -> new GastoNaoEncontradoException(id));

        if (!gasto.getUsuario().getId().equals(usuario.getId())) {
            throw new AcessoNegadoException("Acesso negado");
        }

        if(!gasto.isAtivo()){
            throw new StatusDesativado("Status atual: desativado");
        }

        gasto.setAtivo(false);

    }
}
