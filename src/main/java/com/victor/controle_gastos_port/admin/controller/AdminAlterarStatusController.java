package com.victor.controle_gastos_port.admin.controller;



import com.victor.controle_gastos_port.admin.dto.UsuarioAtivoAndDesativado;
import com.victor.controle_gastos_port.admin.dto.UsuarioAtivoAndDesativadoResponse;
import com.victor.controle_gastos_port.admin.domain.AdminUsuarioStatusService;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminAlterarStatusController {
    private final AdminUsuarioStatusService adminUsuarioStatusService;


    public AdminAlterarStatusController(AdminUsuarioStatusService adminDesativar) {
        this.adminUsuarioStatusService = adminDesativar;
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
}
