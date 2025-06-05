package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO for Mesa operations")
public class MesaDTO {
    
    @Schema(description = "ID da mesa", example = "1")
    private Long id;
    
    @Schema(description = "Quantidade de lugares na mesa", example = "4")
    private Integer quantidade;
    
    @Schema(description = "Status da mesa", example = "livre")
    private String status;
}