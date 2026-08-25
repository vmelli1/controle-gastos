package com.victor.controle_gastos_port.gasto.controller;

import com.victor.controle_gastos_port.gasto.dto.gasto_request.GastoFixoRequest;
import com.victor.controle_gastos_port.gasto.dto.gasto_request.GastoVariavelRequest;
import com.victor.controle_gastos_port.gasto.dto.gasto_response.GastoFixoResponse;
import com.victor.controle_gastos_port.gasto.dto.gasto_response.GastoVariavelResponse;
import com.victor.controle_gastos_port.gasto.dto.listagem.PageListagemResponse;
import com.victor.controle_gastos_port.gasto.service.cadastrar_gasto.GastoFixoService;
import com.victor.controle_gastos_port.gasto.service.cadastrar_gasto.GastoVariavelService;
import com.victor.controle_gastos_port.gasto.service.listagem_gasto.ListarGastoTotalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/gasto")
public class GastoFixoController {
    @Autowired
    private GastoFixoService gastoFixoService;
    @Autowired
    private GastoVariavelService gastoVariavelService;
    @Autowired
    private ListarGastoTotalService listaGastoService;

    @PostMapping("/cadastrar-gasto-fixo")
    public ResponseEntity<GastoFixoResponse> cadastrarGastoFixo(@RequestBody @Valid GastoFixoRequest dto){
        GastoFixoResponse gastoFixo = gastoFixoService.cadastrarGasto(dto);
        return new ResponseEntity<>(gastoFixo, HttpStatus.CREATED);
    }

    @PostMapping("/cadastrar-gasto-variavel")
    public ResponseEntity<GastoVariavelResponse> cadastrarGastoVariavel(@RequestBody @Valid GastoVariavelRequest dto){
        GastoVariavelResponse gastoVariavel = gastoVariavelService.cadastrarGastoVariavel(dto);
        return new ResponseEntity<>(gastoVariavel, HttpStatus.CREATED);
    }

    @GetMapping("/listar-gasto")
    public ResponseEntity<PageListagemResponse> listarGastoVariavel(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim,
            @RequestParam(defaultValue = "0") int pagina){
        var listar = listaGastoService.listagem(inicio,fim, pagina);
        return new ResponseEntity<>(listar, HttpStatus.OK);
    }
}
