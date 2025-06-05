package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para operações de Produto")
public class ProdutoDTO {
    
    @Schema(description = "ID do produto", example = "1")
    private Long id;
    
    @Schema(description = "Nome do produto", example = "X-Burger")
    private String nome;
    
    @Schema(description = "Descrição do produto", example = "Hambúrguer com queijo, alface e tomate")
    private String descricao;
    
    @Schema(description = "Preço do produto", example = "25.90")
    private Double preco;
}