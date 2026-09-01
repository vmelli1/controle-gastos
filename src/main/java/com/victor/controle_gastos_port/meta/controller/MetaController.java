package com.victor.controle_gastos_port.meta.controller;


import com.victor.controle_gastos_port.meta.dto.MetaRequest;
import com.victor.controle_gastos_port.meta.dto.MetaResponse;
import com.victor.controle_gastos_port.meta.dto.ObterProgressoMetasResponse;
import com.victor.controle_gastos_port.meta.service.CriarMetaService;
import com.victor.controle_gastos_port.meta.service.ObterProgressoMetasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/meta")
public class MetaController {
    private final CriarMetaService criarMetaService;
    private final ObterProgressoMetasService obterProgressoMetasService;

    public MetaController(CriarMetaService criarMetaService, ObterProgressoMetasService obterProgressoMetasService) {
        this.criarMetaService = criarMetaService;
        this.obterProgressoMetasService = obterProgressoMetasService;
    }

    @PostMapping("/cadastrar-meta")
    public ResponseEntity<MetaResponse> criarMeta (@RequestBody MetaRequest dto) {
        MetaResponse definirMeta = criarMetaService.cadastrarMeta(dto);
        return new ResponseEntity<>(definirMeta, HttpStatus.CREATED);
    }

    @GetMapping("/progresso-meta")
    public ResponseEntity<List<ObterProgressoMetasResponse>> progressoMeta (@RequestParam("mes") YearMonth mes){
        List<ObterProgressoMetasResponse> progresso = obterProgressoMetasService.progressoMetas(mes);
        return new ResponseEntity<>(progresso, HttpStatus.OK);
    }
}
