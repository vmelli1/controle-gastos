package com.victor.controle_gastos_port.meta.service;

import com.victor.controle_gastos_port.categoria.model.Categoria;
import com.victor.controle_gastos_port.categoria.repository.CategoriaRepository;
import com.victor.controle_gastos_port.config.exception.CategoriaExistenteException;
import com.victor.controle_gastos_port.config.exception.CategoriaNaoEncontradaException;
import com.victor.controle_gastos_port.meta.dto.MetaRequest;
import com.victor.controle_gastos_port.meta.dto.MetaResponse;
import com.victor.controle_gastos_port.meta.model.Meta;
import com.victor.controle_gastos_port.meta.repository.Metarepository;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import com.victor.controle_gastos_port.config.exception.AcessoNegadoException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;



@Service
public class CriarMetaService {
    private final Metarepository metarepository;

    private final CategoriaRepository categoriaRepository;

    public CriarMetaService(Metarepository metarepository, CategoriaRepository categoriaRepository) {
        this.metarepository = metarepository;
        this.categoriaRepository = categoriaRepository;
    }

    public MetaResponse cadastrarMeta(MetaRequest dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AcessoNegadoException("Acesso negado. Usuário não autenticado.");
        }
        Usuario usuario = (Usuario) authentication.getPrincipal();

        Categoria categoria =categoriaRepository.findById(dto.categoriaID()).orElseThrow(()-> new CategoriaNaoEncontradaException(dto.categoriaID()));

        if(!categoria.getUsuario().equals(usuario)){
            throw new AcessoNegadoException("Acesso negado");
        }

        boolean jaExiste = metarepository.existsByUsuarioAndCategoriaAndMesReferenciaAndAtivoTrue(usuario,categoria,dto.mesReferencia());
        if (jaExiste) {
            throw new CategoriaExistenteException("categoria ja cadastrado");
        }



        Meta meta = new Meta();
        meta.setValorLimite(dto.valorLimite());
        meta.setMesReferencia(dto.mesReferencia());
        meta.setUsuario(usuario);
        meta.setCategoria(categoria);
        meta.setAtivo(true);

        var salvar = metarepository.save(meta);

        return new MetaResponse(salvar.getId(),
                salvar.getValorLimite(),
                salvar.getMesReferencia(),
                salvar.getCategoria().getId(),
                salvar.isAtivo());

    }



}
