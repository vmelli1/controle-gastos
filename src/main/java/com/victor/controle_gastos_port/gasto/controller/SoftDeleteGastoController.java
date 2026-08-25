package com.victor.controle_gastos_port.gasto.controller;

import com.victor.controle_gastos_port.gasto.service.soft_delete_gasto.SoftDeleteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gasto")
public class SoftDeleteGastoController {

    private final SoftDeleteService softDeleteService;

    public SoftDeleteGastoController(SoftDeleteService softDeleteService) {
        this.softDeleteService = softDeleteService;
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> updateStatus(@PathVariable long id){
        softDeleteService.softDelete(id);
        return new  ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
