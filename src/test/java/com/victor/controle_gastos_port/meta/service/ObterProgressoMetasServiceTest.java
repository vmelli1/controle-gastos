package com.victor.controle_gastos_port.meta.service;

import java.time.YearMonth;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

        // assert (verificar)
        assertThat(resultado).hasSize(1);
        ObterProgressoMetasResponse r = resultado.get(0);
        assertThat(r.totalGasto()).isEqualByComparingTo("0");
        assertThat(r.percentualAtingido()).isEqualByComparingTo("0");
        assertThat(r.estourou()).isFalse();

    }
}
