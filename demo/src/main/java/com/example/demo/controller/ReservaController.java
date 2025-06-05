package com.example.demo.controller;

import com.example.demo.dto.ReservaDTO;
import com.example.demo.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservas")
@Tag(name = "Reserva Controller", description = "Endpoints para gerenciamento de reservas")
@CrossOrigin(origins = "*")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Operation(summary = "Criar uma nova reserva", description = "Cria uma nova reserva para uma mesa e cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reserva criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou horário fora do permitido")
    })
    @PostMapping
    public ResponseEntity<ReservaDTO> createReserva(@RequestBody ReservaDTO reservaDTO) {
        return ResponseEntity.ok(reservaService.save(reservaDTO));
    }

    @Operation(summary = "Listar todas as reservas", description = "Retorna uma lista de todas as reservas")
    @GetMapping
    public ResponseEntity<List<ReservaDTO>> getAllReservas() {
        return ResponseEntity.ok(reservaService.findAll());
    }

    @Operation(summary = "Buscar reserva por ID", description = "Retorna uma reserva específica pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTO> getReservaById(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.findById(id));
    }

    @Operation(summary = "Verificar disponibilidade", 
              description = "Verifica se uma mesa está disponível para reserva em um horário específico")
    @GetMapping("/disponibilidade")
    public ResponseEntity<Map<String, Object>> verificarDisponibilidade(
            @RequestParam Long mesaId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime horario) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            ReservaDTO dto = new ReservaDTO();
            dto.setMesaId(mesaId);
            dto.setHorario(horario);
            reservaService.validarDisponibilidade(dto);
            response.put("disponivel", true);
            response.put("mensagem", "Mesa disponível para reserva");
        } catch (RuntimeException e) {
            response.put("disponivel", false);
            response.put("mensagem", e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Buscar reservas por mesa e data", 
              description = "Retorna todas as reservas de uma mesa em uma data específica")
    @GetMapping("/mesa/{mesaId}/data")
    public ResponseEntity<List<ReservaDTO>> getReservasByMesaAndData(
            @PathVariable Long mesaId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime data) {
        return ResponseEntity.ok(reservaService.findReservasByMesaAndData(mesaId, data));
    }

    @Operation(summary = "Cancelar reserva", description = "Cancela uma reserva e libera a mesa")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReserva(@PathVariable Long id) {
        reservaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}