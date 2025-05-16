package com.example.demo.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.Entities.usuario;
import com.example.demo.dto.UsuarioDto;
import com.example.demo.service.UsuarioService;
import com.example.demo.service.Utils.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Renato", description = "Endpoints de gerenciamento de usuário")
@RestController
@RequestMapping("api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Busca usuário por ID", description = "Retorna as informações do usuário baseado no ID")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> getById(@PathVariable Long id) {
        Optional<UsuarioDto> UsuarioDto = usuarioService.usuarioFindById(id);
        return UsuarioDto.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());

    }


}
