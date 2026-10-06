package com.victor.controle_gastos_port.meta.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;


import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.victor.controle_gastos_port.categoria.model.Categoria;
import com.victor.controle_gastos_port.categoria.repository.CategoriaRepository;
import com.victor.controle_gastos_port.config.IUsuarioAutenticadoProvider;
import com.victor.controle_gastos_port.config.exception.AcessoNegadoException;
import com.victor.controle_gastos_port.config.exception.CategoriaExistenteException;
import com.victor.controle_gastos_port.config.exception.CategoriaNaoEncontradaException;
import com.victor.controle_gastos_port.meta.dto.MetaRequest;
import com.victor.controle_gastos_port.meta.model.Meta;
import com.victor.controle_gastos_port.meta.repository.Metarepository;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import com.victor.controle_gastos_port.meta.dto.MetaResponse;

@ExtendWith (MockitoExtension.class)
public class CriarMetaServiceTest {
    @Mock 
    private Metarepository metarepository;

    @Mock 
    private CategoriaRepository categoriaRepository;

    @Mock
    private IUsuarioAutenticadoProvider usuarioProvider;

    @InjectMocks 
    private CriarMetaService criarMetaService;

    private Usuario usuario;
    private Categoria categoria;
    private final YearMonth mes = YearMonth.of(2026, 10);

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        
        categoria = new Categoria();
        categoria.setId(10L);
        categoria.setNome("alimentação");
        categoria.setUsuario(usuario);
    }

    private MetaRequest criarMetaRequest(Long categoriaId) {
        return new MetaRequest(new BigDecimal("200.00"),mes,categoriaId,true);
    }

    @Test
    @DisplayName("Categoria inexistente lança CategoriaNaoEncontradaException e não salva a meta")
    void cadastrarMeta_categoriaInexistente_lancaExcecao_naoSalva() {

        //arrange
        when(usuarioProvider.getUsuarioLogado()).thenReturn(usuario);
        when(categoriaRepository.findById(99l)).thenReturn(Optional.empty());

        //act & assert
        CategoriaNaoEncontradaException exception = assertThrows(CategoriaNaoEncontradaException.class,() -> {
            criarMetaService.cadastrarMeta(criarMetaRequest(99l));
        });

        assertThat(exception.getMessage()).isEqualTo("Categoria com id: 99 nao encontrada");
        verifyNoInteractions(metarepository);
    }

    @Test 
    @DisplayName("Meta já cadastrada para a categoria e mês lança CategoriaExistenteException")
    void cadastrarMeta_metaJaCadastrada_lancaExcecao_naoSalva() {
        // arrange
        when(usuarioProvider.getUsuarioLogado()).thenReturn(usuario);
        when(categoriaRepository.findById(10l)).thenReturn(Optional.of(categoria));
        when(metarepository.existsByUsuarioAndCategoriaAndMesReferenciaAndAtivoTrue(usuario, categoria, mes)).thenReturn(true);

        //act & assert
        CategoriaExistenteException exception = assertThrows(CategoriaExistenteException.class, () -> {
            criarMetaService.cadastrarMeta(criarMetaRequest(10l));
        });

        assertThat(exception.getMessage()).isEqualTo("categoria ja cadastrado");
        verify(metarepository, never()).save(any());
    }

    @Test 
    @DisplayName ("Categoria de outro usuário lança AcessoNegadoException e não salva a meta")
    void cadastrarMeta_categoriaOutroUsuario_lancaExcecao_naoSalva() {

        // arrange
        Usuario outroUsuario = new Usuario();
        outroUsuario.setId(2L);
        categoria.setUsuario(outroUsuario);
        
        when(usuarioProvider.getUsuarioLogado()).thenReturn(usuario);
        when(categoriaRepository.findById(10l)).thenReturn(Optional.of(categoria));

        //act & assert
        AcessoNegadoException exception = assertThrows(AcessoNegadoException.class, () -> {
            criarMetaService.cadastrarMeta(criarMetaRequest(10l));
        });
        assertThat(exception.getMessage()).isEqualTo("Acesso negado");
        verifyNoInteractions(metarepository);
    }

    @Test 
    @DisplayName ("Dados válidos, meta é salva corretamente")
    void cadastrarMeta_dadosValidos_salvaEDevolveResponse(){
        // arrange 
        when(usuarioProvider.getUsuarioLogado()).thenReturn(usuario);
        when(categoriaRepository.findById(10L)).thenReturn(Optional.of(categoria));
        when(metarepository.existsByUsuarioAndCategoriaAndMesReferenciaAndAtivoTrue(usuario, categoria, mes)).thenReturn(false);
        when(metarepository.save(any())).thenAnswer(
            invocation -> {
                Meta meta = invocation.getArgument(0);
                meta.setId(100L); 
                return meta;
            }
        );
        // act
        MetaResponse response = criarMetaService.cadastrarMeta(criarMetaRequest(10L));

        // assert
        assertThat(response.id()).isEqualTo(100L);
        assertThat(response.valorLimite()).isEqualTo(new BigDecimal("200.00"));
        assertThat(response.mesReferencia()).isEqualTo(mes);
        assertThat(response.CategoriaId()).isEqualTo(10L);
        assertThat(response.ativo()).isTrue();

    }
}
