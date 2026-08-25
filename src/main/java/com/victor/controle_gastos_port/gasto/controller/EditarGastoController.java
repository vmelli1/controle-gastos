package com.victor.controle_gastos_port.gasto.controller;

import com.victor.controle_gastos_port.gasto.dto.editar_gasto_request.EditarGastoRequest;
import com.victor.controle_gastos_port.gasto.dto.editar_gasto_response.EditarGastoResponse;
import com.victor.controle_gastos_port.gasto.service.editar_gasto.EditarGastoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/editar")
public class EditarGastoController {
    private final EditarGastoService editarGastoService;

    public EditarGastoController(EditarGastoService editarGastoService) {
        this.editarGastoService = editarGastoService;
    }

    @PutMapping("/gasto/{id}")
    public ResponseEntity<EditarGastoResponse> atualizarGasto(@RequestBody EditarGastoRequest dto, @PathVariable Long id){
        EditarGastoResponse gasto = editarGastoService.editarGasto(dto,id);
        return new ResponseEntity<>(gasto, HttpStatus.OK);

    }
}
