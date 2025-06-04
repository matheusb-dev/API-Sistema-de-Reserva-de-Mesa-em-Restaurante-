package com.example.demo.service;


import com.example.demo.Entities.Cliente;
import com.example.demo.dto.ClienteDTO;
import com.example.demo.mapper.ClienteMapper;
import com.example.demo.repository.IClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ClienteService {

    @Autowired
    private IClienteRepository clientteRepository;

    @Autowired
    private ClienteMapper clienteMapper;
    

    public List<ClienteDTO> listarTodos() {
        return clienteMapper.toDTOList(clientteRepository.findAll());
    }

    public Optional<ClienteDTO> buscarPorId(Long id) {
        return clientteRepository.findById(id).map(clienteMapper::toDTO);
    }

    public ClienteDTO salvar(ClienteDTO clienteDTO) {
        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        return clienteMapper.toDTO(clientteRepository.save(cliente));
    }

    public void deletar(Long id) {
        clientteRepository.deleteById(id);
    }
}

