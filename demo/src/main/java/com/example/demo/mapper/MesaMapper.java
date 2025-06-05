package com.example.demo.mapper;

import com.example.demo.dto.MesaDTO;
import com.example.demo.Entities.Mesa;
import org.springframework.stereotype.Component;

@Component
public class MesaMapper {
    
    public Mesa toEntity(MesaDTO dto) {
        Mesa mesa = new Mesa();
        mesa.setQuantidade(dto.getQuantidade());
        mesa.setStatus("livre"); // Default status
        return mesa;
    }
    
    public MesaDTO toDTO(Mesa mesa) {
        return new MesaDTO(
            mesa.getId(),
            mesa.getQuantidade(),
            mesa.getStatus()
        );
    }
}