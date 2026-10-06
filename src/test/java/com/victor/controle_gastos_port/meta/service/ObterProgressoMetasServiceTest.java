package com.victor.controle_gastos_port.meta.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;

import com.victor.controle_gastos_port.categoria.model.Categoria;
import com.victor.controle_gastos_port.config.IUsuarioAutenticadoProvider;
import com.victor.controle_gastos_port.gasto.repository.GastoRepository;
import com.victor.controle_gastos_port.meta.dto.ObterProgressoMetasResponse;
import com.victor.controle_gastos_port.meta.model.Meta;
import com.victor.controle_gastos_port.meta.repository.Metarepository;
import com.victor.controle_gastos_port.usuario.model.Usuario;

@ExtendWith(MockitoExtension.class)
public class ObterProgressoMetasServiceTest {
    @Mock 
    private GastoRepository gastoRepository;
    @Mock
    private Metarepository metarepository;
    @Mock 
    private IUsuarioAutenticadoProvider usuarioProvider;
    @InjectMocks
    private ObterProgressoMetasService service;

    private Usuario usuario;
    private Categoria alimentacao;
    private final YearMonth mes = YearMonth.of(2026, 9);

    @BeforeEach 
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);

        alimentacao = new Categoria();
        alimentacao.setId(10L);
        alimentacao.setNome("Alimentação");   
    }

    private Meta criarMeta(String valorLimite){
        Meta meta = new Meta();
        meta.setId(100L);
        meta.setValorLimite(new BigDecimal(valorLimite));
        meta.setCategoria(alimentacao);
        meta.setMesReferencia(mes);
        meta.setUsuario(usuario);
        meta.setAtivo(true);
        return meta;
    }
    @Test 
    @DisplayName ("Sem gastos na Categoria, o total é zero e a meta não estoura")
    void semGastos_totalZero_naoEstoura(){
        // arrange (preparar)
        when (usuarioProvider.getUsuarioLogado()).thenReturn(usuario);
        when(metarepository.metaAtivaPorUsuarioPorMes(usuario,mes)).thenReturn(List.of(criarMeta("100.00")));
               when(gastoRepository.somarPorCategoriaEDatas(eq(usuario), eq(alimentacao), any(), any()))
                .thenReturn(null);

        // act (agir - executar)
        List<ObterProgressoMetasResponse> resultado = service.progressoMetas(mes);
        // verify (verificar se o método foi chamado)
        verify(gastoRepository).somarPorCategoriaEDatas(eq(usuario), eq(alimentacao), 
        eq(LocalDate.of(2026, 9, 1)), 
        eq(LocalDate.of(2026, 9, 30)));

        // assert (verificar)
        assertThat(resultado).hasSize(1);
        ObterProgressoMetasResponse r = resultado.get(0);
        assertThat(r.totalGasto()).isEqualByComparingTo("0");
        assertThat(r.percentualAtingido()).isEqualByComparingTo("0");
        assertThat(r.estourou()).isFalse();
    }
    @Test
    @DisplayName ("Com gastos na categoria, o total é calculado e a meta estoura, limite 200, gasto 250")
    void comGastos_totalCalculado_estoura(){
        //arrange
        when(usuarioProvider.getUsuarioLogado()).thenReturn(usuario);
        when(metarepository.metaAtivaPorUsuarioPorMes(usuario, mes)).thenReturn(List.of(criarMeta("200.00")));
        when(gastoRepository.somarPorCategoriaEDatas(eq(usuario), eq(alimentacao),any(),any())).thenReturn(new BigDecimal("250.00"));

        //act
        List<ObterProgressoMetasResponse> resultado = service.progressoMetas(mes);

        //assert
        assertThat(resultado).hasSize(1);
        ObterProgressoMetasResponse r = resultado.get(0);
        assertThat(r.nomeCategoria()).isEqualTo("Alimentação");
        assertThat(r.valorLimite()).isEqualByComparingTo("200.00");
        assertThat(r.totalGasto()).isEqualByComparingTo("250.00");
        assertThat(r.percentualAtingido()).isEqualByComparingTo("125");
        assertThat(r.estourou()).isTrue();
    }

    @Test
    @DisplayName ("Com gastos na categoria, o total é calculado e a meta Não estoura, limite 200, gasto 170")
    void comGastos_totalCalculado_naoEstoura_limite200_gasto170(){
        //arrange
        when(usuarioProvider.getUsuarioLogado()).thenReturn(usuario);
        when(metarepository.metaAtivaPorUsuarioPorMes(usuario, mes)).thenReturn(List.of(criarMeta("200.00")));
        when(gastoRepository.somarPorCategoriaEDatas(eq(usuario), eq(alimentacao),any(),any())).thenReturn(new BigDecimal("170.00"));

        //act
        List<ObterProgressoMetasResponse> resultado = service.progressoMetas(mes);

        //assert
        assertThat(resultado).hasSize(1);
        ObterProgressoMetasResponse r = resultado.get(0);
        assertThat(r.nomeCategoria()).isEqualTo("Alimentação");
        assertThat(r.valorLimite()).isEqualByComparingTo("200.00");
        assertThat(r.totalGasto()).isEqualByComparingTo("170.00");
        assertThat(r.percentualAtingido()).isEqualByComparingTo("85");
        assertThat(r.estourou()).isFalse();
    }

     @Test
    @DisplayName ("Com gastos na categoria, o total é calculado e a meta não estoura, limite 100, gasto 100")
    void comGastos_totalCalculado_naoEstoura_Limite100_Gasto100(){
        //arrange
        when(usuarioProvider.getUsuarioLogado()).thenReturn(usuario);
        when(metarepository.metaAtivaPorUsuarioPorMes(usuario, mes)).thenReturn(List.of(criarMeta("100.00")));
        when(gastoRepository.somarPorCategoriaEDatas(eq(usuario), eq(alimentacao),any(),any())).thenReturn(new BigDecimal("100.00"));

        //act
        List<ObterProgressoMetasResponse> resultado = service.progressoMetas(mes);

        //assert
        assertThat(resultado).hasSize(1);
        ObterProgressoMetasResponse r = resultado.get(0);
        assertThat(r.nomeCategoria()).isEqualTo("Alimentação");
        assertThat(r.valorLimite()).isEqualByComparingTo("100.00");
        assertThat(r.totalGasto()).isEqualByComparingTo("100.00");
        assertThat(r.percentualAtingido()).isEqualByComparingTo("100");
        assertThat(r.estourou()).isFalse();
    }

    @Test 
    @DisplayName ("Sem metas ativas, o retorno é uma lista vazia")
    void semMetasAtivas_retornaListaVazia(){
        // arrange
        when(usuarioProvider.getUsuarioLogado()).thenReturn(usuario);
        when(metarepository.metaAtivaPorUsuarioPorMes(usuario, mes)).thenReturn(List.of());

        // act
        List<ObterProgressoMetasResponse> resultado = service.progressoMetas(mes);

        // assert
        assertThat(resultado).isEmpty();
        verifyNoInteractions(gastoRepository);
    }

    @Test 
    @DisplayName ("Limite zero, gasto Positivo")
    void limiteZero_gastoPositivo(){
        // arrange
        when(usuarioProvider.getUsuarioLogado()).thenReturn(usuario);
        when(metarepository.metaAtivaPorUsuarioPorMes(usuario, mes)).thenReturn(List.of(criarMeta("0.00")));
        when(gastoRepository.somarPorCategoriaEDatas(eq(usuario), eq(alimentacao),any(),any())).thenReturn(new BigDecimal("50.00"));

        // act
        List<ObterProgressoMetasResponse> resultado = service.progressoMetas(mes);

        // assert
        assertThat(resultado).hasSize(1);
        ObterProgressoMetasResponse r = resultado.get(0);
        assertThat(r.nomeCategoria()).isEqualTo("Alimentação");
        assertThat(r.valorLimite()).isEqualByComparingTo("0.00");
        assertThat(r.totalGasto()).isEqualByComparingTo("50.00");
        assertThat(r.percentualAtingido()).isEqualByComparingTo("0");
        assertThat(r.estourou()).isTrue();
    }


}
