package com.example.demo.Entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, name = "user_cpf")
    private String cpf;

    @Column(nullable = false, name = "user_nome")
    private String nome;

    @Column(nullable = false, unique = true, name = "user_email")
    private String email;

    @Column(nullable = false, name = "user_senha")
    private String senha;

    @Column(nullable = false, name = "user_telefone")
    private String telefone;

    @Column(nullable = false, unique = true, name = "user_dataNascimento")
    private LocalDate dataNascimento;

}
