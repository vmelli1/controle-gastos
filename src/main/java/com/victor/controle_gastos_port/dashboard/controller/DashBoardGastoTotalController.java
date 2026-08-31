package com.victor.controle_gastos_port.dashboard.controller;


import com.victor.controle_gastos_port.dashboard.dto.DashBoardResponse;
import com.victor.controle_gastos_port.dashboard.service.DashBoardGastoTotalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/dashboard")
public class DashBoardGastoTotalController {
    private final DashBoardGastoTotalService dashBoardGastoTotalService;

    public DashBoardGastoTotalController(DashBoardGastoTotalService dashBoardGastoTotalService) {
        this.dashBoardGastoTotalService = dashBoardGastoTotalService;
    }

    @GetMapping
    public ResponseEntity<DashBoardResponse> total(@RequestParam LocalDate inicio, @RequestParam LocalDate fim){
        DashBoardResponse totalGasto = dashBoardGastoTotalService.gastoTotal(inicio,fim);
        return ResponseEntity.ok(totalGasto);
    }
}
