package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.MesaDTO;
import com.example.demo.service.MesaService;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Mesas", description = "Endpoints para gerenciamento de mesas")
@RestController
@RequestMapping("api/mesas")
public class MesaController {

    @Autowired
    private MesaService mesaService;

    @Operation(summary = "Lista todas as mesas", description = "Retorna uma lista com todas as mesas cadastradas")
    @GetMapping
    public ResponseEntity<List<MesaDTO>> listarMesas() {
        List<MesaDTO> mesas = mesaService.listarTodas();
        return ResponseEntity.ok(mesas);
    }

    @Operation(summary = "Lista mesas disponíveis", description = "Retorna mesas com status 'Livre'")
    @GetMapping("/disponiveis")
    public ResponseEntity<List<MesaDTO>> listarMesasDisponiveis() {
        List<MesaDTO> mesas = mesaService.listarDisponiveis();
        return ResponseEntity.ok(mesas);
    }

    @Operation(summary = "Busca uma mesa por ID", description = "Retorna os detalhes de uma mesa específica")
    @GetMapping("/{id}")
    public ResponseEntity<MesaDTO> buscarPorId(@PathVariable Long id) {
        Optional<MesaDTO> mesa = mesaService.buscarPorId(id);
        return mesa.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Registra uma nova mesa", description = "Cadastra uma nova mesa no sistema")
    @PostMapping
    public ResponseEntity<ApiResponse<MesaDTO>> criarMesa(@Valid @RequestBody MesaDTO mesaDTO) {
        try {
            MesaDTO savedMesa = mesaService.salvar(mesaDTO);
            ApiResponse<MesaDTO> response = new ApiResponse<>(savedMesa);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse("Argumento inválido", e.getMessage());
            ApiResponse<MesaDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro interno", e.getMessage());
            ApiResponse<MesaDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Atualiza uma mesa", description = "Atualiza os dados de uma mesa existente")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MesaDTO>> atualizarMesa(@PathVariable Long id, @Valid @RequestBody MesaDTO mesaDTO) {
        try {
            mesaDTO.setId(id);
            MesaDTO updatedMesa = mesaService.salvar(mesaDTO);
            ApiResponse<MesaDTO> response = new ApiResponse<>(updatedMesa);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse("Argumento inválido", e.getMessage());
            ApiResponse<MesaDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro interno", e.getMessage());
            ApiResponse<MesaDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Altera status da mesa", description = "Modifica o status de uma mesa para Livre, Ocupada ou Reservada")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<MesaDTO>> alterarStatus(@PathVariable Long id, @RequestBody String status) {
        try {
            MesaDTO mesaAtualizada = mesaService.atualizarStatus(id, status);
            ApiResponse<MesaDTO> response = new ApiResponse<>(mesaAtualizada);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse("Argumento inválido", e.getMessage());
            ApiResponse<MesaDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro interno", e.getMessage());
            ApiResponse<MesaDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Remove uma mesa", description = "Remove uma mesa do sistema, desde que não tenha reservas associadas")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMesa(@PathVariable Long id) {
        try {
            mesaService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Busca mesas por capacidade", description = "Lista mesas com capacidade maior ou igual ao valor informado")
    @GetMapping("/capacidade/{capacidade}")
    public ResponseEntity<List<MesaDTO>> buscarPorCapacidade(@PathVariable Integer capacidade) {
        List<MesaDTO> mesas = mesaService.buscarPorCapacidade(capacidade);
        return ResponseEntity.ok(mesas);
    }
}