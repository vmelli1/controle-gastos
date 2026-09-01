package com.victor.controle_gastos_port.meta.model;

import com.victor.controle_gastos_port.categoria.model.Categoria;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.YearMonth;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Meta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Positive
    private BigDecimal valorLimite;
    private YearMonth mesReferencia;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoriaID")
    private Categoria categoria;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarioID")
    private Usuario usuario;
    private  boolean ativo;
}
