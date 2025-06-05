package com.example.demo.controller;

import com.example.demo.dto.MesaDTO;
import com.example.demo.service.MesaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mesas")
@Tag(name = "Mesa Controller", description = "Endpoints para gerenciamento de mesas")
@CrossOrigin(origins = "*")
public class MesaController {

    @Autowired
    private MesaService mesaService;

    @Operation(summary = "Criar uma nova mesa", description = "Cria uma nova mesa com a quantidade especificada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mesa criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @PostMapping
    public ResponseEntity<MesaDTO> createMesa(@RequestBody MesaDTO mesaDTO) {
        return ResponseEntity.ok(mesaService.save(mesaDTO));
    }

    @Operation(summary = "Listar todas as mesas", description = "Retorna uma lista de todas as mesas")
    @GetMapping
    public ResponseEntity<List<MesaDTO>> getAllMesas() {
        return ResponseEntity.ok(mesaService.findAll());
    }

    @Operation(summary = "Buscar mesa por ID", description = "Retorna uma mesa específica pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<MesaDTO> getMesaById(@PathVariable Long id) {
        return ResponseEntity.ok(mesaService.findById(id));
    }

    @Operation(summary = "Atualizar mesa", description = "Atualiza os dados de uma mesa existente")
    @PutMapping("/{id}")
    public ResponseEntity<MesaDTO> updateMesa(@PathVariable Long id, @RequestBody MesaDTO mesaDTO) {
        mesaDTO.setId(id);
        return ResponseEntity.ok(mesaService.update(mesaDTO));
    }

    @Operation(summary = "Deletar mesa", description = "Remove uma mesa pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMesa(@PathVariable Long id) {
        mesaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}