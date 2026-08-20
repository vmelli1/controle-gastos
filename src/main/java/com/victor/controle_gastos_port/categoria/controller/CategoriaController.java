package com.victor.controle_gastos_port.categoria.controller;


import com.victor.controle_gastos_port.categoria.dto.CategoriaRequest;
import com.victor.controle_gastos_port.categoria.dto.CategoriaResponse;
import com.victor.controle_gastos_port.categoria.dto.DeletarCategoriaRequest;
import com.victor.controle_gastos_port.categoria.dto.ListaCategoriaResponse;
import com.victor.controle_gastos_port.categoria.service.CategoriaService;
import com.victor.controle_gastos_port.categoria.service.DeletarCategoria;
import com.victor.controle_gastos_port.categoria.service.ListaCategoriaService;
import org.hibernate.query.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
    private final CategoriaService categoriaService;
    private final ListaCategoriaService listaCategoriaService;
    private final DeletarCategoria deletarCategoria;

    public CategoriaController(CategoriaService categoriaService, ListaCategoriaService listaCategoriaService, DeletarCategoria deletarCategoria) {
        this.categoriaService = categoriaService;
        this.listaCategoriaService = listaCategoriaService;
        this.deletarCategoria = deletarCategoria;
    }

    @PostMapping
    public ResponseEntity<CategoriaResponse> categoriaPorId(@RequestBody CategoriaRequest dto) {
        CategoriaResponse criarResponse = categoriaService.criarCategoria(dto);
        return new ResponseEntity<>(criarResponse, HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarCategorias(@RequestParam(defaultValue = "0") int pagina) {
        var listagem = listaCategoriaService.listarCategorias(pagina);
        return new ResponseEntity<>(listagem, HttpStatus.OK);
    }


    @DeleteMapping("/excluir")
    public ResponseEntity<Void> excluirCategoria(@RequestBody DeletarCategoriaRequest dto){
          deletarCategoria.deletCategoria(dto);
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
