package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.ReservaDTO;
import com.example.demo.service.ReservaService;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Reservas", description = "Endpoints para gerenciamento de reservas")
@RestController
@RequestMapping("api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Operation(summary = "Lista todas as reservas", description = "Retorna uma lista com todas as reservas")
    @GetMapping
    public ResponseEntity<List<ReservaDTO>> listarReservas() {
        List<ReservaDTO> reservas = reservaService.listarTodas();
        return ResponseEntity.ok(reservas);
    }

    @Operation(summary = "Lista reservas de hoje", description = "Retorna reservas para o dia atual")
    @GetMapping("/hoje")
    public ResponseEntity<List<ReservaDTO>> listarReservasHoje() {
        List<ReservaDTO> reservas = reservaService.buscarReservasHoje();
        return ResponseEntity.ok(reservas);
    }

    @Operation(summary = "Lista próximas reservas", description = "Retorna reservas nas próximas horas")
    @GetMapping("/proximas/{horas}")
    public ResponseEntity<List<ReservaDTO>> listarProximasReservas(
            @Parameter(description = "Número de horas para buscar") @PathVariable int horas) {
        List<ReservaDTO> reservas = reservaService.buscarReservasProximasHoras(horas);
        return ResponseEntity.ok(reservas);
    }

    
    @Operation(summary = "Busca uma reserva por ID", description = "Retorna os detalhes de uma reserva específica")
    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTO> buscarPorId(@PathVariable Long id) {
        Optional<ReservaDTO> reserva = reservaService.buscarPorId(id);
        return reserva.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Lista reservas de um cliente", description = "Lista todas as reservas de um cliente específico")
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<ReservaDTO>> listarReservasCliente(@PathVariable Long clienteId) {
        List<ReservaDTO> reservas = reservaService.buscarPorCliente(clienteId);
        return ResponseEntity.ok(reservas);
    }

    @Operation(summary = "Lista reservas por status", description = "Lista reservas filtradas por status")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ReservaDTO>> listarPorStatus(@PathVariable String status) {
        List<ReservaDTO> reservas = reservaService.buscarPorStatus(status);
        return ResponseEntity.ok(reservas);
    }

    @Operation(summary = "Cria uma nova reserva", 
               description = "Cria uma nova reserva de mesa. Formato da data: 2025-06-05T19:30:00")
    @PostMapping
    public ResponseEntity<ApiResponse<ReservaDTO>> criarReserva(@Valid @RequestBody ReservaDTO reservaDTO) {
        try {
            // Limpar ID para garantir que será gerado automaticamente
            reservaDTO.setId(null);
            
            ReservaDTO savedReserva = reservaService.criarReserva(reservaDTO);
            ApiResponse<ReservaDTO> response = new ApiResponse<>(savedReserva);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro de validação", e.getMessage());
            ApiResponse<ReservaDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro interno", e.getMessage());
            ApiResponse<ReservaDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Atualiza status da reserva", description = "Atualiza o status de uma reserva para Confirmada, Cancelada ou Concluída")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<ReservaDTO>> atualizarStatus(
            @PathVariable Long id, 
            @RequestBody String status) {
        try {
            // Remover aspas se houver
            status = status.replace("\"", "").trim();
            
            ReservaDTO reservaAtualizada = reservaService.atualizarStatus(id, status);
            ApiResponse<ReservaDTO> response = new ApiResponse<>(reservaAtualizada);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro de validação", e.getMessage());
            ApiResponse<ReservaDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro interno", e.getMessage());
            ApiResponse<ReservaDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Cancela uma reserva", description = "Cancela uma reserva existente")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> cancelarReserva(@PathVariable Long id) {
        try {
            reservaService.cancelarReserva(id);
            ApiResponse<String> response = new ApiResponse<>("Reserva cancelada com sucesso");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro de validação", e.getMessage());
            ApiResponse<String> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Operation(summary = "Lista reservas por período", 
               description = "Lista reservas entre duas datas. Formato: 2025-06-05T19:30:00")
    @GetMapping("/periodo")
    public ResponseEntity<List<ReservaDTO>> listarPorPeriodo(
            @Parameter(description = "Data/hora de início", example = "2025-06-05T08:00:00")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @Parameter(description = "Data/hora de fim", example = "2025-06-05T23:59:59")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        try {
            List<ReservaDTO> reservas = reservaService.buscarReservasPorPeriodo(inicio, fim);
            return ResponseEntity.ok(reservas);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
