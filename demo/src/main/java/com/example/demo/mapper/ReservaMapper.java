package com.example.demo.mapper;

import com.example.demo.dto.ReservaDTO;
import com.example.demo.Entities.Reserva;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ReservaMapper {

    ReservaMapper INSTANCE = Mappers.getMapper(ReservaMapper.class);

    @Mapping(source = "mesa.id", target = "mesaId")
    @Mapping(source = "cliente.id", target = "clienteId")
    ReservaDTO toDTO(Reserva reserva);

    @Mapping(source = "mesaId", target = "mesa.id")
    @Mapping(source = "clienteId", target = "cliente.id")
    Reserva toEntity(ReservaDTO reservaDTO);
}