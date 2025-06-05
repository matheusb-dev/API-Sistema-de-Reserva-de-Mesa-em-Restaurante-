package com.example.demo.Entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "mesas")
@NoArgsConstructor
@AllArgsConstructor
public class Mesa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Integer quantidade;
    
    @Column(nullable = false)
    private String status = "livre";  // Default value will be "livre"
}