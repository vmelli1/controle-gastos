package com.victor.controle_gastos_port.gasto.model;


import com.victor.controle_gastos_port.categoria.model.Categoria;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "gastos")
public class Gasto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    long id;

    @NotNull
    BigDecimal valor;
    @NotNull
    LocalDate data;
    @NotBlank
    String descricao;
    @Enumerated(EnumType.STRING)
    GastoTIpo tipo;
    Integer dataVencimento;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categorias_id")
    Categoria categoria;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="usuarios_id")
    Usuario usuario;

    boolean ativo;
}
