package com.victor.controle_gastos_port.logs.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Logs {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,columnDefinition = "TEXT")
    private String mensagem;
    @Column(columnDefinition = "TEXT")
    private String stacktrace;
    private String endpoint;
    @Column(nullable = false)
    private LocalDateTime dataHora;
    @Column(nullable = false)
    private Integer httpStatus;
}
