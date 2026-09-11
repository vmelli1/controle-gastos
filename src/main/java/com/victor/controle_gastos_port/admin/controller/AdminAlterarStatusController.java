package com.victor.controle_gastos_port.admin.controller;



import com.victor.controle_gastos_port.admin.dto.UsuarioAtivoAndDesativado;
import com.victor.controle_gastos_port.admin.dto.UsuarioAtivoAndDesativadoResponse;
import com.victor.controle_gastos_port.admin.domain.AdminUsuarioStatusService;
import com.victor.controle_gastos_port.logs.dto.PaginacaoLogsDto;
import com.victor.controle_gastos_port.logs.service.LogService;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminAlterarStatusController {
    private final AdminUsuarioStatusService adminUsuarioStatusService;
    private final LogService logService;


    public AdminAlterarStatusController(AdminUsuarioStatusService adminDesativar, LogService logService) {
        this.adminUsuarioStatusService = adminDesativar;
        this.logService = logService;
    }

    @PatchMapping("/desativar")
    public ResponseEntity<UsuarioAtivoAndDesativadoResponse> statusDesativar (@RequestBody UsuarioAtivoAndDesativado dto){
        var desativar = adminUsuarioStatusService.statusDesativar(dto);
        return new ResponseEntity<>(desativar, HttpStatus.OK);
    }

    @PatchMapping("/ativar")
    public ResponseEntity<UsuarioAtivoAndDesativadoResponse> statusAtivar (@RequestBody  UsuarioAtivoAndDesativado dto){
        var desativar = adminUsuarioStatusService.statusAtivar(dto);
        return new ResponseEntity<>(desativar, HttpStatus.OK);
    }
    @GetMapping("/listar")
    public List<Usuario> listar(){
        return adminUsuarioStatusService.listar();
    }

    @GetMapping("/logs")
    public ResponseEntity<PaginacaoLogsDto> listarLogs(@RequestParam(defaultValue = "0") int pagina){
        var listarLog = logService.obterLogs(pagina);
        return new ResponseEntity<>(listarLog, HttpStatus.OK);
    }
}
