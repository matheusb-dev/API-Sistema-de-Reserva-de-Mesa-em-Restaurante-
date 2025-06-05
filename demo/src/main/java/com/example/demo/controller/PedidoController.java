package com.example.demo.controller;

import com.example.demo.dto.PedidoDTO;
import com.example.demo.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedido Controller", description = "Endpoints para gerenciamento de pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Operation(summary = "Criar novo pedido", description = "Cria um novo pedido com produtos")
    @PostMapping
    public ResponseEntity<PedidoDTO> createPedido(@RequestBody PedidoDTO pedidoDTO) {
        return ResponseEntity.ok(pedidoService.save(pedidoDTO));
    }

    @Operation(summary = "Listar todos os pedidos", description = "Retorna uma lista de todos os pedidos")
    @GetMapping
    public ResponseEntity<List<PedidoDTO>> getAllPedidos() {
        return ResponseEntity.ok(pedidoService.findAll());
    }

    @Operation(summary = "Buscar pedido por ID", description = "Retorna um pedido específico pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> getPedidoById(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.findById(id));
    }

    @Operation(summary = "Adicionar produtos ao pedido", description = "Adiciona novos produtos a um pedido existente")
    @PostMapping("/{id}/produtos")
    public ResponseEntity<PedidoDTO> adicionarProdutos(
            @PathVariable Long id,
            @RequestBody List<Long> produtosIds) {
        return ResponseEntity.ok(pedidoService.adicionarProdutos(id, produtosIds));
    }

    @Operation(summary = "Deletar pedido", description = "Remove um pedido pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        pedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}