package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO for Reserva operations")
public class ReservaDTO {
    
    @Schema(description = "ID da reserva", example = "1")
    private Long id;
    
    @Schema(description = "ID da mesa", example = "1")
    private Long mesaId;
    
    @Schema(description = "ID do cliente", example = "1")
    private Long clienteId;
    
    @Schema(description = "Horário da reserva", example = "2025-06-05T16:00:00")
    private LocalDateTime horario;
}