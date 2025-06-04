package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class ReservaDTO {
    
    private Long id;

    @NotNull(message = "O ID do cliente é obrigatório")
    private Long clienteId;

    @NotNull(message = "O ID da mesa é obrigatório")
    private Long mesaId;

    @NotNull(message = "A data e hora da reserva são obrigatórias")
    private LocalDateTime dataReserva;

    @Pattern(regexp = "^(Confirmada|Cancelada|Concluída)$", 
             message = "Status deve ser: Confirmada, Cancelada ou Concluída")
    private String status = "Confirmada"; // Valor padrão

    // Campos adicionais para exibição (somente leitura)
    private String nomeCliente;
    private Integer numeroMesa;
    private Integer capacidadeMesa;
}