package com.victor.controle_gastos_port.meta.repository;

import com.victor.controle_gastos_port.categoria.model.Categoria;
import com.victor.controle_gastos_port.meta.model.Meta;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.YearMonth;
import java.util.List;

public interface Metarepository extends JpaRepository<Meta, Long> {
    boolean existsByUsuarioAndCategoriaAndMesReferenciaAndAtivoTrue(Usuario usuario, Categoria categoria, YearMonth yearMonth);

    @Query("""
    SELECT m FROM Meta m WHERE m.usuario = :usuario\s
      AND m.ativo = true\s
      AND m.mesReferencia = :yearMonth
""")
    List<Meta> metaAtivaPorUsuarioPorMes(Usuario usuario, YearMonth yearMonth);
}
