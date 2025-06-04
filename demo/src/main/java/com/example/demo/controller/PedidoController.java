package com.example.demo.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.PedidoDTO;
import com.example.demo.service.PedidoService;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Tag(name = "Pedidos", description = "Endpoints para gerenciamento de pedidos")
@RestController
@RequestMapping("api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Operation(summary = "Lista todos os pedidos", description = "Lista todos os pedidos registrados")
    @GetMapping
    public ResponseEntity<List<PedidoDTO>> listarPedidos() {
        List<PedidoDTO> pedidos = pedidoService.listarTodos();
        return ResponseEntity.ok(pedidos);
    }

    @Operation(summary = "Busca um pedido por ID", description = "Obtém os detalhes de um pedido específico por ID")
    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> buscarPorId(@PathVariable Long id) {
        Optional<PedidoDTO> pedido = pedidoService.buscarPorId(id);
        return pedido.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Lista pedidos por reserva", description = "Lista os pedidos associados a uma reserva específica")
    @GetMapping("/reserva/{reservaId}")
    public ResponseEntity<List<PedidoDTO>> listarPedidosReserva(@PathVariable Long reservaId) {
        List<PedidoDTO> pedidos = pedidoService.buscarPorReserva(reservaId);
        return ResponseEntity.ok(pedidos);
    }

    @Operation(summary = "Lista pedidos por cliente", description = "Lista todos os pedidos de um cliente específico")
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoDTO>> listarPedidosCliente(@PathVariable Long clienteId) {
        List<PedidoDTO> pedidos = pedidoService.buscarPorCliente(clienteId);
        return ResponseEntity.ok(pedidos);
    }

    @Operation(summary = "Cria um novo pedido", 
               description = "Registra um novo pedido. O valor total será calculado automaticamente com base no preço do item e quantidade.")
    @PostMapping
    public ResponseEntity<ApiResponse<PedidoDTO>> criarPedido(@Valid @RequestBody PedidoDTO pedidoDTO) {
        try {
            // Limpar valor total - será calculado automaticamente
            pedidoDTO.setValorTotal(null);
            
            PedidoDTO savedPedido = pedidoService.criarPedido(pedidoDTO);
            ApiResponse<PedidoDTO> response = new ApiResponse<>(savedPedido);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse("Argumento inválido", e.getMessage());
            ApiResponse<PedidoDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro interno", e.getMessage());
            ApiResponse<PedidoDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Atualiza quantidade do pedido", description = "Altera a quantidade de um pedido existente")
    @PatchMapping("/{id}/quantidade")
    public ResponseEntity<ApiResponse<PedidoDTO>> atualizarQuantidade(
            @PathVariable Long id, 
            @RequestBody @Min(value = 1, message = "Quantidade mínima é 1") 
                         @Max(value = 100, message = "Quantidade máxima é 100") Integer quantidade) {
        try {
            PedidoDTO pedidoAtualizado = pedidoService.atualizarQuantidade(id, quantidade);
            ApiResponse<PedidoDTO> response = new ApiResponse<>(pedidoAtualizado);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse("Argumento inválido", e.getMessage());
            ApiResponse<PedidoDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro interno", e.getMessage());
            ApiResponse<PedidoDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Remove um pedido", description = "Remove um pedido existente (apenas se a reserva estiver confirmada)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        try {
            pedidoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Calcula total por reserva", description = "Calcula o valor total de todos os pedidos de uma reserva")
    @GetMapping("/reserva/{reservaId}/total")
    public ResponseEntity<BigDecimal> calcularTotalReserva(@PathVariable Long reservaId) {
        try {
            BigDecimal total = pedidoService.calcularTotalPorReserva(reservaId);
            return ResponseEntity.ok(total);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}