package com.example.demo.service;

import com.example.demo.dto.MesaDTO;
import com.example.demo.Entities.Mesa;
import com.example.demo.repository.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MesaService {

    @Autowired
    private MesaRepository mesaRepository;

    public MesaDTO save(MesaDTO mesaDTO) {
        Mesa mesa = toEntity(mesaDTO);
        mesa.setStatus("livre"); // Always set status to "livre" for new mesas
        return toDTO(mesaRepository.save(mesa));
    }

    public List<MesaDTO> findAll() {
        return mesaRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public MesaDTO findById(Long id) {
        return mesaRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Mesa não encontrada"));
    }

    public MesaDTO update(MesaDTO mesaDTO) {
        // Verify if mesa exists
        findById(mesaDTO.getId());
        Mesa mesa = toEntity(mesaDTO);
        return toDTO(mesaRepository.save(mesa));
    }

    public void delete(Long id) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesa não encontrada"));
        mesaRepository.delete(mesa);
    }

    // Helper methods for converting between DTO and Entity
    private Mesa toEntity(MesaDTO dto) {
        Mesa mesa = new Mesa();
        mesa.setId(dto.getId());
        mesa.setQuantidade(dto.getQuantidade());
        mesa.setStatus(dto.getStatus() != null ? dto.getStatus() : "livre");
        return mesa;
    }

    private MesaDTO toDTO(Mesa mesa) {
        return new MesaDTO(
            mesa.getId(),
            mesa.getQuantidade(),
            mesa.getStatus()
        );
    }
}