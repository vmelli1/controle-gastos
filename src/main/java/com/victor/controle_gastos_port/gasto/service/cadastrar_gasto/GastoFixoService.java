package com.victor.controle_gastos_port.gasto.service.cadastrar_gasto;

import com.victor.controle_gastos_port.categoria.model.Categoria;
import com.victor.controle_gastos_port.categoria.repository.CategoriaRepository;
import com.victor.controle_gastos_port.gasto.dto.gasto_request.GastoFixoRequest;
import com.victor.controle_gastos_port.gasto.dto.gasto_response.GastoFixoResponse;
import com.victor.controle_gastos_port.gasto.model.Gasto;
import com.victor.controle_gastos_port.gasto.model.GastoTIpo;
import com.victor.controle_gastos_port.gasto.repository.GastoRepository;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class GastoFixoService {

    private final GastoRepository gastoRepository;
    private final CategoriaRepository categoriaRepository;

    public GastoFixoService(GastoRepository gastoRepository, CategoriaRepository categoriaRepository) {
        this.gastoRepository = gastoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public GastoFixoResponse cadastrarGasto (GastoFixoRequest dto){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || !auth.isAuthenticated()){
            throw new RuntimeException("Acesso negado");
        }
        Usuario usuario = (Usuario) auth.getPrincipal();


        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() ->  new RuntimeException("Categoria nao existente"));

        if(!categoria.getUsuario().equals(usuario)){
            throw new RuntimeException("Acesso negado");
        }

        Integer data = validarDataVencimento(dto);

        Gasto gasto = new Gasto();
        gasto.setValor(dto.valor());
        gasto.setData(dto.data());
        gasto.setDescricao(dto.descricao());
        gasto.setTipo(GastoTIpo.GASTO_FIXO);
        gasto.setDataVencimento(data);
        gasto.setCategoria(categoria);
        gasto.setUsuario(usuario);
        gasto.setAtivo(true);


       var salvar = gastoRepository.save(gasto);

       return new GastoFixoResponse(salvar.getId(),salvar.getValor(),salvar.getData(),salvar.getDescricao(),salvar.getTipo(), salvar.getDataVencimento(),salvar.getCategoria().getId());

    }


    private Integer validarDataVencimento(GastoFixoRequest  dto){
            Integer  dataVencimento = dto.dataVencimento();
            if (dataVencimento < 1 || dataVencimento > 31){
                throw new RuntimeException("Data de vencimento invalido");
            }

        return dataVencimento;
    }
}
