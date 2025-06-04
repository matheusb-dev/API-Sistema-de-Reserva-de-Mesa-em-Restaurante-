package com.example.demo.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.ItemCardapioDTO;
import com.example.demo.service.ItemCardapioService;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Cardápio", description = "Endpoints para gerenciamento do cardápio")
@RestController
@RequestMapping("api/cardapio")
public class ItemCardapioController {

    @Autowired
    private ItemCardapioService itemCardapioService;

    @Operation(summary = "Lista todos os itens do cardápio", description = "Retorna uma lista com todos os itens do cardápio")
    @GetMapping
    public ResponseEntity<List<ItemCardapioDTO>> listarItens() {
        List<ItemCardapioDTO> itens = itemCardapioService.listarTodos();
        return ResponseEntity.ok(itens);
    }

    @Operation(summary = "Busca um item por ID", description = "Retorna os detalhes de um item específico do cardápio")
    @GetMapping("/{id}")
    public ResponseEntity<ItemCardapioDTO> buscarPorId(@PathVariable Long id) {
        Optional<ItemCardapioDTO> item = itemCardapioService.buscarPorId(id);
        return item.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Busca itens por nome", description = "Busca itens do cardápio que contenham o nome especificado")
    @GetMapping("/buscar")
    public ResponseEntity<List<ItemCardapioDTO>> buscarPorNome(@RequestParam String nome) {
        List<ItemCardapioDTO> itens = itemCardapioService.buscarPorNome(nome);
        return ResponseEntity.ok(itens);
    }

    @Operation(summary = "Busca itens por faixa de preço", description = "Lista itens do cardápio dentro de uma faixa de preço")
    @GetMapping("/preco")
    public ResponseEntity<List<ItemCardapioDTO>> buscarPorFaixaPreco(
            @RequestParam BigDecimal precoMin,
            @RequestParam BigDecimal precoMax) {
        List<ItemCardapioDTO> itens = itemCardapioService.buscarPorFaixaPreco(precoMin, precoMax);
        return ResponseEntity.ok(itens);
    }

    @Operation(summary = "Busca itens abaixo de um preço", description = "Lista itens do cardápio com preço menor que o especificado")
    @GetMapping("/preco-maximo/{preco}")
    public ResponseEntity<List<ItemCardapioDTO>> buscarAbaixoDoPreco(@PathVariable BigDecimal preco) {
        List<ItemCardapioDTO> itens = itemCardapioService.buscarAbaixoDoPreco(preco);
        return ResponseEntity.ok(itens);
    }

    @Operation(summary = "Adiciona um novo item ao cardápio", description = "Cadastra um novo item no cardápio")
    @PostMapping
    public ResponseEntity<ApiResponse<ItemCardapioDTO>> criarItem(@Valid @RequestBody ItemCardapioDTO itemDTO) {
        try {
            ItemCardapioDTO savedItem = itemCardapioService.salvar(itemDTO);
            ApiResponse<ItemCardapioDTO> response = new ApiResponse<>(savedItem);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse("Argumento inválido", e.getMessage());
            ApiResponse<ItemCardapioDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro interno", e.getMessage());
            ApiResponse<ItemCardapioDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Atualiza um item do cardápio", description = "Atualiza os dados de um item existente no cardápio")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ItemCardapioDTO>> atualizarItem(@PathVariable Long id, @Valid @RequestBody ItemCardapioDTO itemDTO) {
        try {
            itemDTO.setId(id);
            ItemCardapioDTO updatedItem = itemCardapioService.salvar(itemDTO);
            ApiResponse<ItemCardapioDTO> response = new ApiResponse<>(updatedItem);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse("Argumento inválido", e.getMessage());
            ApiResponse<ItemCardapioDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro interno", e.getMessage());
            ApiResponse<ItemCardapioDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Remove um item do cardápio", description = "Remove um item do cardápio, desde que não tenha pedidos associados")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarItem(@PathVariable Long id) {
        try {
            itemCardapioService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}