package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.ClienteDTO;
import com.example.demo.service.ClienteService;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Clientes", description = "Endpoints para gerenciamento de clientes")
@RestController
@RequestMapping("api/usuarios")
public class ClienteController {

    @Autowired
    private ClienteService usuarioService;

    @Operation(summary = "Lista todos os clientes", description = "Retorna uma lista com todos os Clientes cadastrados")
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarUsuarios() {
        List<ClienteDTO> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Busca um cliente por ID", description = "Retorna os detalhes de um usuário específico")
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable Long id) {
        Optional<ClienteDTO> ClienteDTO = usuarioService.buscarPorId(id);
        return ClienteDTO.map(ResponseEntity::ok)
                         .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Cria um novo Cliente", description = "Cadastra um novo cliente no sistema")
    @PostMapping
    public ResponseEntity<ApiResponse<ClienteDTO>> criarUsuario(@Valid @RequestBody ClienteDTO ClienteDTO) {
        try {
            // Tenta salvar o usuário
            ClienteDTO savedUsuario = usuarioService.salvar(ClienteDTO);
            
            // Retorna sucesso com o ClienteDTO salvo
            ApiResponse<ClienteDTO> response = new ApiResponse<>(savedUsuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            // Cria um erro com a mensagem específica
            ErrorResponse errorResponse = new ErrorResponse("Argumento inválido", e.getMessage());
            ApiResponse<ClienteDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            // Cria um erro genérico
            ErrorResponse errorResponse = new ErrorResponse("Erro interno", e.getMessage());
            ApiResponse<ClienteDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Deleta um cliente", description = "Remove um cliente do sistema pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
