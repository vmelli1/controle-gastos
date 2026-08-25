package com.victor.controle_gastos_port.gasto.service.cadastrar_gasto;

import com.victor.controle_gastos_port.categoria.model.Categoria;
import com.victor.controle_gastos_port.categoria.repository.CategoriaRepository;
import com.victor.controle_gastos_port.gasto.dto.gasto_request.GastoVariavelRequest;
import com.victor.controle_gastos_port.gasto.dto.gasto_response.GastoVariavelResponse;
import com.victor.controle_gastos_port.gasto.model.Gasto;
import com.victor.controle_gastos_port.gasto.model.GastoTIpo;
import com.victor.controle_gastos_port.gasto.repository.GastoRepository;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import jakarta.transaction.Transactional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class GastoVariavelService {
    private final GastoRepository gastoRepository;
    private final CategoriaRepository categoriaRepository;

    public GastoVariavelService(GastoRepository gastoRepository, CategoriaRepository categoriaRepository) {
        this.gastoRepository = gastoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public GastoVariavelResponse cadastrarGastoVariavel(GastoVariavelRequest dto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated() ){
            throw new RuntimeException("Acesso negado");
        }
        Usuario usuario = (Usuario) authentication.getPrincipal();
        Categoria categoria = categoriaRepository.findById(dto.categoriaId()).orElseThrow(() -> new RuntimeException("Categoria inexistente"));

        if(!categoria.getUsuario().equals(usuario)){
            throw new RuntimeException("acesso negado");
        }

        Gasto gasto = new Gasto();
        gasto.setValor(dto.valor());
        gasto.setData(dto.data());
        gasto.setDescricao(dto.descricao());
        gasto.setTipo(GastoTIpo.GASTO_VARIAVEL);
        gasto.setCategoria(categoria);
        gasto.setUsuario(usuario);
        gasto.setAtivo(true);

        var salvar = gastoRepository.save(gasto);

        return new GastoVariavelResponse(salvar.getId(),
                salvar.getValor(),
                salvar.getData(),
                salvar.getDescricao(),
                salvar.getTipo(),
                salvar.getCategoria().getId()
        );
    }

}
