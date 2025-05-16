package com.example.demo.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class Clientedto {

    private Long Id;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @Size(min = 11, max = 11, message = "CPF deve conter 11 digitos")
    private String cpf;

    @Email(message = "Email inválido")
    private String email;
    private String senha;
    
    @Size(min = 11, max = 11, message = "Digite um telefone com DDD")
    private String telefone;
    private LocalDate dataNascimento;
}
