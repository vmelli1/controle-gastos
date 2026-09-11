package com.victor.controle_gastos_port.gasto.service.soft_delete_gasto;


import com.victor.controle_gastos_port.admin.service.StatusDesativado;
import com.victor.controle_gastos_port.config.IUsuarioAutenticadoProvider;
import com.victor.controle_gastos_port.config.exception.GastoNaoEncontradoException;
import com.victor.controle_gastos_port.gasto.repository.GastoRepository;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import com.victor.controle_gastos_port.config.exception.AcessoNegadoException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class SoftDeleteService {
    private final GastoRepository gastoRepository;
    private final IUsuarioAutenticadoProvider usuarioProvider;


    public SoftDeleteService(GastoRepository gastoRepository, IUsuarioAutenticadoProvider usuarioProvider) {
        this.gastoRepository = gastoRepository;
        this.usuarioProvider = usuarioProvider;
    }

    @Transactional
    public void softDelete(Long id){
        Usuario usuarioAutenticado = usuarioProvider.getUsuarioLogado();

        var gasto = gastoRepository.findById(id).orElseThrow(() -> new GastoNaoEncontradoException(id));

        if (!gasto.getUsuario().getId().equals(usuarioAutenticado.getId())) {
            throw new AcessoNegadoException("Acesso negado");
        }

        if(!gasto.isAtivo()){
            throw new StatusDesativado("Status atual: desativado");
        }

        gasto.setAtivo(false);

    }
}
