package com.example.demo.controller;

import com.example.demo.dto.ProdutoDTO;
import com.example.demo.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@Tag(name = "Produto Controller", description = "Endpoints para gerenciamento de produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Operation(summary = "Criar um novo produto", description = "Cria um novo produto com os dados fornecidos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<ProdutoDTO> createProduto(@RequestBody ProdutoDTO produtoDTO) {
        return ResponseEntity.ok(produtoService.save(produtoDTO));
    }

    @Operation(summary = "Listar todos os produtos", description = "Retorna uma lista de todos os produtos")
    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> getAllProdutos() {
        return ResponseEntity.ok(produtoService.findAll());
    }

    @Operation(summary = "Buscar produto por ID", description = "Retorna um produto específico pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> getProdutoById(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.findById(id));
    }

    @Operation(summary = "Atualizar produto", description = "Atualiza os dados de um produto existente")
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> updateProduto(@PathVariable Long id, @RequestBody ProdutoDTO produtoDTO) {
        produtoDTO.setId(id);
        return ResponseEntity.ok(produtoService.update(produtoDTO));
    }

    @Operation(summary = "Deletar produto", description = "Remove um produto pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}