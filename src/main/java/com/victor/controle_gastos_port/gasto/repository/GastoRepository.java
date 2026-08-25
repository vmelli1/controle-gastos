package com.victor.controle_gastos_port.gasto.repository;

import com.victor.controle_gastos_port.gasto.model.Gasto;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;


public interface GastoRepository extends JpaRepository<Gasto, Long> {

    Page<Gasto> findByUsuarioAndAtivoTrueAndDataBetween(Usuario usuario, LocalDate inicio, LocalDate fim, Pageable pageable);

    @Query("SELECT COALESCE(SUM(g.valor), 0) FROM Gasto g WHERE g.usuario = :usuario AND g.ativo = true AND g.data BETWEEN :inicio AND :fim")
    BigDecimal somarTotalPorUsuarioEDatas(
            @Param("usuario") Usuario usuario,
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim
    );
}
