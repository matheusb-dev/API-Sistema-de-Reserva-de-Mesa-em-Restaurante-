package com.example.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class PedidoDTO {
    
    private Long id;

    @NotNull(message = "O ID da reserva é obrigatório")
    private Long reservaId;

    @NotNull(message = "O ID do item é obrigatório")
    private Long itemId;

    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 1, message = "A quantidade deve ser pelo menos 1")
    private Integer quantidade;

    // REMOVIDO: O valor total será calculado automaticamente
    // Não deve ser informado pelo cliente
    private BigDecimal valorTotal;

    // Campos adicionais para exibição (somente leitura)
    private String nomeItem;
    private BigDecimal precoUnitario;
    private Long clienteId;
    private String nomeCliente;
}