package com.example.demo.mapper;

import com.example.demo.dto.ClienteDTO;
import com.example.demo.Entities.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    ClienteDTO toDTO(Cliente cliente);

    Cliente toEntity(ClienteDTO clienteDTO);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(ClienteDTO clienteDTO, @MappingTarget Cliente cliente);
}