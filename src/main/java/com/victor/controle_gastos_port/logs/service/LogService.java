package com.victor.controle_gastos_port.logs.service;

import com.victor.controle_gastos_port.logs.dto.PaginacaoLogsDto;
import com.victor.controle_gastos_port.logs.dto.RegistrarLogsDto;
import com.victor.controle_gastos_port.logs.model.Logs;
import com.victor.controle_gastos_port.logs.repository.LogRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LogService {

    private final LogRepository logRepository;

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }


    public void logDetalhado(RegistrarLogsDto dto) {
        Logs logs = new Logs();
        logs.setMensagem(dto.mensagem());
        logs.setStacktrace(dto.stackTrace());
        logs.setEndpoint(dto.endpoint());
        logs.setDataHora(LocalDateTime.now());
        logs.setHttpStatus(dto.httpStatus());

        logRepository.save(logs);
    }

    public PaginacaoLogsDto obterLogs(int pagina) {
        var paginacao = logRepository.findByOrderByDataHoraDesc(PageRequest.of(pagina, 10));
        return new PaginacaoLogsDto(paginacao.getContent()
                .stream()
                .map(l -> new RegistrarLogsDto(
                        l.getMensagem(),
                        l.getStacktrace(),
                        l.getEndpoint(),
                        l.getDataHora(),
                        l.getHttpStatus()
                )).toList(), paginacao.getNumber(), paginacao.getTotalPages(), paginacao.getTotalElements());
    }






}
