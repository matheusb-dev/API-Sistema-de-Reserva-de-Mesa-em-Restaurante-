package com.example.demo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MesaDTO {
    
    private Long id;

    @NotNull(message = "O número da mesa é obrigatório")
    @Min(value = 1, message = "O número da mesa deve ser maior que zero")
    @Max(value = 999, message = "O número da mesa não pode ser superior a 999")
    private Integer numero;

    @NotNull(message = "A capacidade é obrigatória")
    @Min(value = 1, message = "A capacidade deve ser pelo menos 1")
    @Max(value = 50, message = "A capacidade máxima é de 50 pessoas")
    private Integer capacidade;

    @Pattern(regexp = "^(Livre|Ocupada|Reservada)$", 
             message = "Status deve ser: Livre, Ocupada ou Reservada")
    private String status = "Livre"; // Valor padrão
}