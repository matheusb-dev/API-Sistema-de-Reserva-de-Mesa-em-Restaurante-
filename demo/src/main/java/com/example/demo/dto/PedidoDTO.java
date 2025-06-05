package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para operações de Pedido")
public class PedidoDTO {
    
    @Schema(description = "ID do pedido", example = "1")
    private Long id;
    
    @Schema(description = "ID do cliente", example = "1")
    private Long clienteId;
    
    @Schema(description = "ID da mesa", example = "1")
    private Long mesaId;
    
    @Schema(description = "Lista de IDs dos produtos", example = "[1, 2, 3]")
    @Builder.Default
    private List<Long> produtosIds = new ArrayList<>();
    
    @Schema(description = "Valor total do pedido", example = "75.80")
    private Double valorTotal;
    
    @Schema(description = "Data e hora do pedido", example = "2025-06-05T17:31:28")
    private LocalDateTime dataPedido;
}