package com.example.demo.Entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "Mesa")
@NoArgsConstructor
@AllArgsConstructor
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer numero;

    @Column(nullable = false)
    private Integer capacidade;

    @Column(nullable = false)
    private String status;

}