package com.example.demo.Entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "produtos")
@NoArgsConstructor
@AllArgsConstructor
public class Produto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(columnDefinition = "TEXT")
    private String descricao;
    
    @Column(nullable = false)
    private Double preco;
}