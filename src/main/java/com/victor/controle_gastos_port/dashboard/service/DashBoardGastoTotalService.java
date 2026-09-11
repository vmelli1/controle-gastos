package com.victor.controle_gastos_port.dashboard.service;

import com.victor.controle_gastos_port.config.IUsuarioAutenticadoProvider;
import com.victor.controle_gastos_port.config.exception.IntervaloDataInvalidoException;
import com.victor.controle_gastos_port.dashboard.dto.DashBoardResponse;

import com.victor.controle_gastos_port.gasto.repository.GastoRepository;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;



@Service
public class DashBoardGastoTotalService {
    private  final GastoRepository gastoRepository;
    private final IUsuarioAutenticadoProvider usuarioProvider;

    public DashBoardGastoTotalService(GastoRepository gastoRepository, IUsuarioAutenticadoProvider usuarioProvider) {
        this.gastoRepository = gastoRepository;
        this.usuarioProvider = usuarioProvider;
    }

    public DashBoardResponse gastoTotal(LocalDate inicio, LocalDate fim){
        Usuario usuarioAutenticado = usuarioProvider.getUsuarioLogado();


        if(inicio.isAfter(fim)){
            throw new IntervaloDataInvalidoException("Data inicial não pode ser maior que a data final");
        }

        BigDecimal gastoTotal = gastoRepository.somarTotalPorUsuarioEDatas(usuarioAutenticado,inicio,fim);

        if(gastoTotal == null){
            gastoTotal = BigDecimal.ZERO;
        }

        BigDecimal quantidadeGastos  = gastoRepository.QuantidadeDeValorPorUsuarioEDatas(usuarioAutenticado,inicio,fim);
        BigDecimal media = BigDecimal.ZERO;

        if(quantidadeGastos != null  && quantidadeGastos.compareTo(BigDecimal.ZERO) >0){
            media = gastoTotal.divide(quantidadeGastos ,2, RoundingMode.HALF_UP);
        }

        var exibir = gastoRepository.exibirGraficoPorPeriodoEUsuario(usuarioAutenticado,inicio,fim);


        long dia = ChronoUnit.DAYS.between(inicio,fim);
        LocalDate fimAnterior = inicio.minusDays(1);
        LocalDate inicioAnterior = fimAnterior.minusDays(dia);
        BigDecimal totalAnterior = gastoRepository.somarTotalPorUsuarioEDatas(usuarioAutenticado,inicioAnterior,fimAnterior);
        BigDecimal diferenca = gastoTotal.subtract(totalAnterior);

        if(totalAnterior == null){
            totalAnterior = BigDecimal.ZERO;
        }



        return new DashBoardResponse(gastoTotal,media,totalAnterior,diferenca,exibir,inicio,fim);
    }




}
