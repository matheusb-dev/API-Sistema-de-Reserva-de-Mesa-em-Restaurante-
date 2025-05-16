package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.Cliente;
import com.example.demo.Entities.usuario;
import com.example.demo.dto.Clientedto;
import com.example.demo.mapper.ClienteMapper;
import com.example.demo.repository.IUsuarioRepository;

@Service
public class ClienteService {

    @Autowired
    private IUsuarioRepository clienteRepository;

    @Autowired
    private ClienteMapper mapper;

    public Optional<Clientedto> usuarioFindById(Long id) {

        return clienteRepository.findById(id).map(mapper::toDto);

    }

    public Clientedto salvar(Clientedto dto) {

        Cliente clientes = mapper.toEntity(dto);

        return mapper.toDto(clienteRepository.save(clientes));
    }

    private <U> U toDto(Cliente cliente1) {
        return null;
    }
}
