package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.Entities.Mesa;
import com.example.demo.dto.MesaDTO;

@Mapper(componentModel = "spring")
public interface MesaMapper {
    
    MesaDTO toDTO(Mesa mesa);

    Mesa toEntity(MesaDTO mesaDTO);

    List<MesaDTO> toDTOList(List<Mesa> mesas);
}