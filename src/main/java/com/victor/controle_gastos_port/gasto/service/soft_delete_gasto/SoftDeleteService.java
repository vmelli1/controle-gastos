package com.victor.controle_gastos_port.gasto.service.soft_delete_gasto;


import com.victor.controle_gastos_port.admin.service.StatusDesativado;
import com.victor.controle_gastos_port.categoria.repository.CategoriaRepository;
import com.victor.controle_gastos_port.gasto.model.Gasto;
import com.victor.controle_gastos_port.gasto.repository.GastoRepository;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SoftDeleteService {
    private final GastoRepository gastoRepository;
    private final CategoriaRepository categoriaRepository;

    public SoftDeleteService(GastoRepository gastoRepository, CategoriaRepository categoriaRepository) {
        this.gastoRepository = gastoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public void softDelete(Long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || !auth.isAuthenticated()){
            throw new RuntimeException("Acesso negado");
        }
        Usuario usuario = (Usuario) auth.getPrincipal();
        var gasto = gastoRepository.findById(id).orElseThrow(() -> new RuntimeException("Gasto não encontrado"));

        if (!gasto.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("Acesso negado");
        }

        if(!gasto.isAtivo()){
            throw new StatusDesativado("Status atual: desativado");
        }

        gasto.setAtivo(false);

    }
}
