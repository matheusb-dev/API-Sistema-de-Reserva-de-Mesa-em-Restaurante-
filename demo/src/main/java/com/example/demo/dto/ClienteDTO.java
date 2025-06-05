package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO for Cliente operations")
public class ClienteDTO {
    
    @Schema(description = "ID do cliente", example = "1")
    private Long id;
    
    @Schema(description = "Nome do cliente", example = "João Silva")
    private String nome;
    
    @Schema(description = "Email do cliente", example = "joao@email.com")
    private String email;
    
    @Schema(description = "Telefone do cliente", example = "(11) 98765-4321")
    private String telefone;
    
    @Schema(description = "CPF do cliente", example = "123.456.789-00")
    private String cpf;
}