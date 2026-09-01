package com.victor.controle_gastos_port.gasto.repository;

import com.victor.controle_gastos_port.categoria.model.Categoria;
import com.victor.controle_gastos_port.dashboard.dto.ExibirGraficoResponse;
import com.victor.controle_gastos_port.gasto.model.Gasto;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


public interface GastoRepository extends JpaRepository<Gasto, Long> {

    Page<Gasto> findByUsuarioAndAtivoTrueAndDataBetween(Usuario usuario, LocalDate inicio, LocalDate fim, Pageable pageable);

    @Query("SELECT COALESCE(SUM(g.valor), 0) FROM Gasto g WHERE g.usuario = :usuario AND g.ativo = true AND g.data BETWEEN :inicio AND :fim")
    BigDecimal somarTotalPorUsuarioEDatas(
            @Param("usuario") Usuario usuario,
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim
    );

    @Query("""
    SELECT COALESCE(COUNT(g.valor),0)
    from Gasto g where g.usuario = :usuario and g.ativo = true and g.data BETWEEN :inicio and :fim
    """)
    BigDecimal QuantidadeDeValorPorUsuarioEDatas(Usuario usuario, LocalDate inicio, LocalDate fim);

    @Query("""
    SELECT new com.victor.controle_gastos_port.dashboard.dto.ExibirGraficoResponse( c.nome, (sum(g.valor))) FROM Gasto g join g.categoria c where g.usuario = :usuario and g.ativo = true and g.data BETWEEN :inicio and :fim
    GROUP BY c.id, c.nome
""")
    List<ExibirGraficoResponse> exibirGraficoPorPeriodoEUsuario(
            @Param("usuario") Usuario usuario,
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fin
    );

    @Query("""
    SELECT SUM(g.valor) FROM Gasto g\s
    WHERE g.usuario = :usuario\s
      AND g.categoria = :categoria\s
      AND g.data BETWEEN :inicio AND :fim
""")
    BigDecimal somarPorCategoriaEDatas(
            Usuario usuario,
            Categoria categoria,
            LocalDate inicio,
            LocalDate fim
    );
}
