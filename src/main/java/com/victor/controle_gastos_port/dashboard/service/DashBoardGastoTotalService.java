package com.victor.controle_gastos_port.dashboard.service;

import com.victor.controle_gastos_port.config.exception.IntervaloDataInvalidoException;
import com.victor.controle_gastos_port.dashboard.dto.DashBoardResponse;

import com.victor.controle_gastos_port.gasto.repository.GastoRepository;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import com.victor.controle_gastos_port.config.exception.AcessoNegadoException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;



@Service
public class DashBoardGastoTotalService {
    private  final GastoRepository gastoRepository;
    public DashBoardGastoTotalService(GastoRepository gastoRepository) {
        this.gastoRepository = gastoRepository;
    }

    public DashBoardResponse gastoTotal(LocalDate inicio, LocalDate fim){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth==null || !auth.isAuthenticated()){
            throw new AcessoNegadoException("Acesso negado. Usuário não autenticado.");
        }
        Usuario usuario = (Usuario) auth.getPrincipal();

        if(inicio.isAfter(fim)){
            throw new IntervaloDataInvalidoException("Data inicial não pode ser maior que a data final");
        }

        BigDecimal gastoTotal = gastoRepository.somarTotalPorUsuarioEDatas(usuario,inicio,fim);

        if(gastoTotal == null){
            gastoTotal = BigDecimal.ZERO;
        }

        BigDecimal quantidadeGastos  = gastoRepository.QuantidadeDeValorPorUsuarioEDatas(usuario,inicio,fim);
        BigDecimal media = BigDecimal.ZERO;

        if(quantidadeGastos != null  && quantidadeGastos.compareTo(BigDecimal.ZERO) >0){
            media = gastoTotal.divide(quantidadeGastos ,2, RoundingMode.HALF_UP);
        }

        var exibir = gastoRepository.exibirGraficoPorPeriodoEUsuario(usuario,inicio,fim);


        long dia = ChronoUnit.DAYS.between(inicio,fim);
        LocalDate fimAnterior = inicio.minusDays(1);
        LocalDate inicioAnterior = fimAnterior.minusDays(dia);
        BigDecimal totalAnterior = gastoRepository.somarTotalPorUsuarioEDatas(usuario,inicioAnterior,fimAnterior);
        BigDecimal diferenca = gastoTotal.subtract(totalAnterior);

        if(totalAnterior == null){
            totalAnterior = BigDecimal.ZERO;
        }



        return new DashBoardResponse(gastoTotal,media,totalAnterior,diferenca,exibir,inicio,fim);
    }




}
