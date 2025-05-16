package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.usuario;
import com.example.demo.dto.UsuarioDto;
import com.example.demo.mapper.UsuarioMapper;
import com.example.demo.repository.IUsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper mapper;

    public Optional<UsuarioDto> usuarioFindById(Long id) {

        return usuarioRepository.findById(id).map(mapper::toDto);

    }

    public UsuarioDto salvar(UsuarioDto dto) {

        usuario usuario = mapper.toEntity(dto);

        return mapper.toDto(usuarioRepository.save(usuario));
    }
}
